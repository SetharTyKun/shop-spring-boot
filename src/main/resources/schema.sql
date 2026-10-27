-- Table: customer, order, order_line, product
-- customer 1 ──── * orders 1 ──── * order_line * ──── 1 product

-- PRODUCT
CREATE TABLE IF NOT EXISTS product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL CHECK (stock >= 0)
);

-- CUSTOMER
CREATE TABLE IF NOT EXISTS customer (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL
);

-- ORDERS
CREATE TABLE IF NOT EXISTS orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_id BIGINT NOT NULL REFERENCES customer(id),
  status VARCHAR(20) NOT NULL,
  placed_at TIMESTAMP NOT NULL,
  total DECIMAL(10,2) NOT NULL
);

-- ORDER_LINE
CREATE TABLE IF NOT EXISTS order_line (
  order_id BIGINT NOT NULL REFERENCES orders(id),
  product_id BIGINT NOT NULL REFERENCES product(id),
  qty INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (order_id, product_id)
);