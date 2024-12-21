package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Income;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcIncomeDao implements IncomeDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcIncomeDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Income createNewIncome(Income income) {
        String sql = "INSERT INTO incomes(user_id,amount,source_id,date)\n" +
                "VALUES(?,?,?,?) RETURNING income_id ";
        int incomeId = jdbcTemplate.queryForObject(sql,int.class,income.getUserId(),income.getAmount(),income.getSourceId(),
                income.getDate());
        income.setIncomeId(incomeId);
        income.setSourceName(getSourceName(income.getSourceId()));

        String updateAccountSql = "UPDATE account\n" +
                "SET balance = balance + ? \n" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(updateAccountSql,income.getAmount(),income.getUserId());

        return income;
    }

    @Override
    public Income getIncomeByIncomeId(int incomeId) {
        String sql = "SELECT income_id, user_id, amount, source_id, date\n" +
                "FROM incomes\n" +
                "WHERE income_id = ? \n";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,incomeId);
        if(result.next()){
            return mapRowToIncome(result);
        }
        throw new DaoException("Income with ID" + incomeId + " not found.");
    }

    @Override
    public List<Income> getIncomeList(int userId) {
        List<Income> incomeList = new ArrayList<>();
        String sql = "SELECT income_id, user_id, amount, source_id, date\n" +
                "FROM incomes\n" +
                "WHERE user_id = ? ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId);
            while(results.next()){
                incomeList.add(mapRowToIncome(results));
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation",e);
        }

        return incomeList;
    }

    @Override
    public Map<Integer, String> incomeSource() {
        String sql = "SELECT source_id, name\n" +
                "FROM income_source";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        Map<Integer,String> incomeSource = new HashMap<>();
        while(results.next()){
            incomeSource.put(results.getInt("source_id"),results.getString("name"));
        }
        return incomeSource;
    }

    private Income mapRowToIncome(SqlRowSet row){
        Income income = new Income();
        income.setIncomeId(row.getInt("income_id"));
        income.setUserId(row.getInt("user_id"));
        income.setAmount(row.getDouble("amount"));
        income.setSourceId(row.getInt("source_id"));
        income.setSourceName(getSourceName(row.getInt("source_id")));
        if(row.getDate("date") != null){
            income.setDate(row.getDate("date"));
        }
        return income;
    }

    private String getSourceName(int sourceId){
        String sql = "SELECT name\n" +
                "FROM income_source\n" +
                "WHERE source_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,sourceId);
        String sourceName = null;
        if(result.next()){
            sourceName = result.getString("name");
        }
        return sourceName;
    }
}
