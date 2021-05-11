create table brand
(
    id bigserial not null constraint brand_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    brand_name text
);

create table product
(
    id bigserial not null constraint product_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    picture_storage_key text,
    item_code text,
    brand_id bigint constraint product_brand_fkey references brand on delete set null,
    product_name text,
    base_price decimal(15,6),
    description text,

    constraint product_item_code_key unique (item_code)
);

create table campaign
(
    id bigserial not null constraint campaign_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    campaign_name text,
    banner_picture_storage_key text,
    delivery_fee_decimal_discount decimal(3,2) constraint campaign_delivery_fee_decimal_discount_between_0_and_1 check (delivery_fee_decimal_discount between 0.00 and 1.00),
    start_datetime timestamp not null,
    end_datetime timestamp not null constraint campaign_start_datetime_lower_than_end_datetime check (
        start_datetime > end_datetime
    )
);

create table campaign_product
(
    decimal_discount decimal(3,2) constraint campaign_product_decimal_discount_between_0_and_1 check (decimal_discount between 0.00 and 1.00),
    campaign_id bigint not null constraint campaign_product_campaign_fkey references campaign on delete cascade,
    product_id bigint not null constraint campaign_product_product_fkey references product on delete cascade,
    constraint campaign_product_pkey primary key(campaign_id, product_id)
);

create table address
(
    id bigserial not null constraint address_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    street_number numeric,
    building_name text,
    street_number_suffix text,
    street_name text,
    street_type text,
    street_direction varchar(2),
    address_type text,
    address_type_identifier text,
    local_municipality text,
    city_name text,
    governing_district text,
    postal_area text,
    country text,
    additional_info text,
    latitude decimal(8,6),
    longitude decimal(9,6)
);

create table distributor
(
    id bigserial not null constraint distributor_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    company_name text,
    trade_name text,
    company_document text
);

create table distributor_branch
(
    id bigserial not null constraint distributor_branch_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    distributor_id bigint not null constraint distributor_branch_distributor_fkey references distributor on delete cascade,
    branch_name text
);

create table distributor_branch_contact
(
    id bigserial not null constraint distributor_branch_contact_pkey primary key,
    active boolean not null,
    changed_date_time timestamp not null,
    created_date_time timestamp not null,
    version bigint,

    distributor_branch_id bigint not null constraint distributor_branch_contact_distributor_branch_fkey references distributor_branch on delete cascade,
    contact_type text not null,
    contact_value text not null
);

create table distributor_branch_address
(
    distributor_branch_id bigint not null constraint distributor_branch_address_distributor_branch_fkey references distributor_branch on delete cascade,
    address_id bigint not null constraint distributor_branch_address_address_fkey references distributor_branch on delete cascade,
    constraint distributor_branch_address_pkey primary key(distributor_branch_id, address_id)
);

