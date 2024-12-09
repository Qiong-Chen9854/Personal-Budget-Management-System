package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private int categoryId;

    private String categoryName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Expense() {
    }

    public Expense(int expenseId, int userId, double amount, int categoryId, String categoryName,Date date) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
        return expenseId == expense.expenseId && userId == expense.userId && Double.compare(expense.amount, amount) == 0 && categoryId == expense.categoryId && Objects.equals(categoryName, expense.categoryName) && Objects.equals(date, expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, userId, amount, categoryId, categoryName, date);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", date=" + date +
                '}';
    }
}
