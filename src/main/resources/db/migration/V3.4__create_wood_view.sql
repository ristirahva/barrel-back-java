create view wood_view as
SELECT w.id, w.name, name_lat, count(b) barrel_count
FROM wood w
left join barrel b on b.wood_id = w.id
group by w.id
order by w.name;