create table public.drink (
 	id SERIAL PRIMARY KEY,
        source varchar(64) NOT NULL,
        name varchar(32) NOT NULL, 
 	alcohol INT NOT NULL,
	description varchar(255) NULL,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);