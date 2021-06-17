create table if not exists product
(
    id bigserial not null constraint product_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    picture_storage_key text,
    item_code text,
    brand_id bigint constraint product_brand_fkey references brand on delete set null,
    product_name text not null,
    base_price decimal(15,6) not null,
    description text,

    constraint product_item_code_key unique (item_code)
);

create table if not exists product_category
(
    product_id bigint not null constraint product_category_product_fkey references product on delete cascade,
    category_id bigint not null constraint product_category_category_fkey references category on delete cascade,
    constraint product_category_pkey primary key(product_id, category_id)
);
