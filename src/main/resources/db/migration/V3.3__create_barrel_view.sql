CREATE VIEW barrel_view AS
SELECT b.id, b.volume, b.description, b.is_archived, 
c.id cooper_id, c.name cooper_name, 
w.id wood_id, w.name wood_name, 
COUNT(bh) fill_count, 
TO_CHAR(AGE(COALESCE(MAX(bh.date_end), current_date), MIN(bh.date_start)), 'YY "г." MM "мес." DD "д."') fill_duration 
FROM barrel b 
INNER JOIN cooper c on c.id = b.cooper_id
INNER JOIN wood w on w.id = b.wood_id
LEFT JOIN barrel_history bh ON bh.barrel_id = b.id 
GROUP BY (b.id, volume, b.description, c.id, c.name, w.id, w.name) 
ORDER BY b.id;
