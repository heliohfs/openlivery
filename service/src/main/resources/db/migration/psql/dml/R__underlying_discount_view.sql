create or replace view underlying_discount as select discount.*
    from discount
        inner join campaign
            on discount.campaign_id = campaign.id and
                campaign.active = true
        inner join claim_rule
            on discount.claim_rule_id = claim_rule.id and
                (claim_rule.claim_limit is null or
                    claim_rule.claim_count < claim_rule.claim_limit) and
                (claim_rule.in_between_campaign_period = false or
                    current_timestamp at time zone 'utc' between start_datetime and end_datetime)
    where discount.active = true and discount.coupon_code is null
    group by discount.id;
