create table if not exists system_parameters
(
    min_delivery_fee_value decimal(15, 6) not null default 0.00,
    delivery_fee_value_per_unit decimal(15, 6) not null default 0.00,
    min_delivery_fee_distance_threshold int not null default 0,
    maximum_delivery_radius int not null default 5
);
