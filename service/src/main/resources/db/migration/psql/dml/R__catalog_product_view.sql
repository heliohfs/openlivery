create or replace view catalog_product as select product.*,
    (case discount.discount_type when 'PERCENT_OFF'
        then coalesce(round(product.base_price - (discount.discount * product.base_price), 6), product.base_price)
        else coalesce(case when product.base_price - discount.discount <= 0 then 0 else round(product.base_price - discount.discount, 6) end, product.base_price)
    end) as final_price,
    discount.discount_type as discount_type,
    discount.discount as discount,
    (case when discount.discount is null then false else true end) as discount_applied
from product
    left join lateral (
        select campaign_id, product_id, discount_type, discount
        from campaign_discount discount
            right join campaign
                on campaign.id = discount.campaign_id and
                campaign.active = true and
                (campaign.end_datetime is null or current_timestamp at time zone 'utc' between start_datetime and end_datetime)
        where discount.product_id = product.id and discount.active = true and discount.access_by = 'CATALOG'
        order by (case discount_type when 'PERCENT_OFF' then 0 else 1 end), discount desc
        limit 1
    ) as discount on true
where product.active = true;