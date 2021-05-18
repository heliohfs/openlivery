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