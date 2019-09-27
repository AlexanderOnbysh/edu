-- COMMON TASKS
-- З використанням агрегуючих функцій та конструкції HAVING. 
SELECT software.id, software.title, COUNT(*) as count FROM software
	JOIN contracts
	ON software.id = contracts.software_id
	GROUP BY software.id
	HAVING COUNT(*) >= 30
	ORDER BY count ASC;


-- З перетворенням типу даних результату запиту або формату дати.
SELECT id, to_char(current_timestamp, 'HH12:MI:SS'), contract_id from delivery;

-- З пошуком по фрагменту текстового поля. (Ех: знайти всі прізвища, що закінчуються на «ко») 

SELECT id, name, surname from clients
WHERE name LIKE '%ko';

-- зі вставкою будь-якого значення в поле результату запиту, що набуло значення NULL в результаті запиту (Ех: якщо прізвище «AUDI», що купив, - NULL, поставити символ «_» оператор NVL або CASE ).  
SELECT CASE WHEN software.version='' THEN 'no version' ELSE software.version END FROM software;




-- Task 1
-- На яку програму було складено найбільшу кількість договорів.
SELECT software.id, software.title, COUNT(*) as count FROM software
	JOIN contracts
	ON software.id = contracts.software_id
	GROUP BY software.id
	ORDER BY count DESC
	LIMIT 1;

-- Task 2
-- На яку суму було складено договори на дату “Дата”
CREATE OR REPLACE FUNCTION price_on(DATE)
  RETURNS TABLE(sum NUMERIC)
  AS
  $$
  SELECT SUM(price) FROM contracts
  JOIN software
  ON contracts.software_id = software.id
  WHERE contracts.date BETWEEN $1 AND $1 + interval '1 day';
  $$
  LANGUAGE SQL;
  
SELECT * FROM price_on('2017-9-8');

-- Task 3
-- Хто з клієнтів не мав договору на поставку програми “Назва програми” Список за алфавітом.
CREATE OR REPLACE FUNCTION dont_buy(VARCHAR(255))
  RETURNS TABLE(id INT, client_id INT, name VARCHAR(255), surname VARCHAR(255))
  AS 
  $$ 
  SELECT DISTINCT clients.id, clients.client_id, name, surname FROM clients
  JOIN contracts ON clients.id = contracts.client_id
  JOIN software  ON contracts.software_id = software.id
  WHERE software.title != $1
  ORDER BY name, surname; 
  $$
  LANGUAGE SQL;
  
SELECT * FROM dont_buy('Vel itaque.');

-- task 4
-- Поставки за якими договорами не були виконані
SELECT * FROM delivery
JOIN contracts
ON delivery.contract_id = contracts.id
WHERE delivery.date IS NULL;

-- task 5
-- Які програми не були поставлені за договорами
SELECT contracts.id, contracts.date, client_id, software_id FROM contracts
LEFT JOIN delivery
ON contracts.id = delivery.contract_id
WHERE delivery.id IS NULL;