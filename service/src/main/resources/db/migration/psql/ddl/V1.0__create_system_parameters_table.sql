create table if not exists system_parameters
(
    id smallint not null primary key check(id = 1) default 1,
    version bigint default 1,
    min_delivery_fee_value decimal(15, 6) not null default 0.00,
    delivery_fee_value_per_unit decimal(15, 6) not null default 0.00,
    min_delivery_fee_distance_threshold int not null default 0,
    maximum_delivery_radius int not null default 5
);
