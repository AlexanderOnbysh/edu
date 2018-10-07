-- Зробити task 1 без LIMIT
SELECT software.id, software.title, COUNT(*) FROM software
	JOIN contracts
	ON software.id = contracts.software_id
	GROUP BY software.id
	HAVING COUNT(*) = 
		(SELECT COUNT(*) as count FROM software
		JOIN contracts
		ON software.id = contracts.software_id
		GROUP BY software.id
		ORDER BY count DESC
		LIMIT 1);


-- Вибрати поставки договори яких були укладены в попередьому місяці
CREATE OR REPLACE FUNCTION previous(DATE)
  RETURNS TABLE(id INT, date TIMESTAMP, contract_id INT)
  AS
  $$
  SELECT delivery.id, delivery.date, delivery.contract_id FROM delivery
  JOIN contracts
  ON delivery.contract_id = contracts.id
  WHERE contracts.date BETWEEN $1 - INTERVAL '1 month' AND $1;
  $$
  LANGUAGE SQL;
  
SELECT * FROM previous('2017-1-8');