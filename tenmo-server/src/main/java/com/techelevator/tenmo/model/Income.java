package com.techelevator.tenmo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Income {
    private int incomeId;
    private int userId;
    private double amount;
    private String source;
    private LocalDate date;

    public Income() {
    }

    public Income(int incomeId, int userId, double amount, String source, LocalDate date) {
        this.incomeId = incomeId;
        this.userId = userId;
        this.amount = amount;
        this.source = source;
        this.date = date;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return incomeId == income.incomeId && userId == income.userId && Double.compare(income.amount, amount) == 0 && Objects.equals(source, income.source) && Objects.equals(date, income.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeId, userId, amount, source, date);
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", source='" + source + '\'' +
                ", date=" + date +
                '}';
    }
}
