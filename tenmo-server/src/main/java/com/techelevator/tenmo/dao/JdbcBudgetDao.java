package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Budget;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Map;

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

    @Override
    public Map<Date, double[]> budgetVsSpending(String dateAsString, int userId) {
        String sql = "SELECT TO_CHAR(b.month_year, 'YYYY-MM') AS month_year,\n" +
                "    \tb.amount AS total_budget,\n" +
                "    \tCOALESCE(SUM(e.amount), 0) AS total_expenses\n" +
                "FROM budgets b\n" +
                "LEFT JOIN expenses e ON b.user_id = e.user_id \n" +
                "          AND TO_CHAR(b.month_year, 'YYYY-MM') = TO_CHAR(e.date, 'YYYY-MM')\n" +
                "WHERE TO_CHAR(b.month_year, 'YYYY-MM') = '2024-11' AND b.user_id = 1001\n" +
                "GROUP BY b.month_year, b.amount;\n"
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
