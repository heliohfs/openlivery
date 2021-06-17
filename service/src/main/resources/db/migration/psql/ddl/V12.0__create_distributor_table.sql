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

create table if not exists distributor_manager
(
    user_id bigint not null constraint distributor_manager_pkey primary key references "user" on delete cascade,
    distributor_id bigint not null constraint distributor_manager_distributor_fkey references distributor on delete cascade
);

create table if not exists distributor_operator
(
    user_id bigint not null constraint distributor_operator_pkey primary key references "user" on delete cascade,
    distributor_id bigint not null constraint distributor_operator_distributor_fkey references distributor on delete cascade
);

create table if not exists distributor_address
(
    distributor_id bigint not null constraint distributor_address_distributor_fkey references distributor,
    address_id bigint not null constraint distributor_address_address_fkey references address,
    constraint distributor_address_pkey primary key(distributor_id, address_id)
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