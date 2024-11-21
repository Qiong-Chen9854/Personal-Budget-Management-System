package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Expense;

import java.util.List;

public interface ExpenseDao {
    Expense createNewExpense(Expense expense);
    Expense getExpenseByExpenseId(int id);

    List<Expense> getExpenseList(int userId);
}
