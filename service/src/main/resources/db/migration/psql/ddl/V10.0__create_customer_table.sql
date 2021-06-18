create table if not exists customer
(
    user_id bigint not null constraint customer_pkey primary key references "user" on delete cascade,
    default_address_id bigint constraint customer_default_address_fkey references address on delete set null,
    balance decimal(15,6) default 0
);

create table if not exists customer_data
(
    id bigserial not null constraint customer_data_pkey primary key,
    complete_name text not null,
    phone_number text not null,
    identity_number text not null constraint customer_data_identity_number_key unique
);

create table if not exists customer_coupon
(
    customer_identity_number text not null constraint customer_coupon_customer_fkey references customer_data(identity_number),
    coupon_code text not null constraint customer_coupon_coupon_code_fkey references campaign_coupon(code),
    application_count bigint not null default 1,
    constraint customer_coupon_pkey primary key(customer_identity_number, coupon_code)
);

alter table customer add column if not exists customer_data_id bigint not null constraint customer_customer_data_fkey references customer_data on delete cascade;

create table if not exists customer_address
(
    customer_id bigint not null constraint customer_address_user_fkey references customer on delete cascade,
    address_id bigint not null constraint customer_address_address_fkey references address on delete cascade,
    constraint user_address_pkey primary key(customer_id, address_id)
);