BEGIN TRANSACTION;
ROLLBACK;

SELECT *
FROM incomes
WHERE user_id = 1001

SELECT *
FROM account

SELECT *
FROM expenses

Delete from incomes
where user_id = 1001


-- create a new income
INSERT INTO incomes(user_id,amount,source,date)
VALUES(1001,200,'Freelance','2024-11-14') RETURNING income_id;

UPDATE account
SET balance = balance + 200
WHERE user_id = 1001;

-- create a new expense
INSERT INTO expenses(user_id,amount,category,date)
VALUES(1001,100,'Food','2024-11-18') RETURNING expense_id

UPDATE account
SET balance = balance - 100
WHERE user_id = 1001



SELECT income_id, user_id, amount, source, date
FROM incomes
WHERE income_id = 3001

SELECT expense_id, user_id, amount, category, date
FROM expenses
WHERE expense_id = 4001



