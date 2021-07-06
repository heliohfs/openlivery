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

    order_value_at_least decimal(15, 6),
    order_value_up_to decimal(15, 6),

    delivery_fee_at_least decimal(15, 6),
    delivery_fee_up_to decimal(15, 6),

    constraint claim_rule_claim_limit_greater_or_equals_to_claim_count check (claim_limit is null or claim_limit >= claim_count),
    constraint claim_rule_order_value_up_to_greater_than_order_value_at_least check (order_value_up_to is null or order_value_up_to > order_value_at_least),
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
    kind text not null,
    discount decimal(15,6) not null,

    constraint discount_kind_one_of check(kind in ('PERCENT_OFF', 'AMOUNT_OFF')),
    constraint discount_discount_percentage_discount_between_0_and_1 check (kind <> 'PERCENT_OFF' or discount between 0 and 1),
    constraint discount_discount_discount_is_positive check (discount >= 0)
);

create table if not exists order_discount
(
    discount_id bigint not null constraint order_discount_pkey primary key,
    max_discount_value decimal(15, 6),
    constraint order_discount_discount_fkey foreign key(discount_id) references discount on delete cascade,
    constraint order_discount_max_discount_value_positive check (max_discount_value is null or max_discount_value >= 0)
);

create table if not exists delivery_fee_discount
(
    discount_id bigint not null constraint delivery_fee_discount_pkey primary key,
    max_discount_value decimal(15, 6),
    constraint delivery_fee_discount_discount_fkey foreign key(discount_id) references discount on delete cascade,
    constraint delivery_fee_discount_max_discount_value_positive check (max_discount_value is null or max_discount_value >= 0)
);

create table if not exists product_discount
(
    discount_id bigint not null constraint product_discount_pkey primary key,
    product_id bigint not null constraint product_discount_product_fkey references product on delete cascade,
    constraint product_discount_discount_fkey foreign key(discount_id) references discount on delete cascade
);

create table if not exists order_discount_valid_product
(
    order_discount_id bigint not null constraint order_discount_valid_product_order_discount_fkey references order_discount on delete cascade,
    product_id bigint not null constraint order_discount_valid_product_product_fkey references product on delete cascade,
    constraint order_discount_valid_product_pkey primary key(order_discount_id, product_id)
);

create table if not exists delivery_fee_discount_valid_product
(
    delivery_fee_discount_id bigint not null constraint delivery_fee_discount_valid_product_delivery_fee_discount_fkey references delivery_fee_discount on delete cascade,
    product_id bigint not null constraint delivery_fee_discount_valid_product_product_fkey references product on delete cascade,
    constraint delivery_fee_discount_valid_product_pkey primary key(delivery_fee_discount_id, product_id)
);