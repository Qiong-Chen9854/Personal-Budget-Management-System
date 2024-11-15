BEGIN TRANSACTION;
ROLLBACK;

SELECT *
FROM incomes

Delete from incomes
where user_id = 1001



INSERT INTO incomes(user_id,amount,source,date)
VALUES(1001,200,'Freelance','2024-11-14') RETURNING income_id
