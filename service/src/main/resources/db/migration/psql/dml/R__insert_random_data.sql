insert into brand(brand_name) values
    ('Skol'),
    ('Heineken'),
    ('Petra'),
    ('Imperial'),
    ('Brahma'),
    ('Stela Artois'),
    ('Corona');


insert into product(
    picture_storage_key,
    item_code,
    brand_id,
    product_name,
    base_price,
    description
) values
    ('skol_269', '105923291822', 1, 'Skol 269ml', 1.50, ''),
    ('skol_350', '205223223422', 1, 'Skol 350ml', 2.60, ''),
    ('skol_600', '305223223422', 1, 'Skol 600ml', 4.50, ''),
    ('heineken_600', '412312512322', 2, 'Heineken 269ml', 3.21, ''),
    ('heineken_600', '521312312521', 2, 'Heineken 350ml', 4.50, ''),
    ('heineken_600', '723412412324', 2, 'Heineken 600ml', 7.49, ''),
    ('petra_269', '805923291822', 3, 'Petra 269ml', 1.50, ''),
    ('petra_350', '905223223422', 3, 'Petra 350ml', 2.60, ''),
    ('petra_600', '105223223422', 3, 'Petra 600ml', 4.50, ''),
    ('imperial_269', '115923291822', 4, 'Imperial 269ml', 1.50, ''),
    ('imperial_350', '125223223422', 4, 'Imperial 350ml', 2.60, ''),
    ('imperial_600', '135223223422', 4, 'Imperial 600ml', 4.50, ''),
    ('brahma_269', '145923291822', 5, 'Brahma 269ml', 1.50, ''),
    ('brahma_350', '155223223422', 5, 'Brahma 350ml', 2.60, ''),
    ('brahma_600', '165223223422', 5, 'Brahma 600ml', 4.50, ''),
    ('stela_600', '175223223422', 6, 'Stela Artois 355ml', 4.50, ''),
    ('corona_600', '185223223422', 7, 'Corona 355ml', 4.50, '');


insert into campaign(
	campaign_name,
	start_datetime,
	end_datetime
) values
    ('Campanha Atual', current_timestamp, current_timestamp + interval '1' day),
    ('Campanha Futura', current_timestamp + interval '1' day, current_timestamp + interval '2' day),
    ('Campanha Antiga', current_timestamp - interval '2' day, current_timestamp - interval '1' day);


insert into category(category_name) values
    ('Cerveja'),
    ('Vinho'),
    ('Destilados'),
    ('Energéticos');


insert into product_category(product_id, category_id) values
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 1);


insert into campaign_discount(
	campaign_id,
	apply_to,
	access_by,
	discount_type,
	discount,
	product_id
) values
	(1, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.1, 1),
	(1, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.2, 2),
	(1, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.3, 3),
	(1, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.4, 4),
	(1, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.5, 5),
	(2, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.6, 6),
	(2, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.7, 7),
	(2, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.8, 8),
	(2, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.9, 9),
	(2, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.10, 10),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.11, 11),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.12, 12),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.13, 13),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.14, 14),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.15, 15),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.16, 16),
	(3, 'PRODUCT', 'CATALOG', 'PERCENT_OFF', 0.17, 17);


insert into distributor(
    business_start_hour,
    business_end_hour,
    company_name,
    trade_name,
    company_document
) values
    ('10:00', '23:59', 'Bebidas LTDA', 'Hélio Beer', '24612392942013'),
    ('10:00', '23:59', 'Bebidas LTDA', 'João Beer', '24312392942013');


insert into address(
    street_number,
    street_name,
    country,
    city_name,
    governing_district,
    latitude,
    longitude
) values
    ('5884', 'Rua Cliente 1', 'Brasil', 'Campo Grande', 'MS', '22.124123124', '11.12412312'),
    ('423', 'Rua Cliente 2', 'Brasil', 'Campo Grande', 'MS', '22.124123124', '11.12412312');

insert into distributor_address(
    distributor_id,
    address_id
) values
    (1, 1),
    (2, 2);

insert into distributor_contact(
    distributor_id,
    contact_type,
    contact_value
) values
    (1, 'PHONE', '+5567996791800'),
    (1, 'EMAIL', 'helio_hfs@hotmail.com'),
    (2, 'PHONE', '+5567994723343');

