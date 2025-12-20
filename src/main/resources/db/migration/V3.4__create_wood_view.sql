CREATE VIEW wood_view AS
SELECT w.id, w.name, name_lat, count(b) barrel_count
FROM wood w
LEFT JOIN barrel b ON b.wood_id = w.id
GROUP BY w.id
ORDER BY w.name;