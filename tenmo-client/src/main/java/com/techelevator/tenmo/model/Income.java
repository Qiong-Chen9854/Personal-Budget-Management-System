package com.techelevator.tenmo.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class Income {
    private int incomeId;
    private int userId;
    private double amount;
    private int sourceId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Income() {
    }

    public Income(int incomeId, int userId, double amount, int sourceId, Date date) {
        this.incomeId = incomeId;
        this.userId = userId;
        this.amount = amount;
        this.sourceId = sourceId;
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

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
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
        Income income = (Income) o;
        return incomeId == income.incomeId && userId == income.userId && Double.compare(income.amount, amount) == 0 && sourceId == income.sourceId && Objects.equals(date, income.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeId, userId, amount, sourceId, date);
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", sourceId=" + sourceId +
                ", date=" + date +
                '}';
    }
}
