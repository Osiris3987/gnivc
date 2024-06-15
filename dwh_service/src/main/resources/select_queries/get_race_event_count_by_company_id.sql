SELECT COUNT(DISTINCT rv.id)
FROM races_view as rv
         INNER JOIN race_events_view as rev ON rv.id = rev.race_id
         INNER JOIN tasks_view as tv ON rv.task_id = tv.id
WHERE rev.event_type = 'COMPLETED'
  AND tv.company_id = 'b24842d4-91ae-463b-ac83-50179208d04e'
  AND rev.created_at BETWEEN '2024-06-10 13:59:27' AND '2024-06-13 15:59:20';

SELECT COUNT(DISTINCT rv.id)
FROM races_view as rv
         INNER JOIN race_events_view as rev ON rv.id = rev.race_id
         INNER JOIN tasks_view as tv ON rv.task_id = tv.id
WHERE rev.event_type = 'COMPLETED'
  AND tv.company_id = 'b24842d4-91ae-463b-ac83-50179208d04e'
  AND rev.created_at BETWEEN '2024-06-10 13:59:27' AND '2024-06-13 15:59:20';

explain SELECT count()
FROM races_view r
    JOIN race_events_view re ON r.id = re.race_id
    JOIN (
    SELECT re2.race_id, max(re2.created_at) as created_at
    FROM race_events_view re2
    GROUP BY re2.race_id
    ) b on b.created_at = re.created_at
    LEFT JOIN tasks_view as t ON r.task_id = t.id
    WHERE re.event_type = 'CREATED' AND t.company_id = 'b24842d4-91ae-463b-ac83-50179208d04e' AND re.created_at BETWEEN '2024-05-10 13:59:27' AND '2024-06-14 15:59:20';
;


SELECT
    r.id AS race_id,
    re.event_type,
    re.created_at
FROM
    races_view r
        JOIN race_events_view re ON r.id = re.race_id
ORDER BY
    re.created_at DESC;

SELECT re2.race_id, max(re2.created_at) as created_at
FROM race_events_view re2
group by re2.race_id



