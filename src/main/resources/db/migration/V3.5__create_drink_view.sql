create view drink_view as
WITH ranked_drinks AS (
    SELECT 
        d.id, 
        d.source, 
        d.name, 
        d.alcohol, 
        d.description, 
        bh.date_end,
        ROW_NUMBER() OVER(PARTITION BY d.id ORDER BY bh.date_end DESC) AS rn
    FROM drink d
    LEFT JOIN barrel_history bh ON d.id = bh.drink_id
)
SELECT 
    id, source, name, alcohol, description, date_end
FROM ranked_drinks
WHERE rn = 1
ORDER BY date_end DESC;