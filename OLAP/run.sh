docker run -d --name OracleXE --shm-size=8g -p 1522:1521 -p 8080:8080 -e ORACLE_PWD=password oracle/database:11.2.0.2-xe
