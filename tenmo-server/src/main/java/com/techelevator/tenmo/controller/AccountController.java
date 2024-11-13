package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;
    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public double balance(Principal principal){
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        return accountDao.getAccountBalanceByUserId(user.getId());
    }
}
