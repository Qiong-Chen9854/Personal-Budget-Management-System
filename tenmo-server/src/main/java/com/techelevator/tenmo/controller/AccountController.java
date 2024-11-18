package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.IncomeDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Income;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {
    private AccountDao accountDao;
    private IncomeDao incomeDao;
    private UserDao userDao;
    public AccountController(AccountDao accountDao, UserDao userDao,IncomeDao incomeDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.incomeDao = incomeDao;
    }

    @RequestMapping(path="/account", method = RequestMethod.GET)
    public double balance(Principal principal){
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        return accountDao.getAccountBalanceByUserId(user.getId());
    }

    @RequestMapping(path="/account/income", method=RequestMethod.POST)
    public Income createIncome(@RequestBody Income income, Principal principal){
        income.setUserId(getUserId(principal));
        return incomeDao.createNewIncome(income);
    }

    @RequestMapping(path="/account/income", method = RequestMethod.GET)
    public List<Income> getIncomeList(Principal principal){
        return incomeDao.getIncomeList(getUserId(principal));
    }

    private int getUserId(Principal principal){
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        return user.getId();
    }
}
