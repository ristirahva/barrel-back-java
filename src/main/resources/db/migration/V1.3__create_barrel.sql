create table public.barrel (
 	id SERIAL PRIMARY KEY,
 	wood_id INT REFERENCES wood(id),
        cooper_id INT REFERENCES cooper(id),
	volume int NOT NULL,
	description varchar(255) NULL
);