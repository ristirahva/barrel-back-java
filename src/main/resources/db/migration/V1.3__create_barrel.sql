-- CREATE TYPE barrel_burn_level AS ENUM ('слабый', 'средний', 'сильный');

create table public.barrel (
 	id SERIAL PRIMARY KEY,
 	wood_id INT NOT NULL REFERENCES wood(id),
        cooper_id INT NOT NULL REFERENCES cooper(id),
--        burn_level barrel_burn_level NOT NULL,
        burn_level VARCHAR NOT NULL CHECK(burn_level IN ('слабый', 'средний', 'сильный')), 
	volume int NOT NULL,
	description varchar(255) NULL,
	is_archived boolean NOT NULL DEFAULT false,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);