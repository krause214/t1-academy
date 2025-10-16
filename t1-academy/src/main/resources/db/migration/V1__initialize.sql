create table if not exists users (
	id bigserial primary key,
	username varchar(255) unique
);

insert into users (username) values ('Andrey');
insert into users (username) values ('Pupa');
insert into users (username) values ('Lupa');
insert into users (username) values ('abvgd');
insert into users (username) values ('PUPUPU');
insert into users (username) values ('UserName');
insert into users (username) values ('User');
insert into users (username) values ('Petr');
insert into users (username) values ('HihiHaha');