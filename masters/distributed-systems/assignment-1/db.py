import logging
from uuid import uuid1

import psycopg2
from decouple import config
from psycopg2.extras import LoggingConnection

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def init_db():
    fly_conn, hotel_conn, account_conn = [
        psycopg2.connect(
            connection_factory=LoggingConnection,
            host=config('HOST'),
            port=config(f'DB{i}_PORT'),
            user=config('DB_USER'),
            password=config('PASSWORD'),
            dbname=config(f'DB{i}_NAME'),
        ) for i in range(1, 4)
    ]
    [conn.initialize(logger) for conn in (fly_conn, hotel_conn, account_conn)]
    return fly_conn, hotel_conn, account_conn


def add_user(user_name: str, amount: int) -> int:
    fly_conn, hotel_conn, account_conn = init_db()
    fly_db, hotel_db, account_db = [db.cursor() for db in (fly_conn, hotel_conn, account_conn)]
    account_db.execute(
        "INSERT INTO account (client_name, amount) VALUES (%s, %s) RETURNING account_id;",
        (user_name, amount)
    )
    account_id = account_db.fetchone()[0]
    account_conn.commit()
    return account_id


def show_account(account_id: int) -> str:
    fly_conn, hotel_conn, account_conn = init_db()
    fly_db, hotel_db, account_db = [db.cursor() for db in (fly_conn, hotel_conn, account_conn)]
    account_db.execute(
        "SELECT * FROM account WHERE account_id = %s;",
        (account_id,)
    )
    account = account_db.fetchone()
    account_conn.commit()
    return account


def show_transactions():
    fly_conn, hotel_conn, account_conn = init_db()
    fly_db, hotel_db, account_db = [db.cursor() for db in (fly_conn, hotel_conn, account_conn)]
    fly_db.execute("SELECT * FROM pg_prepared_xacts;")
    logging.info(f'Fly transactions: {fly_db.fetchall()}')
    hotel_db.execute("SELECT * FROM pg_prepared_xacts;")
    logging.info(f'Hotel transactions: {hotel_db.fetchall()}')
    account_db.execute("SELECT * FROM pg_prepared_xacts;")
    logging.info(f'Account transactions: {account_db.fetchall()}')


def transaction(fly_record: tuple,
                hotel_record: tuple,
                account_id: int,
                enable_prepare: bool = True):
    fly_conn, hotel_conn, account_conn = init_db()
    fly_db, hotel_db, account_db = [db.cursor() for db in (fly_conn, hotel_conn, account_conn)]
    # begin transaction
    xid = uuid1().hex
    [db.tpc_begin(xid) for db in (fly_conn, hotel_conn, account_conn)]

    # prepare
    try:
        hotel_db.execute(
            "INSERT INTO hotel_booking "
            "(client_name, hotel_name, arrival, departure) "
            "VALUES (%s, %s, %s, %s)",
            hotel_record
        )
        fly_db.execute(
            "INSERT INTO fly_booking "
            "(client_name, fly_number, departure_from, departure_to, date) "
            "VALUES (%s, %s, %s, %s, %s)",
            fly_record
        )
        account_db.execute(
            "UPDATE account SET amount=amount-100 WHERE account_id=%s",
            (account_id,)
        )
        logger.info('Prepare records')
        fly_conn.tpc_prepare()
        hotel_conn.tpc_prepare()
        account_conn.tpc_prepare()
    except Exception as e:
        logger.info('Rollback records')
        logger.exception(e)
        fly_conn.tpc_rollback()
        hotel_conn.tpc_rollback()
        account_conn.tpc_rollback()
    else:
        if enable_prepare:
            fly_conn.tpc_commit()
        hotel_conn.tpc_commit()
        account_conn.tpc_commit()


def with_rollback():
    hotel_record = ("Edsger Dijkstra", "Hogwarts", "2020-03-04", "2020-03-14")
    fly_record = ("Edsger Dijkstra", "9 3/4", "London", "Hogwarts", "2020-03-04")
    account_id = add_user('Edsger Dijkstra', 350)
    for i in range(4):
        show_transactions()
        logger.info(f"Before transaction: {show_account(account_id)}")
        transaction(fly_record, hotel_record, account_id)
        logger.info(f"After transaction: {show_account(account_id)}")


def without_rollback():
    hotel_record = ("Edsger Dijkstra", "Hogwarts", "2020-03-04", "2020-03-14")
    fly_record = ("Edsger Dijkstra", "9 3/4", "London", "Hogwarts", "2020-03-04")
    account_id = add_user('Edsger Dijkstra', 350)
    for i in range(4):
        show_transactions()
        logger.info(f"Before transaction: {show_account(account_id)}")
        transaction(fly_record, hotel_record, account_id, enable_prepare=False)
        logger.info(f"After transaction: {show_account(account_id)}")


if __name__ == '__main__':
    logger.info("-" * 70)
    logger.info("With rollback")
    with_rollback()
    logger.info("-" * 70)
    logger.info("Without rollback")
    without_rollback()
