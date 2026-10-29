DELETE FROM order_line;
DELETE FROM orders;
UPDATE product SET stock = 10 WHERE id = 1;
UPDATE product SET stock = 5 WHERE id = 2;
UPDATE product SET stock = 20 WHERE id = 3;