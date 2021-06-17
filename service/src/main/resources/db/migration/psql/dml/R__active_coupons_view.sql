create or replace view active_coupon as
    select
        cp_coupon.id,
        cp_coupon.code,
        cp_coupon.application_limit_by_user,
        cp_coupon.campaign_id
    from campaign_coupon cp_coupon
        join campaign cp
            on cp.id = cp_coupon.campaign_id
    where cp.active = true and
        cp_coupon.active = true and
        (cp_coupon.application_limit is null or cp_coupon.application_limit > cp_coupon.application_count) and
        (cp.end_datetime is null or current_timestamp at time zone 'utc' between cp.start_datetime and cp.end_datetime);
