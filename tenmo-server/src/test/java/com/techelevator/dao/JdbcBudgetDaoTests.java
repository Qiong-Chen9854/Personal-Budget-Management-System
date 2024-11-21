package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcBudgetDao;
import com.techelevator.tenmo.model.Budget;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JdbcBudgetDaoTests extends BaseDaoTests{
    private Budget testBudget;
    private JdbcBudgetDao jdbcBudgetDao;

    @Before
    public void setup(){
        this.jdbcBudgetDao = new JdbcBudgetDao(dataSource);
        LocalDate localDate = LocalDate.of(2024,11,01);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.testBudget = new Budget(0,1001,200,date);

    }

    @Test
    public void create_new_budget(){
        Budget createBudget = jdbcBudgetDao.createBudget(testBudget);
        Budget selectBudget = jdbcBudgetDao.selectBudgetByDate(createBudget.getMonthYear());
        testBudget.setBudgetId(createBudget.getBudgetId());
        Assert.assertEquals(testBudget,selectBudget);
    }

}
