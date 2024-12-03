package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Expense;
import com.techelevator.tenmo.model.Income;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to Your Personal Budget Management System! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View Your Current Balance");
        System.out.println("2: Add a New Income");
        System.out.println("3: Add an Expense");
        System.out.println("4: Set a Budget");
        System.out.println("5: Summary Report");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void pintSummaryMenu(){
        System.out.println();
        System.out.println("1: View All Your Incomes");
        System.out.println("2: View All Your Expenses");
        System.out.println("3: View All Your Budget VS Spending");
        System.out.println("0: Exit");
    }

    public int printIncomeSource(){
        System.out.println();
        System.out.println("Id: Income Source ");
        System.out.println("1:  Salary");
        System.out.println("2:  Freelance");
        System.out.println("3:  Investment");
        System.out.println("4:  Gifts");
        System.out.println("5:  Create A New Source");
        System.out.print("Enter the source Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public int printExpenseCategory(){
        System.out.println();
        System.out.println("Id: Expense Category ");
        System.out.println("1:  Housing");
        System.out.println("2:  Food");
        System.out.println("3:  Transportation");
        System.out.println("4:  Shopping");
        System.out.println("5:  Create A New Category");
        System.out.print("Enter the Category Id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printUserCurrentBalance(double balance){
        System.out.println("Your current account balance is : $" + String.valueOf(balance));
    }

    public double askUserAmount(){
        System.out.print("The Income Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        return amount;
    }

    public Date askDate(){
        System.out.print("What Data for this Transaction(format as 'yyyy-MM-dd'): ");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(scanner.nextLine());
            return date;
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use 'yyyy-MM-dd'.");
            return null;  // Or you can prompt the user again for a valid date
        }
    }

    public void printNewIncomeInfo(Income income){
        System.out.print("This new income added into your account --> ");
        System.out.print("Amount: $" + String.valueOf(income.getAmount()));
        System.out.println(" Source: " + income.getSourceId());
    }

    public void printNewExpenseInfo(Expense expense){
        System.out.println("------------------------------------------------------");
        System.out.println("This new expense occurred into your account --> ");
        System.out.println("Amount: $" + String.valueOf(expense.getAmount()));
        System.out.println("The Expense spend on: " + expense.getCategory());
        System.out.println("------------------------------------------------------");

    }

    public void printIncomeList(List<Income> incomes){
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("------------------------------------------------------");
        System.out.println("IncomeID        Amount        Source        Date");
        System.out.println("------------------------------------------------------");
        for(Income income:incomes){
            System.out.printf("%-16s",income.getIncomeId());
            System.out.printf("%-14s",String.format("$%.2f", income.getAmount()));
            System.out.printf("%-14s",income.getSourceId());
            // Format the date
            if (income.getDate() != null) {
                System.out.print(dateFormat.format(income.getDate()));
            } else {
                System.out.print("N/A"); // Handle null dates
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------");
    }

    public void printExpenseList(List<Expense> expenses){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("---------------------------------------------------------");
        System.out.println("ExpenseID        Amount        Category        Date");
        System.out.println("---------------------------------------------------------");
        for(Expense expense:expenses){
            System.out.printf("%-17s",expense.getExpenseId());
            System.out.printf("%-14s",String.format("$%.2f",expense.getAmount()));
            System.out.printf("%-16s",expense.getCategory());
            // Format the date
            if (expense.getDate() != null) {
                System.out.print(dateFormat.format(expense.getDate()));
            } else {
                System.out.print("N/A"); // Handle null dates
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------");
    }

    public void printBudgetVsSpending(Map<String,double[]> budgetVsSpending){
        System.out.println("----------------------------------------------------------");
        System.out.println("Date             Budget           Spending         Balance");
        for(Map.Entry<String,double[]> entry: budgetVsSpending.entrySet()){
            System.out.printf("%-17s",entry.getKey());
            System.out.printf("%-17s",String.format("$%.2f",entry.getValue()[0]));
            System.out.printf("%-17s",String.format("$%.2f",entry.getValue()[1]));
            System.out.printf("%-17s",String.format("$%.2f",(entry.getValue()[0] - entry.getValue()[1])));
        }
        System.out.println();
        System.out.println("----------------------------------------------------------");
    }

//    public void alertBudgetCloseToLimit(String formattedDate, Map<String,double[]> budgetVsSpending){
//        double remain = budgetVsSpending.get(formattedDate)[0] - budgetVsSpending.get(formattedDate)[1];
//        System.out.println("-------------------------------------------------");
//        System.out.println("WARNING!!!!!");
//        System.out.println("Your Budget is close to limit, remain --> $" + remain);
//        System.out.println("-------------------------------------------------");
//    }

}
