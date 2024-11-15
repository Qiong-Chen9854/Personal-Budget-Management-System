BEGIN TRANSACTION;
ROLLBACK;

SELECT *
FROM incomes

SELECT *
FROM account

Delete from incomes
where user_id = 1001



INSERT INTO incomes(user_id,amount,source,date)
VALUES(1001,200,'Freelance','2024-11-14') RETURNING income_id;

UPDATE account
SET balance = balance + 200
WHERE user_id = 1001;

