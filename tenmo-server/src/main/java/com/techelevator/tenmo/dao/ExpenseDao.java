package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Expense;

import java.util.List;
import java.util.Map;

public interface ExpenseDao {
    Expense createNewExpense(Expense expense);
    Expense getExpenseByExpenseId(int id);

    List<Expense> getExpenseList(int userId);
    Map<Integer, String> expenseCategoryList();

}
