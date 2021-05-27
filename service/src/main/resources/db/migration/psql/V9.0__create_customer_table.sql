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
    phone_number text not null
);

alter table customer add column if not exists customer_data_id bigint not null constraint customer_customer_data_fkey references customer_data on delete cascade;

create table if not exists customer_address
(
    customer_id bigint not null constraint customer_address_user_fkey references customer on delete cascade,
    address_id bigint not null constraint customer_address_address_fkey references address on delete cascade,
    constraint user_address_pkey primary key(customer_id, address_id)
);