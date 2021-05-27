create table if not exists "user"
(
    id bigserial not null constraint user_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    oauth_id text not null,

    constraint user_oauth_id_key unique(oauth_id)
);

create table if not exists user_authority
(
    user_id bigint constraint user_authority_user_fkey references "user" on delete cascade,
    authority_id bigint constraint user_authority_authority_fkey references authority on delete cascade,
    constraint user_authority_pkey primary key(user_id, authority_id)
);