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
    phone_number text not null,
    email text not null,
    balance decimal(15,6) constraint customer_balance_zero_or_greater check(balance >= 0) default 0.00,
    constraint customer_email_key unique (email)
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
