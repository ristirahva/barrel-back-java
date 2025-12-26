CREATE VIEW barrel_history_view AS
SELECT b.id AS barrel_id, b.volume as barrel_volume, d.id as drink_id, d.name as drink_name, w.id as wood_id, w.name as wood_name, 
db.date_start, db.date_end, db.alcohol_start, db.alcohol_end, COALESCE(db.date_end - db.date_start, current_date - db.date_start) duration, db.description
FROM barrel_history db, drink d, barrel b, wood w
WHERE db.drink_id = d.id AND db.barrel_id = b.id AND b.wood_id = w.id
ORDER BY b.id, db.date_start;
