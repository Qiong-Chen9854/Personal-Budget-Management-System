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
INSERT INTO incomes (user_id, amount, source, date)
VALUES
    (1001, 3000.00, 'Salary', '2024-01-15'),
    (1002, 1500.00, 'Freelance', '2024-02-01'),
    (1001, 200.00, 'Gift', '2024-02-05'),
    (1003, 2500.00, 'Investment', '2024-03-10');

-- Insert sample expenses with restricted categories
INSERT INTO expenses (user_id, amount, category, date)
VALUES
    (1001, 150.00, 'Food', '2024-01-16'),
    (1002, 75.00, 'Entertainment', '2024-02-05'),
    (1003, 1200.00, 'Housing', '2024-03-01'),
    (1001, 45.00, 'Transportation', '2024-01-20');

-- Insert sample budgets with restricted categories
INSERT INTO budgets (user_id, amount, category, month_year)
VALUES
    (1001, 1000.00, 'Monthly Budget', '2024-01-01'),
    (1002, 1500.00, 'Monthly Budget', '2024-02-01'),
    (1003, 2000.00, 'Monthly Budget', '2024-03-01');

COMMIT;