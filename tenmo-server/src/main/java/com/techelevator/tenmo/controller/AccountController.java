package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Budget;
import com.techelevator.tenmo.model.Expense;
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
    private ExpenseDao expenseDao;
    private BudgetDao budgetDao;
    private UserDao userDao;
    public AccountController(AccountDao accountDao, UserDao userDao,IncomeDao incomeDao,ExpenseDao expenseDao,
                             BudgetDao budgetDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.incomeDao = incomeDao;
        this.expenseDao = expenseDao;
        this.budgetDao = budgetDao;
    }

    @RequestMapping(path="/account", method=RequestMethod.GET)
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

    @RequestMapping(path="/account/income", method=RequestMethod.GET)
    public List<Income> getIncomeList(Principal principal){
        return incomeDao.getIncomeList(getUserId(principal));
    }
    @RequestMapping(path="/account/expense", method=RequestMethod.POST)
    public Expense createExpense(@RequestBody Expense expense, Principal principal){
        expense.setUserId(getUserId(principal));
        return expenseDao.createNewExpense(expense);
    }
    @RequestMapping(path="/account/expense", method=RequestMethod.GET)
    public List<Expense> getExpenseList(Principal principal){
        return  expenseDao.getExpenseList(getUserId(principal));
    }
    @RequestMapping(path="/account/budget",method = RequestMethod.POST)
    public Budget createBudget(@RequestBody Budget budget, Principal principal){
        budget.setUserId(getUserId(principal));
        return budgetDao.createBudget(budget);
    }


    private int getUserId(Principal principal){
        String userName = principal.getName();
        User user = userDao.getUserByUsername(userName);
        return user.getId();
    }
}
