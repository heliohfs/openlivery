create table if not exists campaign
(
    id bigserial not null constraint campaign_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    campaign_name text,
    description text,
    banner_picture_storage_key text,
    start_datetime timestamp with time zone not null default current_timestamp,
    end_datetime timestamp with time zone,
    discount_strategy text not null,

    constraint campaign_discount_strategy_one_of check (discount_strategy in ('COUPON', 'CATALOG', 'FIRST_ORDER')),
    constraint campaign_start_datetime_lower_than_end_datetime check (end_datetime is null or start_datetime < end_datetime)
);

create table if not exists campaign_discount
(
    id bigserial not null constraint campaign_discount_pkey primary key,
    campaign_id bigint not null constraint campaign_discount_campaign_fkey references campaign on delete cascade,
    product_id bigint constraint campaign_discount_product_fkey references product on delete cascade,
    discount_type text not null,
    decimal_discount decimal(3,2) not null default 0.00,
    max_order_discount decimal(15,6),
    min_order_value decimal(15, 6),

    constraint campaign_discount_one_of check (discount_type in ('ORDER', 'DELIVERY_FEE', 'PRODUCT')),
    constraint campaign_discount_product_type_requires_product_id check (discount_type <> 'PRODUCT' or product_id is not null),
    constraint campaign_discount_decimal_discount_between_0_and_1 check (decimal_discount between 0.00 and 1.00)
);

create table if not exists campaign_coupon
(
    id bigserial not null constraint campaign_coupon_pkey primary key,
    campaign_id bigint not null constraint campaign_coupon_campaign_fkey references campaign on delete cascade,
    active boolean not null default true,
    code text not null constraint campaign_coupon_code_key unique,
    auth_required boolean not null default false,
    application_limit_by_user bigint,
    application_limit bigint,
    application_count bigint not null default 0,

    constraint application_count_not_greater_than_limit check (application_limit is null or application_count <= application_limit)
);