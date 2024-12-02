package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcIncomeDao;
import com.techelevator.tenmo.model.Income;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class JdbcIncomeDaoTests extends BaseDaoTests{
    private JdbcTemplate jdbcTemplate;
    private Income testIncome;
    private JdbcIncomeDao jdbcIncomeDao;

    @Before
    public void setup(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcIncomeDao = new JdbcIncomeDao(dataSource);
        LocalDate localDate = LocalDate.of(2024,11,15);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testIncome = new Income(0,1001,100,6002, date);
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

    @Test
    public void get_income_list(){
        List<Income> incomeList = jdbcIncomeDao.getIncomeList(1001);
        Assert.assertEquals(2,incomeList.size());

    }

//    @After
//    public void cleanup(){
//        String deleteIncome = "DELETE FROM incomes WHERE income_id = ? ";
//        jdbcTemplate.update(deleteIncome, testIncome.getIncomeId());
//    }
}
