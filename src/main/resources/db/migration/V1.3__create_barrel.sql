create table public.barrel (
 	id SERIAL PRIMARY KEY,
 	wood_id INT NOT NULL REFERENCES wood(id),
        cooper_id INT NOT NULL REFERENCES cooper(id),
	volume int NOT NULL,
	description varchar(255) NULL,
	is_archived boolean NOT NULL DEFAULT false,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);