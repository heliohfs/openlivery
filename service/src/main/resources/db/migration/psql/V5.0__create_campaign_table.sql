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