BEGIN TRANSACTION;
-- ROLLBACK;

CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	user_name varchar(200) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50),	
);

CREATE TABLE accounts(
	account_id serial PRIMARY KEY,
	user_id int,
	 balance NUMERIC(12, 2) DEFAULT 100.00
	CONSTRAINT fk_accounts_user_id foreign key (user_id) references users(user_id)
);




COMMIT;