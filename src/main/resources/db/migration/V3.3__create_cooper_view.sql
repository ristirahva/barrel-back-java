create view cooper_view as
SELECT c.id, c.name, c.url, count(b) barrel_count 
FROM cooper c 
LEFT JOIN barrel b ON c.id = b.cooper_id 
group by c.id 
ORDER BY c.name;
