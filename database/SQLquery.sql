BEGIN TRANSACTION;
ROLLBACK;

SELECT *
FROM tenmo_user

SELECT *
FROM incomes
WHERE user_id = 1001

SELECT *
FROM account

SELECT *
FROM budgets

SELECT expense_id, user_id, amount, category_id, date
FROM expenses
WHERE user_id = 1001

Delete from incomes
where user_id = 1001

Delete from expenses
where user_id = 1001
F

Delete from budgets
where user_id = 1001

SELECT *
FROM income_source

SELECT *
FROM expense_category

INSERT INTO income_source(source_id,name)VALUES(6001,'Salary');
INSERT INTO income_source(source_id,name)VALUES(6002,'Freelance');
INSERT INTO income_source(source_id,name)VALUES(6003,'Investment');
INSERT INTO income_source(source_id,name)VALUES(6004,'Gifts');

INSERT INTO expense_category(category_id,name)VALUES(7001,'Housing');
INSERT INTO expense_category(category_id,name)VALUES(7002,'Food');
INSERT INTO expense_category(category_id,name)VALUES(7003,'Transportation');
INSERT INTO expense_category(category_id,name)VALUES(7004,'Shopping');





-- create a new income
INSERT INTO incomes(user_id,amount,source_id,date)
VALUES(1001,200,6002,'2024-11-14') RETURNING income_id;

UPDATE account
SET balance = balance + 200
WHERE user_id = 1001;

-- create a new expense
INSERT INTO expenses(user_id,amount,category_id,date)
VALUES(1001,100,7002,'2024-11-18') RETURNING expense_id

INSERT INTO expenses(user_id,amount,category_id,date)
VALUES(1001,20,7002,'2024-11-22') RETURNING expense_id

INSERT INTO budgets(user_id, amount, month_year)
VALUES(1001,200,'2024-11-01') RETURNING budget_id

UPDATE account
SET balance = balance - 100
WHERE user_id = 1001



SELECT income_id, user_id, amount, source_id, date
FROM incomes
WHERE income_id = 3001

SELECT expense_id, user_id, amount, category_id, date
FROM expenses
WHERE expense_id = 4001

SELECT amount,month_year
FROM budgets
WHERE month_year = '2024-11-01'




SELECT TO_CHAR(b.month_year, 'YYYY-MM') AS month_year,
    	b.amount AS total_budget,
    	COALESCE(SUM(e.amount), 0) AS total_expenses
FROM budgets b
LEFT JOIN expenses e ON b.user_id = e.user_id 
          AND TO_CHAR(b.month_year, 'YYYY-MM') = TO_CHAR(e.date, 'YYYY-MM')
WHERE TO_CHAR(b.month_year, 'YYYY-MM') = '2024-11' AND user_id = 1001
GROUP BY b.month_year, b.amount;

SELECT name
FROM expense_category
WHERE category_id = 7001


