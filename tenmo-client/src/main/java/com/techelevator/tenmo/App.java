package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private AccountService accountService = new AccountService(API_BASE_URL);
    
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            accountService.setCurrentUser(currentUser);
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        //Check if the budget close to limit
        isBudgetCloseToLimit();

        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                addNewIncome();
            } else if (menuSelection == 3) {
                addNewExpense();
            } else if (menuSelection == 4) {
                setBudget();
            } else if (menuSelection == 5) {
                summaryReport();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        double getBalance = accountService.getBalance();
        consoleService.printUserCurrentBalance(getBalance);
		
	}

	private void addNewIncome() {
		// TODO Auto-generated method stub
        double getAmount = consoleService.askUserAmount("What is the amount for this transaction: ");
        int incomeSourceId = consoleService.printIncomeSource(accountService.incomeSourcesList());
        Income income = new Income();
        income.setSourceId(incomeSourceId);
        Date date = consoleService.askDate();
        income.setAmount(getAmount);

        income.setDate(date);
        income = accountService.addIncome(income);
        consoleService.printNewIncomeInfo(income);
	}

	private void addNewExpense() {
		// TODO Auto-generated method stub
        double getAmount = consoleService.askUserAmount("What is the amount for this transaction: ");
        int expenseCategoryId = consoleService.printExpenseCategory(accountService.expenseCategoryList());
        Date date = consoleService.askDate();
        Expense expense = new Expense();
        expense.setAmount(getAmount);
        expense.setCategoryId(expenseCategoryId);
        expense.setDate(date);
        expense = accountService.createNewExpense(expense);
        consoleService.printNewExpenseInfo(expense);
		
	}

	private void setBudget() {
		// TODO Auto-generated method stub
        double getAmount = consoleService.askUserAmount("What is the amount for this budget setting: ");
        Date date = consoleService.askDateForBudget();
        Budget budget = new Budget();
        budget.setAmount(getAmount);
        budget.setMonthYear(date);
        accountService.createBudget(budget);
	}

	private void summaryReport() {
        int summaryMenu = -1;
		// TODO Auto-generated method stub
        while(summaryMenu != 0){
            consoleService.pintSummaryMenu();
            summaryMenu = consoleService.promptForMenuSelection("Please choose an option: ");
            if(summaryMenu == 1){
                getIncomeList();
            }else if(summaryMenu == 2){
                getExpenseList();
            }else if(summaryMenu == 3){
                getBudgetVsSpending();
            }
        }
	}

    private void getIncomeList(){
        List<Income> incomes = new ArrayList<>();
        incomes = accountService.getIncomeList();
        consoleService.printIncomeList(incomes);
    }

    private void getExpenseList(){
        List<Expense> expenses = new ArrayList<>();
        expenses = accountService.getAllExpense();
        consoleService.printExpenseList(expenses);
    }

    private void getBudgetVsSpending(){
        Map<String, double[]> budgetVsSpending = new HashMap<>();
        String monthAsString = consoleService.promptForString("Which month You want to check? --> Enter Month('YYYY-MM')): ");
        budgetVsSpending = accountService.budgetVsSpendingByMonth(monthAsString);
        consoleService.printBudgetVsSpending(budgetVsSpending);
    }

    private void isBudgetCloseToLimit(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDate = currentDate.format(formatter);

        Map<String, double[]> budgetVsSpending = accountService.budgetVsSpendingByMonth(formattedDate);

        if (budgetVsSpending == null || !budgetVsSpending.containsKey(formattedDate)) {
            System.out.println("No budget data available for the current month.");
            return; // Exit if no data is found
        }

        double[] budgetData = budgetVsSpending.get(formattedDate);
        if (budgetData == null || budgetData.length < 2) {
            System.out.println("Incomplete budget data for the current month.");
            return; // Exit if data is incomplete
        }
        double totalBudget = budgetData[0];
        double totalSpending = budgetData[1];
        double remainingBudget = totalBudget - totalSpending;

        // Check if the budget is close to being exceeded
        if (remainingBudget <= 50) { // Alert when the remaining budget is less than or equal to 50
            consoleService.alertBudgetCloseToLimit(formattedDate, budgetVsSpending);
        } else {
            System.out.println("Remaining budget is sufficient: " + remainingBudget);
        }
    }
}
