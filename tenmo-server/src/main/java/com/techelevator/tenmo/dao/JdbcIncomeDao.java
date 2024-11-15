package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class JdbcIncomeDao implements IncomeDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcIncomeDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Income createNewIncome(Income income) {
        String sql = "INSERT INTO incomes(user_id,amount,source,date)\n" +
                "VALUES(?,?,?,?) RETURNING income_id ";
        int incomeId = jdbcTemplate.queryForObject(sql,int.class,income.getUserId(),income.getAmount(),income.getSource(),
                income.getDate());
        income.setIncomeId(incomeId);

        return income;
    }
}
