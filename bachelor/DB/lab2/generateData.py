from faker import Faker
import asyncio

class GenData:
    def __init__(self):
        self.faker: Faker = Faker()

    def gen_contracts(self, n: int):
        query = []
        for _ in range(n):
            surname = self.faker.last_name()
            software_id = self.faker.random_int()
            date = self.faker.date_this_century(before_today=True, after_today=False)

            query.append(f"INSERT INTO contracts (surname, software_id, date) "
                         f"VALUES ('{surname}', '{software_id}', '{date}')")

        return ';'.join(query)

    def gen_client(self, n: int):
        query = []
        for _ in range(n):
            client_id = self.faker.random_int()
            name = self.faker.first_name()
            surname = self.faker.last_name()
            address = self.faker.address()
            company = self.faker.company()

            query.append(f"INSERT INTO clients (client_id, name, surname, address, company) "
                         f"VALUES ('{client_id}', '{name}', '{surname}', '{address}', '{company}')")

        return ';'.join(query)

async def clear_buffer(b):
    if not b:
        await asyncio.sleep(3.43)
