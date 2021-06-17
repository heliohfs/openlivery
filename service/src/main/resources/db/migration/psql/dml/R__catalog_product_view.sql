create or replace view catalog_product as
    select
        prod.*,
        coalesce(round(prod.base_price - (max(cp_prod.decimal_discount) * prod.base_price), 6), prod.base_price) as final_price,
        max(cp_prod.decimal_discount) as decimal_discount,
        (case max(cp_prod.decimal_discount) when null then false else true end) as discount_applied
    from campaign cp
        join campaign_discount cp_prod
            on cp.id = cp_prod.campaign_id and
                cp_prod.discount_type = 'PRODUCT'
        right join product prod
            on prod.id = cp_prod.product_id and
                cp.active = true and
                cp.discount_strategy = 'CATALOG' and
                (cp.end_datetime is null or current_timestamp at time zone 'utc' between start_datetime and end_datetime)
    where prod.active is true
    group by prod.id;

