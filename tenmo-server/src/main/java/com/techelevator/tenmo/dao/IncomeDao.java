package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Income;

import java.util.List;

public interface IncomeDao {
    Income createNewIncome(Income income);

    Income getIncomeByIncomeId(int incomeId);
    List<Income> getIncomeList(int userId);
}
