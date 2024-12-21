package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcExpenseDao;
import com.techelevator.tenmo.model.Expense;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JdbcExpenseDaoTests extends BaseDaoTests{
    private Expense testExpense;
    private JdbcExpenseDao jdbcExpenseDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        jdbcExpenseDao = new JdbcExpenseDao(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        LocalDate localDate = LocalDate.of(2024,11,18);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testExpense = new Expense(0,1001,100,7002,null,date);
    }

    @Test
    public void create_new_expense(){
        Expense createExpense = jdbcExpenseDao.createNewExpense(testExpense);
        Expense selectedExpense = jdbcExpenseDao.getExpenseByExpenseId(createExpense.getExpenseId());
        testExpense.setExpenseId(createExpense.getExpenseId());
        Assert.assertEquals(testExpense,selectedExpense);
    }

    @Test
    public void get_all_expense(){
        List<Expense> expenseList = jdbcExpenseDao.getExpenseList(1001);
        int numOfExpense = expenseList.size();
        Assert.assertEquals(2,numOfExpense);
    }

    @Test
    public void get_expense_category(){
        Map<Integer, String> expenseCategory = jdbcExpenseDao.expenseCategoryList();
        Assert.assertEquals(4,expenseCategory.size());
    }

//    @After
//    public void cleanup(){
//        String deleteExpenses = "DELETE FROM expenses WHERE expense_id = ?";
//        jdbcTemplate.update(deleteExpenses,testExpense.getExpenseId());
//    }

}
