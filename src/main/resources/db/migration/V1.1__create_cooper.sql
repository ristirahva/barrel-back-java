create table public.cooper (
 	id SERIAL PRIMARY KEY,
	name VARCHAR(64) UNIQUE NOT NULL,
	URL VARCHAR(64) NULL
);
