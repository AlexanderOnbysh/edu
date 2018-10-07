-- Create the user 
create user TASK5
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
-- Grant/Revoke role privileges
grant connect to TASK5;
grant resource to TASK5;
-- Grant/Revoke system privileges
grant unlimited tablespace to TASK5;

create user TASK5_2
identified by "OLAP"
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
-- Grant/Revoke role privileges
grant connect to TASK5_2;
grant resource to TASK5_2;
-- Grant/Revoke system privileges
grant unlimited tablespace to TASK5_2;