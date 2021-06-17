create table if not exists deliveryman
(
    user_id bigint not null constraint deliveryman_pkey primary key references "user" on delete cascade,
    complete_name text not null,
    phone_number text not null
);
