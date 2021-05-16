create table if not exists system_parameters
(
    min_delivery_fee_value decimal(15, 6) not null default 0.00,
    delivery_fee_value_per_unit decimal(15, 6) not null default 0.00,
    min_delivery_fee_distance_threshold int not null default 0
);

create table if not exists brand
(
    id bigserial not null constraint brand_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    brand_name text not null
);

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

create table if not exists category
(
    id bigserial not null constraint category_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    category_name text not null,
    constraint category_category_name_key unique(category_name)
);

create table if not exists product_category
(
    product_id bigint not null constraint product_category_product_fkey references product on delete cascade,
    category_id bigint not null constraint product_category_category_fkey references category on delete cascade,
    constraint product_category_pkey primary key(product_id, category_id)
);

create table if not exists campaign
(
    id bigserial not null constraint campaign_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    campaign_name text,
    banner_picture_storage_key text,
    coupon_code text,
    omit boolean not null constraint hidden_campaign_requires_coupon_code check (omit = false or coupon_code is not null) default false,
    start_datetime timestamp with time zone not null,
    end_datetime timestamp with time zone not null constraint campaign_start_datetime_lower_than_end_datetime check (
        start_datetime < end_datetime
    ),
    delivery_fee_decimal_discount decimal(3,2) constraint campaign_delivery_fee_decimal_discount_between_0_and_1 check (
        delivery_fee_decimal_discount between 0.00 and 1.00
    ),
    constraint campaign_coupon_code unique(coupon_code)
);

create table if not exists campaign_product
(
    decimal_discount decimal(3,2) constraint campaign_product_decimal_discount_between_0_and_1 check (decimal_discount between 0.00 and 1.00),
    campaign_id bigint not null constraint campaign_product_campaign_fkey references campaign on delete cascade,
    product_id bigint not null constraint campaign_product_product_fkey references product on delete cascade,
    constraint campaign_product_pkey primary key(campaign_id, product_id)
);

create table if not exists distributor
(
    id bigserial not null constraint distributor_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    business_start_hour time with time zone not null,
    business_end_hour time with time zone not null,
    company_name text not null,
    trade_name text not null,
    company_document text not null
);

create table if not exists distributor_address
(
    id bigserial not null constraint distributor_address_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    distributor_id bigint constraint distributor_address_distributor_fkey references distributor on delete cascade,
    latitude decimal(8,6),
    longitude decimal(9,6),
    street_name text,
    street_number numeric,
    additional_info text,
    city_name text,
    governing_district text,
    country text
);

create table if not exists distributor_contact
(
    id bigserial not null constraint distributor_contact_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    distributor_id bigint not null constraint distributor_contact_distributor_fkey references distributor on delete cascade,
    contact_type text not null,
    contact_value text not null
);

create table if not exists customer
(
    id bigserial not null constraint customer_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    oauth_id text,
    ref_code text not null,
    complete_name text not null,
    email text not null,
    cellphone text not null,
    id_number text not null,
    balance decimal(15,6) constraint customer_balance_zero_or_greater check(balance >= 0) default 0.00
);

create table if not exists customer_address
(
    id bigserial not null constraint customer_address_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    customer_id bigint constraint customer_address_customer_fkey references customer on delete cascade,
    latitude decimal(8,6),
    longitude decimal(9,6),
    street_name text,
    street_number numeric,
    additional_info text,
    city_name text,
    governing_district text,
    country text
);

alter table customer add column if not exists default_address_id bigint constraint customer_default_address_address_fkey references customer_address on delete set null;

create table if not exists "order"
(
    id bigserial not null constraint order_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    customer_rating int constraint order_customer_rating_depends_on_status check(
        status = 'FINISHED'
    ),
    deliveryman_rating int constraint order_deliveryman_rating_depends_on_status check(
        status = 'FINISHED'
    ),
    customer_rating_reason text constraint order_customer_rating_reason_depends_on_rating check (
        customer_rating is not null
    ),
    deliveryman_rating_reason text constraint order_deliveryman_rating_reason_depends_on_rating check (
        deliveryman_rating is not null
    ),
    notes text,
    customer_id bigint constraint order_customer_fkey references customer on delete set null,
    customer_address_id bigint constraint order_customer_address_fkey references customer_address on delete set null,
    distributor_id bigint constraint order_distributor_fkey references distributor on delete set null,
    status text not null constraint order_status_one_of check (
        status in (
            'CANCELED',
            'INCOMPLETE',
            'PLACED',
            'TIMEOUT',
            'ACCEPTED',
            'ON_ROUTE',
            'FINISHED'
        )
    ) default 'PLACED',
    non_completion_reason text constraint order_non_completion_reason_depends_on_status check (
        status in ('CANCELED', 'INCOMPLETE')
    )
);

create table if not exists order_product
(
    order_id bigint not null constraint order_products_order_fkey references "order",
    product_id bigint not null constraint order_products_product_fkey references product on delete cascade,
    amount int not null constraint order_product_positive_non_zero_amount check(amount > 0),
    constraint order_product_pkey primary key(order_id, product_id)
);
