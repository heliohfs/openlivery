create table if not exists campaign
(
    id bigserial not null constraint campaign_pkey primary key,
    active boolean not null default false,
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
    active boolean not null default false
);

create table if not exists claim_rule
(
    id bigserial not null constraint claim_rule_pkey primary key,

    in_between_campaign_period boolean not null default false,
    requires_auth boolean not null default true,
    max_days_since_registration_date int,

    claim_count int not null default 0,
    claim_limit int,
    claim_limit_by_user int,
    constraint claim_rule_claim_limit_greater_or_equals_to_claim_count check (claim_limit is null or claim_limit >= claim_count),

    order_value_at_least decimal(15, 6),
    order_value_up_to decimal(15, 6),
    constraint claim_rule_order_value_up_to_greater_than_order_value_at_least check (order_value_up_to is null or order_value_up_to > order_value_at_least),

    delivery_fee_at_least decimal(15, 6),
    delivery_fee_up_to decimal(15, 6),
    constraint claim_rule_delivery_fee_up_to_greater_than_delivery_fee_at_least check (delivery_fee_up_to is null or delivery_fee_up_to > delivery_fee_at_least)
);

create table if not exists discount
(
    id bigserial not null constraint discount_pkey primary key,
    active boolean not null default false,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    campaign_id bigint not null constraint discount_campaign_fkey references campaign on delete cascade,
    coupon_code text constraint discount_coupon_fkey references coupon on delete cascade,
    claim_rule_id bigint constraint discount_claim_rule_fkey references claim_rule on delete cascade,

    apply_to text not null,
    discount_type text not null,
    discount decimal(15,6) not null,
    max_order_discount_value decimal(15, 6),

    product_id bigint constraint discount_product_fkey references product on delete cascade,

    constraint discount_campaign_product_key unique(campaign_id, product_id),

    constraint discount_apply_to_one_of check(apply_to in ('ORDER', 'DELIVERY_FEE', 'PRODUCT')),
    constraint discount_discount_type_one_of check(discount_type in ('PERCENT_OFF', 'AMOUNT_OFF')),
    constraint discount_max_order_discount_value_only_if_discount_type_is_percent_off check(max_order_discount_value is null or discount_type = 'PERCENT_OFF'),
    constraint discount_max_order_discount_value_only_if_apply_to_order check(max_order_discount_value is null or apply_to = 'ORDER'),

    constraint discount_no_unnecessary_product_id check(apply_to = 'PRODUCT' or product_id is null),
    constraint discount_apply_to_product_requires_product_id check(apply_to <> 'PRODUCT' or product_id is not null),
    constraint discount_discount_percentage_discount_between_0_and_1 check (discount_type <> 'PERCENT_OFF' or discount between 0 and 1),
    constraint discount_discount_discount_is_positive check (discount >= 0)
);