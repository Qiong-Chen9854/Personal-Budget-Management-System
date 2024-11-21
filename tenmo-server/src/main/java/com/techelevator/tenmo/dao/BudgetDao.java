package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Budget;

import java.util.Date;

public interface BudgetDao {
    Budget createBudget(Budget budget);
    Budget selectBudgetByDate(Date date);
}
