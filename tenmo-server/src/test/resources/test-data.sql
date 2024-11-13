BEGIN TRANSACTION;
-- ROLLBACK;
DROP TABLE IF EXISTS budgets, expenses,incomes, account,tenmo_user;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_income_id,seq_expense_id,seq_budget_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_income_id
	INCREMENT BY 1
	START WITH 3001
	NO MAXVALUE;

CREATE TABLE incomes(
	income_id int not null default nextval('seq_income_id'),
	user_id int,
	amount numeric(12,2) default 0.00,
	source varchar(225),
	date DATE default current_date,
	CONSTRAINT pk_incomes PRIMARY KEY(income_id),
	CONSTRAINT fk_incomes foreign key (user_id) references tenmo_user(user_id)
);

CREATE SEQUENCE seq_expense_id
	INCREMENT BY 1
	START WITH 4001
	NO MAXVALUE;

CREATE TABLE expenses(
	expense_id int not null default nextval('seq_expense_id'),
	user_id int,
	amount numeric(12,2) default 0.00,
	category varchar(225),
	date DATE default current_date,
	CONSTRAINT pk_expenses PRIMARY KEY (expense_id),
	CONSTRAINT fk_expenses FOREIGN KEY (user_id) REFERENCES tenmo_user(user_id)
);

CREATE SEQUENCE seq_budget_id
	INCREMENT BY 1
	START WITH 5001
	NO MAXVALUE;

CREATE TABLE budgets(
	budget_id int not null default nextval('seq_budget_id'),
	user_id int,
	amount numeric(12,2) default 0.00,
	category varchar(225),
	month_year date default null,
	CONSTRAINT pk_budgets PRIMARY KEY (budget_id),
	CONSTRAINT fk_budgets FOREIGN KEY (user_id) REFERENCES tenmo_user(user_id)
);

COMMIT;