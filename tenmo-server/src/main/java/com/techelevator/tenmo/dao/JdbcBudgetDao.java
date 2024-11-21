package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Budget;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Date;

@Component
public class JdbcBudgetDao implements BudgetDao{

    private JdbcTemplate jdbcTemplate;
    public JdbcBudgetDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Budget createBudget(Budget budget) {
        String sql = "INSERT INTO budgets(user_id, amount, month_year)\n" +
                "VALUES(?,?,?) RETURNING budget_id ";
        int budgetId = jdbcTemplate.queryForObject(sql,int.class,budget.getUserId(),budget.getAmount(),budget.getMonthYear());
        budget.setBudgetId(budgetId);
        return budget;
    }

    @Override
    public Budget selectBudgetByDate(Date date) {
        String sql = "SELECT budget_id, user_id, amount,month_year\n" +
                "FROM budgets\n" +
                "WHERE month_year = ? ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,date);
        if(result.next()){
            return mapToBudget(result);
        }

       throw new DaoException("This Budget does not found!");
    }

    private Budget mapToBudget(SqlRowSet row){
        Budget budget = new Budget();
        budget.setBudgetId(row.getInt("budget_id"));
        budget.setUserId(row.getInt("user_id"));
        budget.setAmount(row.getDouble("amount"));
        if(row.getDate("month_year") != null){
            budget.setMonthYear(row.getDate("month_year"));
        }
        return budget;
    }
}
