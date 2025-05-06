-- name: GetTrackingBySession :many
select *
from session_tracking
where session_tracking.session_id = ?
;

-- name: CreateTracking :exec
insert into session_tracking(
    latitude,
    longitude,
    altitude,
    time,
    session_id
) values (?, ?, ?, ?, ?);

