CREATE VIEW barrel_view AS
SELECT b.id, b.volume, b.description, c.id cooper_id, c.name cooper_name, w.id wood_id, w.name wood_name, COUNT(*) fill_count, SUM(COALESCE(dib.date_end - dib.date_start, current_date - dib.date_start)) fill_duration 
FROM barrel b, cooper c, wood w, barrel_history dib 
WHERE b.cooper_id = c.id AND b.wood_id = w.id AND dib.barrel_id=b.id 
GROUP BY (b.id, volume, b.description, c.id, c.name, w.id, w.name) 
ORDER BY b.id;
