CREATE TABLE IF NOT EXISTS product (
  id bigint AUTO_INCREMENT  PRIMARY KEY,
  description varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  price float(20) NOT NULL,
  quantity integer NOT NULL
);