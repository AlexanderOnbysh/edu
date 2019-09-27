-- Create the user 
create user TASK4
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
-- Grant/Revoke role privileges
grant connect to TASK4;
grant resource to TASK4;
-- Grant/Revoke system privileges
grant unlimited tablespace to TASK4;


-- Create the user 
create user TASK4_2
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
-- Grant/Revoke role privileges
grant connect to TASK4_2;
grant resource to TASK4_2;
-- Grant/Revoke system privileges
grant unlimited tablespace to TASK4_2;