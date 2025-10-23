create table if not exists payment_execution (
	id bigserial primary key,
	status varchar(255),
	amount numeric,
	account_number varchar(255)
);
