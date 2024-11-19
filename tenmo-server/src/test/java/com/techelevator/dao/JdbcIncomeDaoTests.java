package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcIncomeDao;
import com.techelevator.tenmo.model.Income;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JdbcIncomeDaoTests extends BaseDaoTests{
//    private static final Income INCOME_1 = new Income()
    private Income testIncome;
    private JdbcIncomeDao jdbcIncomeDao;

    @Before
    public void setup(){
        jdbcIncomeDao = new JdbcIncomeDao(dataSource);
        LocalDate localDate = LocalDate.of(2024,11,15);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testIncome = new Income(0,1001,100,"Freelance", date);
    }

    @Test
    public void add_new_income_entry(){
        Income returnIncome = jdbcIncomeDao.createNewIncome(testIncome);
        Assert.assertNotNull("Income is null", returnIncome);
        Assert.assertTrue("Income id not set", returnIncome.getIncomeId() > 0);
        Income selectedIncome = jdbcIncomeDao.getIncomeByIncomeId(returnIncome.getIncomeId());
        testIncome.setIncomeId(returnIncome.getIncomeId());
        Assert.assertEquals(testIncome,selectedIncome);
    }
}