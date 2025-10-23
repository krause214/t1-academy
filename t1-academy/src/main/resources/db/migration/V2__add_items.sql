create table if not exists products (
	id bigserial primary key,
	account_number varchar(255) unique,
	balance numeric,
	product_type varchar(255),
	user_id integer not null,
	constraint fk_user foreign key (user_id) references users(id)
);

insert into products (account_number, balance, product_type, user_id)
values ('NUMBER_ONE_1', 1000000, 'ACCOUNT',
    (select id from users u where u.username = 'Pupa')
);

insert into products (account_number, balance, product_type, user_id)
values ('NUMBER_TWO_2', 2000000, 'CARD',
    (select id from users u where u.username = 'Pupa')
);

