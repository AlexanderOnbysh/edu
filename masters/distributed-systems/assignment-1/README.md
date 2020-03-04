# 2 phase commit

## How to run
```bash
make setup
make run
```

## Results
### 2 phase commit
```
"/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/.env/bin/python" "/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/db.py"
INFO:__main__:----------------------------------------------------------------------
INFO:__main__:With rollback
DEBUG:__main__:b"INSERT INTO account (client_name, amount) VALUES ('Edsger Dijkstra', 350) RETURNING account_id;"
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:Before transaction: (1, 'Edsger Dijkstra', Decimal('350'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=1'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:After transaction: (1, 'Edsger Dijkstra', Decimal('250'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:Before transaction: (1, 'Edsger Dijkstra', Decimal('250'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=1'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:After transaction: (1, 'Edsger Dijkstra', Decimal('150'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:Before transaction: (1, 'Edsger Dijkstra', Decimal('150'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=1'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:After transaction: (1, 'Edsger Dijkstra', Decimal('50'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:Before transaction: (1, 'Edsger Dijkstra', Decimal('50'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=1'
INFO:__main__:Rollback records
ERROR:__main__:new row for relation "account" violates check constraint "positive_amount"
DETAIL:  Failing row contains (1, Edsger Dijkstra, -50).
Traceback (most recent call last):
  File "/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/db.py", line 88, in transaction
    (account_id,)
  File "/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/.env/lib/python3.7/site-packages/psycopg2/extras.py", line 454, in execute
    return super(LoggingCursor, self).execute(query, vars)
psycopg2.errors.CheckViolation: new row for relation "account" violates check constraint "positive_amount"
DETAIL:  Failing row contains (1, Edsger Dijkstra, -50).

DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 1;'
INFO:__main__:After transaction: (1, 'Edsger Dijkstra', Decimal('50'))
```

## Blocked transactions
As we can see from logs `Fly transactions` are increasing.

```
INFO:__main__:----------------------------------------------------------------------
INFO:__main__:Without rollback
DEBUG:__main__:b"INSERT INTO account (client_name, amount) VALUES ('Edsger Dijkstra', 350) RETURNING account_id;"
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:Before transaction: (2, 'Edsger Dijkstra', Decimal('350'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=2'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:After transaction: (2, 'Edsger Dijkstra', Decimal('250'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: [('557', 'a5ee75da5df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 76607, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1')]
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:Before transaction: (2, 'Edsger Dijkstra', Decimal('250'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=2'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:After transaction: (2, 'Edsger Dijkstra', Decimal('150'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: [('557', 'a5ee75da5df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 76607, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1'), ('558', 'a60591a25df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 226333, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1')]
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:Before transaction: (2, 'Edsger Dijkstra', Decimal('150'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=2'
INFO:__main__:Prepare records
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:After transaction: (2, 'Edsger Dijkstra', Decimal('50'))
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Fly transactions: [('557', 'a5ee75da5df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 76607, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1'), ('558', 'a60591a25df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 226333, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1'), ('559', 'a61ae1b05df911eaa618acde48001122', datetime.datetime(2020, 3, 4, 9, 22, 20, 365073, tzinfo=psycopg2.tz.FixedOffsetTimezone(offset=0, name=None)), 'user', 'db1')]
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Hotel transactions: []
DEBUG:__main__:b'SELECT * FROM pg_prepared_xacts;'
INFO:root:Account transactions: []
DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:Before transaction: (2, 'Edsger Dijkstra', Decimal('50'))
DEBUG:__main__:b"INSERT INTO hotel_booking (client_name, hotel_name, arrival, departure) VALUES ('Edsger Dijkstra', 'Hogwarts', '2020-03-04', '2020-03-14')"
DEBUG:__main__:b"INSERT INTO fly_booking (client_name, fly_number, departure_from, departure_to, date) VALUES ('Edsger Dijkstra', '9 3/4', 'London', 'Hogwarts', '2020-03-04')"
DEBUG:__main__:b'UPDATE account SET amount=amount-100 WHERE account_id=2'
INFO:__main__:Rollback records
ERROR:__main__:new row for relation "account" violates check constraint "positive_amount"
DETAIL:  Failing row contains (2, Edsger Dijkstra, -50).
Traceback (most recent call last):
  File "/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/db.py", line 88, in transaction
    (account_id,)
  File "/Users/alexon/Projects/edu/masters/distributed systems/assignment-1/.env/lib/python3.7/site-packages/psycopg2/extras.py", line 454, in execute
    return super(LoggingCursor, self).execute(query, vars)
psycopg2.errors.CheckViolation: new row for relation "account" violates check constraint "positive_amount"
DETAIL:  Failing row contains (2, Edsger Dijkstra, -50).

DEBUG:__main__:b'SELECT * FROM account WHERE account_id = 2;'
INFO:__main__:After transaction: (2, 'Edsger Dijkstra', Decimal('50'))
```
