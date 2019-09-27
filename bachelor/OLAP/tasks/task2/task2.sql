-- Вивести сумарну вартість поставки по кожному продукту.
SELECT
  p."product_name",
  SUM(inv_d."quantity" * p."price") TOTAL_PRICE
FROM invoice inv
  JOIN invoice_detail inv_d ON
                              inv."id_invoice_detail" = inv_d."id_invoice"
  JOIN product p ON
                   p."id_product" = inv_d."id_product"
GROUP BY p."product_name";

-- Визначити, товари якого постачальник жодного разу не купувались.
SELECT "supplier_name"
FROM supplier
MINUS
SELECT DISTINCT s."supplier_name"
FROM invoice inv
  JOIN invoice_detail invdet ON
                               inv."id_invoice_detail" = invdet."id_invoice"
  JOIN product p ON
                   p."id_product" = invdet."id_product"
  JOIN supplier s ON
                    s."id_supplier" = p."id_supplier";

-- Визначити, які товари ніколи не продаються у другому півріччі року.
SELECT DISTINCT
  p."product_name",
  inv."purchase_time"
FROM invoice inv
  JOIN invoice_detail invdet
    ON inv."id_invoice_detail" = invdet."id_invoice"
  JOIN product p ON
                   p."id_product" = invdet."id_product"
WHERE EXTRACT(month from inv."purchase_time") not between 6 and 12;

-- Визначити 5 покупців, що найчастіше відвідують магазин.
SELECT
     c."name",
     COUNT(*) AS cnt_inv
   FROM invoice i
     JOIN client c ON c."id_client" = i."id_client"
   GROUP BY i."id_client", c."name"
having count(*) >=
(SELECT min(cnt_inv)
FROM
  (SELECT unique
     COUNT(*) AS cnt_inv
   FROM invoice i
     JOIN client c ON c."id_client" = i."id_client"
   GROUP BY i."id_client", c."name"
   ORDER BY cnt_inv DESC)
where rownum <= 5);


SELECT cnt_inv
FROM
  (SELECT unique
     COUNT(*) AS cnt_inv
   FROM invoice i
     JOIN client c ON c."id_client" = i."id_client"
   GROUP BY i."id_client", c."name"
   ORDER BY cnt_inv DESC)
where rownum <= 5;
-- Визначити працівника, що продає найбільше товарів.
SELECT q1.*
FROM
  (
    SELECT DISTINCT
      i."id_stuff"         stuff,
      SUM(idet."quantity") summa
    FROM invoice i
      JOIN invoice_detail idet ON
                                 i."id_invoice_detail" = idet."id_invoice"
    GROUP BY i."id_stuff"
  ) q1,
  (
    SELECT MAX(q2.summa) max_quant
    FROM
      (
        SELECT DISTINCT
          i."id_stuff"         stuff,
          SUM(idet."quantity") summa
        FROM invoice i
          JOIN invoice_detail idet ON
                                     i."id_invoice_detail" = idet."id_invoice"
        GROUP BY i."id_stuff"
      ) q2
  ) q3
WHERE q1.summa = q3.max_quant;

-- Визначити 3 працівника, що оформили найбільше чеків.
SELECT *
FROM
  (
    SELECT
      i."id_stuff"        STUFF,
      COUNT(i."id_stuff") AMOUNT_RECEIPT
    from invoice i
    GROUP BY i."id_stuff"
    ORDER BY AMOUNT_RECEIPT desc
  );

-- Яка суму в середньому сплачує покупець за одне відвідування магазину.
SELECT
  client,
  round(sum_inv_price / cnt_inv, 2) average
FROM
  (
    SELECT
      i."id_client"                  AS client,
      COUNT(*)                       AS cnt_inv,
      SUM(p."price" * id."quantity") AS sum_inv_price
    FROM invoice i
      JOIN invoice_detail id
        ON i."id_invoice_detail" = id."id_invoice"
      JOIN product p
        ON id."id_product" = p."id_product"
    GROUP BY "id_client"
  );

-- Скільки покупок оформлює кожен продавець за день
SELECT
  q2.stuff1,
  round(q2.sum_purch / q2.cnt_days) as avg_purch_amount
FROM
  (
    SELECT
      q1.stuff    stuff1,
      COUNT(*)    cnt_days,
      SUM(q1.cnt) sum_purch
    FROM
      (
        SELECT
          "id_stuff" stuff,
          COUNT(*)   cnt
        FROM invoice
        GROUP BY "id_stuff", trunc("purchase_time")
      ) q1
    GROUP BY q1.stuff
  ) q2;

-- Визначити які полиці зберігають товари лише одного типу.
SELECT
  st,
  sh
FROM
  (
    SELECT
      s."id_store" st,
      s."shelf"    sh,
      COUNT(*)     pr_types,
      pt."product_type_name"
    FROM store s
      JOIN product p ON
                       s."id_product" = p."id_product"
      JOIN product_type pt
        ON p."id_product_type" = pt."id_product_type"
    GROUP BY s."id_store", s."shelf", pt."product_type_name"
  )
GROUP BY st, sh
HAVING COUNT(*) = 1;

-- Визначити товари, що мають найбільшу ціну та найчастіше продаються.
SELECT
  q1.id_prod,
  q1.price,
  q1.cnt_inv
FROM
  (
    SELECT
      id."id_product"      id_prod,
      COUNT(*)             cnt_inv,
      p."price"            price,
      COUNT(*) * p."price" mult
    FROM invoice i
      JOIN invoice_detail id
        ON i."id_invoice_detail" = id."id_invoice"
      JOIN product p
        ON id."id_product" = p."id_product"
    GROUP by id."id_product", p."price"
    ORDER by price DESC, cnt_inv
  ) q1,
  (
    SELECT MAX(q2.mult) max_mult
    FROM
      (
        SELECT
          id."id_product"      id_prod,
          COUNT(*)             cnt_inv,
          p."price"            price,
          COUNT(*) * p."price" mult
        FROM invoice i
          JOIN invoice_detail id
            ON i."id_invoice_detail" = id."id_invoice"
          JOIN product p
            ON id."id_product" = p."id_product"
        GROUP BY id."id_product", p."price"
      ) q2
  ) q3
WHERE q1.mult = q3.max_mult;