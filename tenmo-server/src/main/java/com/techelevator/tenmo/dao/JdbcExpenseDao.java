package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcExpenseDao implements ExpenseDao{
    private final String CATEGORY_01 = "Housing";
    private final String CATEGORY_02 = "Food";
    private final String CATEGORY_03 = "Transportation";
    private final String CATEGORY_04 = "Shopping";
    private final String CATEGORY_05 = "Savings";

    private JdbcTemplate jdbcTemplate;

    public JdbcExpenseDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Expense createNewExpense(Expense expense) {
        String sqlToCreateExpense = "INSERT INTO expenses(user_id,amount,category,date)\n" +
                "VALUES(?,?,?,?) RETURNING expense_id ";
        int expenseId = jdbcTemplate.queryForObject(sqlToCreateExpense,int.class,expense.getUserId(),expense.getAmount(),
                expense.getCategory(),expense.getDate());
        expense.setExpenseId(expenseId);

        String updateAccountBalance = "UPDATE account\n" +
                "SET balance = balance - ?\n" +
                "WHERE user_id = ? ";
        jdbcTemplate.update(updateAccountBalance,expense.getAmount(),expense.getUserId());
        return expense;
    }

    @Override
    public Expense getExpenseByExpenseId(int id) {
        String sql = "SELECT expense_id, user_id, amount, category, date\n" +
                "FROM expenses\n" +
                "WHERE expense_id = ? ";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
        if(result.next()){
            return mapRowToExpense(result);
        }
        throw new DaoException("Expense with ID" + id + " not found");
    }

    private Expense mapRowToExpense(SqlRowSet row){
        Expense expense = new Expense();
        expense.setExpenseId(row.getInt("expense_id"));
        expense.setUserId(row.getInt("user_id"));
        expense.setAmount(row.getDouble("amount"));
        expense.setCategory(row.getString("category"));
        if(row.getDate("date") != null){
            expense.setDate(row.getDate("date"));
        }

        return expense;

    }


}
