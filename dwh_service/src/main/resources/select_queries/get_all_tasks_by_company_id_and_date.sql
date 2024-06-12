SELECT count()
FROM tasks_view
WHERE created_at >= '2024-06-11 13:59:27'
  AND created_at <= '2024-06-11 15:59:20';

SELECT count()
FROM tasks_view
WHERE company_id LIKE 'b24842d4-91ae-463b-ac83-50179208d04e'
  AND created_at >= '2024-06-11 00:00:00'
  AND created_at <= '2024-06-12 00:00:00'
