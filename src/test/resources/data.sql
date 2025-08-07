INSERT INTO clients (name)
VALUES ('Gabriel Antico'),
       ('Lionel Messi'),
       ('Diego Maradona'),
       ('Cristiano Ronaldo'),
       ('Kylian Mbappé'),
       ('Harry Kane'),
       ('Vinícius Júnior'),
       ('Angel Di María'),
       ('Erling Haaland');

INSERT INTO sellers (name)
VALUES ('Luciana Aymar'),
       ('Agustina Gorzelany'),
       ('Zoe Díaz'),
       ('Eugenia Trinchinetti'),
       ('Agustina Albertario'),
       ('Xan de Waard');

INSERT INTO products (description, unitary_price)
VALUES('AMD RYZEN 5 4600G', 130000),
      ('AMD RYZEN 7 4700', 150000),
      ('AMD RYZEN 5 5600G', 150000),
      ('AMD RYZEN 5 5600X', 165000),
      ('AMD RYZEN 5 5600XT', 170000),
      ('AMD RYZEN 7 5800X3D', 300000),
      ('AMD RYZEN 9 5950X', 350000);


INSERT INTO sales (client_id, seller_id, total)
VALUES (1, 1, 750000),
       (2, 2, 300000),
       (3, 3, 165000),
       (4, 4, 170000),
       (5, 5, 300000),
       (6, 6, 350000),
       (7, 1, 150000);

INSERT INTO sales_details (product_id, cuantity, price, subtotal, sale_id)
VALUES (6, 1, 300000, 300000, 1),
       (7, 1, 350000, 350000, 1),
       (2, 1, 150000, 150000, 2),
       (3, 1, 150000, 150000, 2),
       (4, 1, 165000, 165000, 3),
       (5, 1, 170000, 170000, 4),
       (6, 1, 300000, 300000, 5),
       (7, 1, 350000, 350000, 6),
       (2, 1, 150000, 150000, 7);