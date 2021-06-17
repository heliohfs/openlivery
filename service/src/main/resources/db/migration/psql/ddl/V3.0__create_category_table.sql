create table if not exists category
(
    id bigserial not null constraint category_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    category_name text not null,
    constraint category_category_name_key unique(category_name)
);
