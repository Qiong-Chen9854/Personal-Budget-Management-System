package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Budget {
    private int budgetId;
    private int userId;
    private double amount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date monthYear;

    public Budget() {
    }

    public Budget(int budgetId, int userId, double amount, Date monthYear) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.amount = amount;
        this.monthYear = monthYear;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) {
        this.monthYear = monthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return budgetId == budget.budgetId && userId == budget.userId && Double.compare(budget.amount, amount) == 0 && Objects.equals(monthYear, budget.monthYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, userId, amount, monthYear);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", monthYear=" + monthYear +
                '}';
    }
}

