BEGIN TRANSACTION;
-- ROLLBACK;
DROP TABLE IF EXISTS budgets, expense_category,expenses,income_source,incomes, account,tenmo_user;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id,seq_income_source_id,seq_income_id,seq_expense_category_id,seq_expense_id,seq_budget_id;

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

CREATE SEQUENCE seq_income_source_id
	INCREMENT BY 1
	START WITH 6001
	NO MAXVALUE;


CREATE TABLE income_source(
	source_id int not null default nextval('seq_income_source_id'),
	name varchar(225)
);

CREATE SEQUENCE seq_income_id
	INCREMENT BY 1
	START WITH 3001
	NO MAXVALUE;

CREATE TABLE incomes(
	income_id int not null default nextval('seq_income_id'),
	user_id int,
	amount numeric(12,2) default 0.00,
	source_id int,
	date DATE default current_date,
	CONSTRAINT pk_incomes PRIMARY KEY(income_id),
	CONSTRAINT fk_incomes foreign key (user_id) references tenmo_user(user_id)
);

CREATE SEQUENCE seq_expense_category_id
	INCREMENT BY 1
	START WITH 7001
	NO MAXVALUE;


CREATE TABLE expense_category(
	category_id int not null default nextval('seq_expense_category_id'),
	name varchar(225)
);

CREATE SEQUENCE seq_expense_id
	INCREMENT BY 1
	START WITH 4001
	NO MAXVALUE;

CREATE TABLE expenses(
	expense_id int not null default nextval('seq_expense_id'),
	user_id int,
	amount numeric(12,2) default 0.00,
	category_id int,
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
	month_year date default null,
	CONSTRAINT pk_budgets PRIMARY KEY (budget_id),
	CONSTRAINT fk_budgets FOREIGN KEY (user_id) REFERENCES tenmo_user(user_id)
);

-- Insert sample income_source
INSERT INTO income_source(source_id,name)VALUES(6001,'Salary');
INSERT INTO income_source(source_id,name)VALUES(6002,'Freelance');
INSERT INTO income_source(source_id,name)VALUES(6003,'Investment');
INSERT INTO income_source(source_id,name)VALUES(6004,'Gifts');

-- Insert sample expense_category
INSERT INTO expense_category(category_id,name)VALUES(7001,'Housing');
INSERT INTO expense_category(category_id,name)VALUES(7002,'Food');
INSERT INTO expense_category(category_id,name)VALUES(7003,'Transportation');
INSERT INTO expense_category(category_id,name)VALUES(7004,'Shopping');

-- Insert sample users
INSERT INTO tenmo_user (username, password_hash, role)
VALUES
    ('user1', 'user1', 'user'),
    ('user2', 'user2', 'user'),
    ('admin', 'admin', 'admin');

-- Insert sample accounts
INSERT INTO account (user_id, balance)
VALUES
    (1001, 1000.00),
    (1002, 2500.00),
    (1003, 5000.00);

-- Insert sample incomes
INSERT INTO incomes (user_id, amount, source_id, date)
VALUES
    (1001, 3000.00, 6001, '2024-01-15'),
    (1002, 1500.00, 6002, '2024-02-01'),
    (1001, 200.00, 6004, '2024-02-05'),
    (1003, 2500.00, 6003, '2024-03-10');

-- Insert sample expenses with restricted categories
INSERT INTO expenses (user_id, amount, category_id, date)
VALUES
    (1001, 150.00, 7002, '2024-01-16'),
    (1002, 75.00, 7004, '2024-02-05'),
    (1003, 1200.00, 7001, '2024-03-01'),
    (1001, 45.00, 7004, '2024-01-20');

-- Insert sample budgets with restricted categories
INSERT INTO budgets (user_id, amount, month_year)
VALUES
    (1001, 1000.00, '2024-01-01'),
    (1002, 1500.00, '2024-02-01'),
    (1003, 2000.00, '2024-03-01');

COMMIT;