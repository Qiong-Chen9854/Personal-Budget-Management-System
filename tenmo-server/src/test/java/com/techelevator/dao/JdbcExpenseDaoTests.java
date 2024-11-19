package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcExpenseDao;
import com.techelevator.tenmo.model.Expense;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JdbcExpenseDaoTests extends BaseDaoTests{
    private Expense testExpense;
    private JdbcExpenseDao jdbcExpenseDao;

    @Before
    public void setup(){
        jdbcExpenseDao = new JdbcExpenseDao(dataSource);
        LocalDate localDate = LocalDate.of(2024,11,18);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testExpense = new Expense(0,1001,100,"Food",date);
    }

    @Test
    public void create_new_expense(){
        Expense createExpense = jdbcExpenseDao.createNewExpense(testExpense);
        Expense selectedExpense = jdbcExpenseDao.getExpenseByExpenseId(createExpense.getExpenseId());
        testExpense.setExpenseId(createExpense.getExpenseId());
        Assert.assertEquals(testExpense,selectedExpense);
    }

}
