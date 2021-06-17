create table if not exists administrator
(
    user_id bigint not null constraint administrator_pkey primary key references "user" on delete cascade,
    complete_name text not null,
    phone_number text not null
);