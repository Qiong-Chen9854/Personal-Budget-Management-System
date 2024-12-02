package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Expense;
import com.techelevator.tenmo.model.Income;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import java.util.*;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private AccountService accountService = new AccountService(API_BASE_URL);

    private static final int INCOME_SOURCE_SALARY = 6001;
    private static final int INCOME_SOURCE_FREELANCE = 6002;
    private static final int INCOME_SOURCE_INVESTMENT = 6003;
    private static final int INCOME_SOURCE_GIFTS = 6004;


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
//        isBudgetCloseToLimit();

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
        double getAmount = consoleService.askUserAmount();
        int sourceId = consoleService.printIncomeSource();
        int incomeSourceId = -1;
        if(sourceId == 1){
            incomeSourceId = INCOME_SOURCE_SALARY;
        }else if(sourceId == 2){
            incomeSourceId = INCOME_SOURCE_FREELANCE;
        }else if(sourceId == 3){
            incomeSourceId = INCOME_SOURCE_INVESTMENT;
        }else if(sourceId == 4){
            incomeSourceId = INCOME_SOURCE_GIFTS;
        }
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
        double getAmount = consoleService.askUserAmount();
        String expenseCategory = consoleService.promptForString("The Category of Expense: ");
        Date date = consoleService.askDate();
        Expense expense = new Expense();
        expense.setAmount(getAmount);
        expense.setCategory(expenseCategory);
        expense.setDate(date);
        expense = accountService.createNewExpense(expense);
        consoleService.printNewExpenseInfo(expense);
		
	}

	private void setBudget() {
		// TODO Auto-generated method stub
		
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

//    private void isBudgetCloseToLimit(){
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
//        String formattedDate = currentDate.format(formatter);
//        Map<String, double[]> budgetVsSpending = new HashMap<>();
//        budgetVsSpending = accountService.budgetVsSpendingByMonth(formattedDate);
//        if(budgetVsSpending.get(formattedDate)[0] - budgetVsSpending.get(formattedDate)[1] < 500){
//            consoleService.alertBudgetCloseToLimit(formattedDate,budgetVsSpending);
//        }
//    }
}
