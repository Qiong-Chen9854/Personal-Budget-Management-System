package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Budget;

import java.util.Date;
import java.util.Map;

public interface BudgetDao {
    Budget createBudget(Budget budget);
    Budget selectBudgetByDate(Date date);

    Map<String, double[]> budgetVsSpending(String dateAsString, int userId);
}
