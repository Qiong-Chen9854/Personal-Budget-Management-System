package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Expense() {
    }

    public Expense(int expenseId, int userId, double amount, String category, Date date) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return expenseId == expense.expenseId && userId == expense.userId && Double.compare(expense.amount, amount) == 0 && Objects.equals(category, expense.category) && Objects.equals(date, expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, userId, amount, category, date);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                '}';
    }
}
