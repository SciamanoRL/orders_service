CREATE TABLE orders(
                    id BIGSERIAL PRIMARY KEY,
                    order_number  VARCHAR(255) NOT NULL,
                    total_amount  BIGINT NOT NULL,
                    customer_name VARCHAR(255) NOT NULL ,
                    address       VARCHAR(255) NOT NULL ,
                    delivery_type VARCHAR(255),
                    payment_type  VARCHAR(255),
                    order_date    DATE
);

CREATE TABLE detail_order (
                          id BIGSERIAL PRIMARY KEY ,
                          product_article BIGINT NOT NULL,
                          product_name VARCHAR(255) NOT NULL,
                          product_amount INT NOT NULL,
                          product_price INT NOT NULL,
                          order_no BIGINT,
                          CONSTRAINT fk_order
                              FOREIGN KEY (order_no)
                                  REFERENCES orders (id)
                                  ON DELETE SET NULL
);