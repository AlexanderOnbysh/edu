
psql -d postgres -c "\copy (SELECT * FROM clients) TO clients.csv CSV HEADER DELIMITER ',';"
psql -d postgres -c "\copy (SELECT * FROM contracts) TO contracts.csv CSV HEADER DELIMITER ',';"
psql -d postgres -c "\copy (SELECT * FROM delivery) TO delivery.csv CSV HEADER DELIMITER ',';"
psql -d postgres -c "\copy (SELECT * FROM software) TO software.csv CSV HEADER DELIMITER ',';"
