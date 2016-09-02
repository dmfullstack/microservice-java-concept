CREATE TABLE store (
  store_id     BIGINT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(200) NOT NULL,
  PRIMARY KEY (store_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE product (
  product_id  BIGINT         NOT NULL AUTO_INCREMENT,
  store_id    BIGINT         NOT NULL,
  name        VARCHAR(200)   NOT NULL,
  description VARCHAR(500)   NOT NULL,
  sku         VARCHAR(10)    NOT NULL,
  price       DECIMAL(20, 2) NOT NULL,
  PRIMARY KEY (product_id),
  FOREIGN KEY (store_id) REFERENCES store(store_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE stock (
  product_id BIGINT NOT NULL,
  store_id   BIGINT NOT NULL,
  count      BIGINT NOT NULL,
  PRIMARY KEY (product_id, store_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id),
  FOREIGN KEY (store_id)   REFERENCES store(store_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE orders (
  order_id   BIGINT       NOT NULL AUTO_INCREMENT,
  store_id   BIGINT       NOT NULL,
  order_date TIMESTAMP    NOT NULL,
  status     INT          NOT NULL,
  first_name VARCHAR(200) NOT NULL,
  last_name  VARCHAR(200) NOT NULL,
  email      VARCHAR(200) NOT NULL,
  phone      VARCHAR(20)  NOT NULL,
  PRIMARY KEY (order_id),
  FOREIGN KEY (store_id)   REFERENCES store(store_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;

CREATE TABLE order_details (
  details_id  BIGINT NOT NULL AUTO_INCREMENT,
  order_id    BIGINT NOT NULL,
  product_id  BIGINT NOT NULL,
  count       BIGINT NOT NULL,
  PRIMARY KEY (details_id),
  FOREIGN KEY (order_id)   REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1;
