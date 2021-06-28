create table if not exists address
(
    id bigserial not null constraint address_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    latitude decimal(8,6),
    longitude decimal(9,6),
    street_name text not null,
    street_number numeric not null,
    additional_info text,
    city_name text not null,
    governing_district text not null,
    country text not null
);