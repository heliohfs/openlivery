create table if not exists brand
(
    id bigserial not null constraint brand_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    brand_name text not null
);
