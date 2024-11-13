package com.techelevator.tenmo.model;

import java.time.YearMonth;
import java.util.Objects;

public class Budget {
    private int budgetId;
    private int userId;
    private String category;
    private double amount;
    private YearMonth monthYear;

    public Budget() {
    }

    public Budget(int budgetId, int userId, String category, double amount, YearMonth monthYear) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public YearMonth getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(YearMonth monthYear) {
        this.monthYear = monthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return budgetId == budget.budgetId && userId == budget.userId && Double.compare(budget.amount, amount) == 0 && Objects.equals(category, budget.category) && Objects.equals(monthYear, budget.monthYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, userId, category, amount, monthYear);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", monthYear=" + monthYear +
                '}';
    }
}
