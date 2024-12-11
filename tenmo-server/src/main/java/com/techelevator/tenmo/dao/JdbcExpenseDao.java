package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
        String sqlToCreateExpense = "INSERT INTO expenses(user_id,amount,category_id,date)\n" +
                "VALUES(?,?,?,?) RETURNING expense_id ";
        int expenseId = jdbcTemplate.queryForObject(sqlToCreateExpense,int.class,expense.getUserId(),expense.getAmount(),
                expense.getCategoryId(),expense.getDate());
        expense.setExpenseId(expenseId);
        expense.setCategoryName(getCategoryName(expense.getCategoryId()));

        String updateAccountBalance = "UPDATE account\n" +
                "SET balance = balance - ?\n" +
                "WHERE user_id = ? ";
        jdbcTemplate.update(updateAccountBalance,expense.getAmount(),expense.getUserId());
        return expense;
    }

    @Override
    public Expense getExpenseByExpenseId(int id) {
        String sql = "SELECT expense_id, user_id, amount, category_id, date\n" +
                "FROM expenses\n" +
                "WHERE expense_id = ? ";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,id);
        if(result.next()){
            return mapRowToExpense(result);
        }
        throw new DaoException("Expense with ID" + id + " not found");
    }

    @Override
    public List<Expense> getExpenseList(int userId) {
        List<Expense> expenseList = new ArrayList<>();
        String sql = "SELECT expense_id, user_id, amount, category_id, date\n" +
                "FROM expenses\n" +
                "WHERE user_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()){
            expenseList.add(mapRowToExpense(results));
        }
        return expenseList;
    }



    private Expense mapRowToExpense(SqlRowSet row){
        Expense expense = new Expense();
        expense.setExpenseId(row.getInt("expense_id"));
        expense.setUserId(row.getInt("user_id"));
        expense.setAmount(row.getDouble("amount"));
        expense.setCategoryId(row.getInt("category_id"));
        expense.setCategoryName(getCategoryName(row.getInt("category_id")));
        if(row.getDate("date") != null){
            expense.setDate(row.getDate("date"));
        }
        return expense;
    }

    private String getCategoryName(int categoryId){
        String sql = "SELECT name\n" +
                "FROM expense_category\n" +
                "WHERE category_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,categoryId);
        String categoryName = null;
        if(result.next()){
            categoryName = result.getString("name");
        }
        return categoryName;
    }
}
