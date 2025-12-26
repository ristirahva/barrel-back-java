CREATE VIEW cooper_view AS
SELECT c.id, c.name, c.url, count(b) barrel_count 
FROM cooper c 
LEFT JOIN barrel b ON c.id = b.cooper_id 
GROUP BY c.id 
ORDER BY c.name;
