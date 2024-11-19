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



INSERT INTO incomes(user_id,amount,source,date)
VALUES(1001,200,'Freelance','2024-11-14') RETURNING income_id;

UPDATE account
SET balance = balance + 200
WHERE user_id = 1001;

INSERT INTO expenses(user_id,amount,category,date)
VALUES(1001,100,'Food','2024-11-18') RETURNING expense_id



SELECT income_id, user_id, amount, source, date
FROM incomes
WHERE income_id = 3001



