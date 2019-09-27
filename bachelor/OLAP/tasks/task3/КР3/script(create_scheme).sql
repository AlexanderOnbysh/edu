create user TASK3
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
grant connect to TASK3;
grant resource to TASK3;
grant unlimited tablespace to TASK3;