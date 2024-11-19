package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Expense;

public interface ExpenseDao {
    Expense createNewExpense(Expense expense);
    Expense getExpenseByExpenseId(int id);
}
