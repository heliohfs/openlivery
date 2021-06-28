insert into authority(authority_name) values
    ('WRITE_ORDER'),
    ('WRITE_PRODUCT'),
    ('READ_PRODUCTS'),
    ('READ_CATEGORIES'),
    ('READ_BRANDS'),
    ('READ_CATALOG')
on conflict do nothing;
