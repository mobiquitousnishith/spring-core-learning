create sequence seq_currency start with 1 cycle;
create sequence seq_brand start with 1 cycle;
create sequence seq_product start with 1 cycle;

create table currency
(
    id          bigint primary key default nextval('seq_currency'),
    name        varchar(3) not null,
    description varchar(512)
);

create table brand
(
    id          bigint primary key default nextval('seq_brand'),
    name        varchar(64) not null,
    description varchar(512)
);

create table product
(
    id          bigint primary key default nextval('seq_brand'),
    name        varchar(64)      not null,
    description varchar(512),
    price       double precision not null,
    brand_id    bigint           not null,
    currency_id bigint           not null,
    constraint uk_product_id_brand_id unique (name, brand_id),
    constraint fk_product_brand_id foreign key (brand_id) references brand (id),
    constraint fk_product_currency_id foreign key (currency_id) references currency (id)
);

insert into currency (id, name, description)
VALUES (1, 'INR', 'Indian Rupees'),
       (2, 'USD', 'US Dollar'),
       (3, 'GBP', 'Britain Pound'),
       (4, 'EUR', 'Euro');

select setval('seq_currency', (select max(id) + 1 from currency));

insert into brand (id, name, description)
values (1, 'Microsoft', 'Leading Personal Computer brand'),
       (2, 'Apple', 'Mac, iPad, iPhone manufacturer'),
       (3, 'Amazon', 'Kindle and home automation'),
       (4, 'Lenovo', 'Leading Personal Computer Hardware brand');

select setval('seq_brand', (select max(id) + 1 from brand));

insert into product (name, description, price, brand_id, currency_id)
values ('Surface Pro', 'Hybrid Computer', '999', 1, 2),
       ('Surface Laptop', 'Hybrid Computer', '1099', 1, 2),
       ('Surface Dial', 'Accessory', '89', 1, 2),
       ('Surface Studio', 'Hybrid Desktop Computer', '3999', 1, 2),
       ('iPad Pro 12 inches', 'Tablet device 12 inches screen', '899', 2, 2),
       ('iPad Pro 11 inches', 'Tablet device 11 inches screen', '799', 2, 2),
       ('iPad Air', 'Tablet device', '499', 2, 2),
       ('iPhone 13', null, '699', 2, 2),
       ('iPhone 13 Mini', null, '599', 2, 2),
       ('iPhone 13 Pro', null, '799', 2, 2),
       ('iPhone 13 Pro Max', null, '899', 2, 2);

select *
from product;