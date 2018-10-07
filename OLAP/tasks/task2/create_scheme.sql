create user TASK2
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
grant connect to TASK2;
grant resource to TASK2;
grant unlimited tablespace to TASK2;