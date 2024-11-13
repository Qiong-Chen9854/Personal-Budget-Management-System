package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public double getAccountBalanceByUserId(int userId) {
        double balance = 0.00;
        String sql = "SELECT balance\n" +
                "FROM ACCOUNT\n" +
                "WHERE user_id = ? ";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,userId);
            while(result.next()){
                balance = result.getDouble("balance");
            }
        } catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return balance;
    }
}
