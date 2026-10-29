INSERT INTO product(name, price, stock) VALUES
  ('Laptop', 899.99, 10), ('Mouse', 19.99, 50), ('Keyboard', 49.50, 30),
  ('Monitor', 189.00, 15), ('Headphones', 59.90, 25)
ON CONFLICT (name) DO NOTHING;

INSERT INTO customer(name) SELECT 'Alice' WHERE NOT EXISTS (SELECT 1 FROM customer WHERE name = 'Alice');
INSERT INTO customer(name) SELECT 'Bob' WHERE NOT EXISTS (SELECT 1 FROM customer WHERE name = 'Bob');