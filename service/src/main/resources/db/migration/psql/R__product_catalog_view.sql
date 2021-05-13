create or replace view product_catalog as
    select
        prod.*,
        round(prod.base_price - (max(cp_prod.decimal_discount) * prod.base_price), 6) as final_price,
        max(cp_prod.decimal_discount) as decimal_discount,
        max(cp.delivery_fee_decimal_discount) as delivery_fee_decimal_discount
    from campaign cp
        join campaign_product cp_prod
            on cp.id = cp_prod.campaign_id
        right join product prod
            on prod.id = cp_prod.product_id and
                cp.active = true and
                cp.omit <> true and
                current_timestamp at time zone 'utc' between start_datetime and end_datetime
    where prod.active is true
    group by prod.id;

