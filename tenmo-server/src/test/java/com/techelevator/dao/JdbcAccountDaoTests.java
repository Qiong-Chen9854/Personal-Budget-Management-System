package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcAccountDaoTests extends BaseDaoTests{
    private Account account;
    private JdbcAccountDao jdbcAccountDao;

    @Before
    public void setup(){
        jdbcAccountDao = new JdbcAccountDao(dataSource);
    }

    @Test
    public void get_account_balance(){
        double expectedBalance = 1000.00;
        double actualBalance = jdbcAccountDao.getAccountBalanceByUserId(1001);
        Assert.assertEquals(expectedBalance,actualBalance,0.09);
    }

    @After
    public void cleanup(){
        account = null;
        jdbcAccountDao = null;

    }


}
