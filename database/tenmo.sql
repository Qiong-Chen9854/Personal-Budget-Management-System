BEGIN TRANSACTION;
--ROLLBACK;
DROP TABLE IF EXISTS budgets, expenses,income, accounts,users;

CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	username varchar(200) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50),	
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE TABLE accounts(
	account_id serial PRIMARY KEY,
	user_id int,
	 balance NUMERIC(12, 2) DEFAULT 100.00,
	CONSTRAINT fk_accounts_user_id foreign key (user_id) references users(user_id)
);

CREATE TABLE incomes(
	income_id serial PRIMARY KEY,
	user_id int,
	amount numeric(12,2) default 0.00,
	source varchar(225),
	date DATE default current_date,
	CONSTRAINT fk_incomes_user_id foreign key (user_id) references users(user_id)
);

CREATE TABLE expenses(
	expense_id serial PRIMARY KEY,
	user_id int,
	amount numeric(12,2) default 0.00,
	category varchar(225),
	date DATE default current_date
);

CREATE TABLE budgets(
	budget_id serial PRIMARY KEY,
	user_id int,
	amount numeric(12,2) default 0.00,
	category varchar(225),
	month_year date default null
);

COMMIT;