from flask import Flask, escape, request

import psycopg2
try:
    connection = psycopg2.connect(user = "master",
                                  password = "Re69m3RT8SYdxcwg",
                                  host = "ucu-test-instance.cmhsvjeftch3.eu-central-1.rds.amazonaws.com",
                                  port = "5432",
                                  database = "master")

    cursor = connection.cursor()
    # Print PostgreSQL Connection properties
    print ( connection.get_dsn_parameters(),"\n")

    # Print PostgreSQL version
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS account(
       user_id serial PRIMARY KEY,
       username VARCHAR (50) UNIQUE NOT NULL,
       password VARCHAR (50) NOT NULL);
    """)
except (Exception, psycopg2.Error) as error :
    print ("Error while connecting to PostgreSQL", error)
    #closing database connection.
    if(connection):
        cursor.close()
        connection.close()
        print("PostgreSQL connection is closed")

app = Flask(__name__)

@app.route('/')
def hello():
    name = request.args.get("name", "World")
    return f'Hello, {escape(name)}!'

@app.route('/table')
def table():
    cursor  = connection.cursor()
    cursor.execute('SELECT * FROM account;')
    return str(list(cursor))

@app.route('/add')
def add():
    username = request.args.get("username", "admin")
    password = request.args.get("password", "password123")
    cursor  = connection.cursor()
    cursor.execute(f"INSERT INTO account (username, password) VALUES ('{username}', '{password}') ON CONFLICT DO NOTHING;")
    return 'Success!'
