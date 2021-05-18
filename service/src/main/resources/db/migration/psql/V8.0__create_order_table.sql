create table if not exists "order"
(
    id bigserial not null constraint order_pkey primary key,
    active boolean not null default true,
    changed_date_time timestamp not null default current_timestamp,
    created_date_time timestamp not null default current_timestamp,
    version bigint default 1,

    customer_rating int constraint order_customer_rating_depends_on_status check(
        status = 'FINISHED'
    ),
    deliveryman_rating int constraint order_deliveryman_rating_depends_on_status check(
        status = 'FINISHED'
    ),
    customer_rating_reason text constraint order_customer_rating_reason_depends_on_rating check (
        customer_rating is not null
    ),
    deliveryman_rating_reason text constraint order_deliveryman_rating_reason_depends_on_rating check (
        deliveryman_rating is not null
    ),
    notes text,
    customer_id bigint constraint order_customer_fkey references customer on delete set null,
    customer_address_id bigint constraint order_customer_address_fkey references customer_address on delete set null,
    distributor_id bigint constraint order_distributor_fkey references distributor on delete set null,
    status text not null constraint order_status_one_of check (
        status in (
            'CANCELED',
            'INCOMPLETE',
            'PLACED',
            'TIMEOUT',
            'ACCEPTED',
            'ON_ROUTE',
            'FINISHED'
        )
    ) default 'PLACED',
    non_completion_reason text constraint order_non_completion_reason_depends_on_status check (
        status in ('CANCELED', 'INCOMPLETE')
    )
);

create table if not exists order_product
(
    order_id bigint not null constraint order_products_order_fkey references "order",
    product_id bigint not null constraint order_products_product_fkey references product on delete cascade,
    amount int not null constraint order_product_positive_non_zero_amount check(amount > 0),
    constraint order_product_pkey primary key(order_id, product_id)
);