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

    constraint campaign_start_datetime_lower_than_end_datetime check (end_datetime is null or start_datetime < end_datetime)
);

create table if not exists coupon
(
    code text not null constraint coupon_pkey primary key,
    active boolean not null default true,
    application_count bigint not null default 0,
    application_limit bigint,
    application_limit_by_user bigint,
    allow_anonymous boolean default false,
    campaign_id bigint not null constraint coupon_campaign_fkey references campaign on delete cascade,

    constraint coupon_application_limit_is_positive check (application_limit is null or application_limit >= 0),
    constraint coupon_application_limit_by_user_is_positive check (application_limit is null or application_limit >= 0)
);

create table if not exists campaign_discount
(
    id bigserial not null constraint campaign_discount_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    campaign_id bigint not null constraint campaign_discount_campaign_fkey references campaign on delete cascade,
    apply_to text not null, -- ORDER || DELIVERY_FEE || PRODUCT
    access_by text not null, -- COUPON || CATALOG || FIRST_ORDER || ALL_ORDERS
    discount_type text not null, -- PERCENT_OFF || AMOUNT_OFF
    discount decimal(15,6) not null,

--  access_by = 'COUPON'
    coupon text constraint campaign_discount_coupon_fkey references coupon on delete set null,

--  apply_to = 'PRODUCT'
    product_id bigint constraint campaign_discount_product_fkey references product on delete cascade,

    constraint campaign_discount_campaign_product_key unique(campaign_id, product_id),

    constraint campaign_discount_access_by_one_of check(access_by in ('COUPON', 'CATALOG', 'FIRST_ORDER', 'ALL_ORDERS')),
    constraint campaign_discount_apply_to_one_of check(apply_to in ('ORDER', 'DELIVERY_FEE', 'PRODUCT')),
    constraint campaign_discount_discount_type_one_of check(discount_type in ('PERCENT_OFF', 'AMOUNT_OFF')),

    constraint campaign_discount_no_unnecessary_coupon check(access_by = 'COUPON' or coupon is null),
    constraint campaign_discount_access_by_coupon_requires_coupon check(access_by <> 'COUPON' or coupon is not null),
    constraint campaign_discount_no_unnecessary_product_id check(apply_to = 'PRODUCT' or product_id is null),
    constraint campaign_discount_apply_to_product_requires_product_id check(apply_to <> 'PRODUCT' or product_id is not null),
    constraint campaign_discount_access_by_catalog_only_applies_to_product check(access_by <> 'CATALOG' or apply_to = 'PRODUCT'),

    constraint campaign_discount_discount_percentage_discount_between_0_and_1 check (discount_type <> 'PERCENT_OFF' or discount between 0 and 1),
    constraint campaign_discount_discount_discount_is_positive check (discount >= 0)
);