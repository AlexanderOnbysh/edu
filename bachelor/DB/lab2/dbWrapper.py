import asyncio
import logging
from typing import Dict
from generateData import GenData
import asyncpg
from asyncpg.connection import Connection
import time
from generateData import clear_buffer

logger = logging.getLogger('DBWrapper')

N_RECORDS = 50_000


class DBWrapper:
    def __init__(self, config: Dict[str, str]):
        self.config = config

    async def open_connection(self):
        self._connection: Connection = await asyncpg.connect(**self.config)

    async def close_connection(self):
        await self._connection.close()

    async def exec_query(self, query: str) -> str:
        """
        Execute query and return result code
        """
        result = await self._connection.execute(query)
        return result

    async def fetch_query(self, query: str) -> list:
        """
        Execute query and get NamedTuples with result
        """
        result = await self._connection.fetch(query)
        return result

    async def create_tables_no_index(self):
        table1 = """CREATE TABLE contracts (
                    id          SERIAL,
                    surname     VARCHAR(50) NOT NULL, 
                    software_id INTEGER NOT NULL,
                    date        TIMESTAMP
                    );
                  """

        table2 = """CREATE TABLE clients (
                    id         SERIAL PRIMARY KEY,
                    client_id  INTEGER NOT NULL,
                    name       VARCHAR(50) NOT NULL,
                    surname    VARCHAR(50) NOT NULL,
                    address    VARCHAR(255) NOT NULL,
                    company    VARCHAR(255) NOT NULL
                  );"""
        await self.exec_query(table1)
        await self.exec_query(table2)

    async def create_tables_with_index(self):
        table1 = """CREATE TABLE contracts (
                    id          SERIAL,
                    surname     VARCHAR(50) NOT NULL, 
                    software_id INTEGER NOT NULL,
                    date        TIMESTAMP
                    );
                    CREATE INDEX index_contracts ON contracts (surname);
                          """

        table2 = """CREATE TABLE clients (
                    id         SERIAL PRIMARY KEY,
                    client_id  INTEGER NOT NULL,
                    name       VARCHAR(50) NOT NULL,
                    surname    VARCHAR(50) NOT NULL,
                    address    VARCHAR(255) NOT NULL,
                    company    VARCHAR(255) NOT NULL
                  );
                  CREATE INDEX index_clients ON clients (surname);"""
        await self.exec_query(table1)
        await self.exec_query(table2)

    async def drop_tables(self):
        await self.exec_query('DROP TABLE clients, contracts;')

    async def fill_with_random(self):
        gen = GenData()
        st = time.time()
        print('Генерация клиентов')
        clients = gen.gen_client(N_RECORDS)
        print(f'Время генерации клиентов: {time.time() - st} s')
        print('Генерация контрактов')
        contracts = gen.gen_contracts(N_RECORDS)
        print(f'Время генерации контрактов: {time.time() - st} s')

        print('Вставка данных о клиентах')
        st = time.time()
        await self.exec_query(clients)
        print(f'Время вставки: {time.time() - st} s')

        print('Вставка данных о контрактах')
        st = time.time()
        await self.exec_query(contracts)
        print(f'Время вставки: {time.time() - st} s \n')

    async def make_join(self, with_index):
        await clear_buffer(with_index)
        query = "SELECT * FROM contracts JOIN clients ON clients.surname = contracts.surname WHERE clients.surname = 'Brown';"
        await self.exec_query(query)


if __name__ == '__main__':
    config = {
        'host': 'localhost',
        'port': 5432,
        'database': 'alexon',
    }


    async def run():
        db = DBWrapper(config)
        try:
            print('- Подсоединение к БД')
            await db.open_connection()

            print('Создание таблиц без индексов')
            await db.create_tables_no_index()

            print('Генерация данных')
            await db.fill_with_random()

            print('- Make JOIN without indexies')
            print('JOIN без индексов')
            st = time.time()
            await db.make_join(False)
            print(f'Время: {time.time() - st} s\n')

            print('Удаление таблиц')
            await db.drop_tables()

            print('\nС индексами\n' + '-' * 30)

            print('Создвние таблиц с индексами')
            await db.create_tables_with_index()

            print('Генерация данных')
            await db.fill_with_random()

            print('JOIN с индексами')
            st = time.time()
            await db.make_join(True)
            print(f'Время: {time.time() - st} s\n')

            await db.drop_tables()
        except Exception as e:
            print(e)
            await db.drop_tables()


    loop = asyncio.get_event_loop()

    loop.run_until_complete(run())
