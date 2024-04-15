create table authors(
	id serial primary key, 
	name varchar(30) not null,
	biography varchar(200)
);

create table books(
	id serial primary key, 
	name varchar(30) not null,
	description varchar(100),
	author_id integer,
	foreign key (author_id) references authors(id)
);

create table users(
    id serial primary key,
    email varchar(20) unique not null,
    password varchar(30) not null
);
