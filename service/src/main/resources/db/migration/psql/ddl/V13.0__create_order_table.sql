create table if not exists "order"
(
    id bigserial not null constraint order_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    order_code text constraint order_code_key unique,

    customer_data_id bigint constraint order_customer_data_fkey references customer_data on delete set null,
    deliveryman_id bigint constraint order_deliveryman_user_fkey references "user" on delete set null,
    distributor_id bigint constraint order_distributor_fkey references distributor on delete set null,
    delivery_address_id bigint constraint order_delivery_address_fkey references address on delete set null,

    customer_rating int constraint order_customer_rating_depends_on_status check(customer_rating is null or status = 'FINISHED'),
    deliveryman_rating int constraint order_deliveryman_rating_depends_on_status check(deliveryman_rating is null or status = 'FINISHED'),
    customer_rating_reason text constraint order_customer_rating_reason_depends_on_rating check (customer_rating_reason is null or customer_rating is not null),
    deliveryman_rating_reason text constraint order_deliveryman_rating_reason_depends_on_rating check (deliveryman_rating_reason is null or deliveryman_rating is not null),

    coupon_applied text constraint order_coupon_applied_fkey references coupon on delete set null,

    delivery_fee decimal(15,6) not null,

    notes text,

    status text not null constraint order_status_one_of check (
        status in (
            'CANCELED',
            'INCOMPLETE',
            'PLACED',
            'TIMEOUT',
            'ACCEPTED',
            'ON_ROUTE',
            'ARRIVED',
            'FINISHED'
        )
    ) default 'PLACED',

    non_completion_reason text constraint order_non_completion_reason_depends_on_status check (
        non_completion_reason is null or status in ('CANCELED', 'INCOMPLETE')
    )
);


create table if not exists order_product
(
    order_id bigint not null constraint order_product_order_fkey references "order" on delete cascade,
    product_id bigint not null constraint order_product_product_fkey references product on delete cascade,
    amount int not null,
    price decimal(15, 6) not null,

    constraint order_product_pkey primary key(order_id, product_id)
);