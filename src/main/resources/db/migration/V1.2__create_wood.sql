create table public.wood (
 	id SERIAL PRIMARY KEY,
	name VARCHAR(64) UNIQUE NOT NULL,
	name_lat VARCHAR(64) NULL
);
