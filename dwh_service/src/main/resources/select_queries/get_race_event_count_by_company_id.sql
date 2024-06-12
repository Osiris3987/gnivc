SELECT COUNT(DISTINCT rv.id)
FROM races_view as rv
         INNER JOIN race_events_view as rev ON rv.id = rev.race_id
         INNER JOIN tasks_view as tv ON rv.task_id = tv.id
WHERE rev.event_type = 'COMPLETED'
  AND tv.company_id = 'b24842d4-91ae-463b-ac83-50179208d04e'
  AND rev.created_at BETWEEN '2024-06-10 13:59:27' AND '2024-06-13 15:59:20';