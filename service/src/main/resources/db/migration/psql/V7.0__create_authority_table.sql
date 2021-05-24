create table if not exists authority
(
    id bigserial not null constraint authority_pkey primary key,
    authority_name text not null constraint authority_name_key unique
);