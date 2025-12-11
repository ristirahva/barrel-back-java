create table public.drink_in_barrel (
 	barrel_id INT NOT NULL,
 	drink_id INT NOT NULL,
 	date_start TIMESTAMP,
 	date_end TIMESTAMP,
 	alcohol_start INT,
 	alcohol_end INT,
	description varchar(255) NULL,
	primary key(barrel_id, drink_id)
);