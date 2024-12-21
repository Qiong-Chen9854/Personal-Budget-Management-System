package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private String baseApiUrl;
    private AuthenticatedUser currentUser;
    private User user;

    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String baseApiUrl){
        this.baseApiUrl = baseApiUrl;
    }

    public void setCurrentUser(AuthenticatedUser currentUser){
        this.currentUser = currentUser;
    }

    public AuthenticatedUser getCurrentUser(){
        return this.currentUser;
    }

    public double getBalance(){
        String url = baseApiUrl + "/account";
        double balance = 0.00;
        HttpEntity<Void> entity = new HttpEntity<>(headers());

        try{
            ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET,entity,Double.class);
            balance = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getStatusText());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }

        return balance;
    }

    public Income addIncome(Income income){
        String url = baseApiUrl + "/account/income";
        HttpEntity<Income> entity = new HttpEntity<>(income,headers());

        try{
            ResponseEntity<Income> response = restTemplate.exchange(url,HttpMethod.POST,entity,Income.class);
            income = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getStatusText());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return income;
    }

    public List<Income> getIncomeList(){
        Income[] incomes = null;
        String url = baseApiUrl + "/account/income";

        HttpEntity<Income> entity = new HttpEntity<>(headers());
        try{
            ResponseEntity<Income[]> response = restTemplate.exchange(url,HttpMethod.GET,entity,Income[].class);
            incomes = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(incomes);
    }

    public Map<Integer, String> incomeSourcesList(){
        Map<Integer, String> incomeSources  = new HashMap<>();
        String url = baseApiUrl + "/account/income-sources";
        HttpEntity<Map> entity = new HttpEntity<>(headers());
        try{
            ResponseEntity<Map> response = restTemplate.exchange(url,HttpMethod.GET,entity,Map.class);
            incomeSources = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return incomeSources;
    }

    public Map<Integer, String> expenseCategoryList(){
        Map<Integer, String> expenseCategory  = new HashMap<>();
        String url = baseApiUrl + "/account/expense-category";
        HttpEntity<Map> entity = new HttpEntity<>(headers());
        try{
            ResponseEntity<Map> response = restTemplate.exchange(url,HttpMethod.GET,entity,Map.class);
            expenseCategory = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return expenseCategory;
    }

    public Expense createNewExpense(Expense expense){
        String url = baseApiUrl + "/account/expense";
        HttpEntity<Expense> entity = new HttpEntity<>(expense,headers());
        try{
            ResponseEntity<Expense> response = restTemplate.exchange(url,HttpMethod.POST,entity,Expense.class);
            expense = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }

        return expense;
    }

    public Budget createBudget(Budget budget){
        String url = baseApiUrl + "/account/budget";
        HttpEntity<Budget> entity = new HttpEntity<>(budget,headers());
        try{
            ResponseEntity<Budget> response = restTemplate.exchange(url,HttpMethod.POST,entity, Budget.class);
            budget = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return budget;
    }

    public List<Expense> getAllExpense(){
        Expense[] expenses = null;
        String url = baseApiUrl + "/account/expense";
        HttpEntity<Expense> entity = new HttpEntity<>(headers());

        try{
            ResponseEntity<Expense[]> response = restTemplate.exchange(url,HttpMethod.GET,entity,Expense[].class);
            expenses = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return Arrays.asList(expenses);
    }

    public Map<String, double[]> budgetVsSpendingByMonth(String dateAsString){
        Map<String, double[]> budgetVsSpending = new HashMap<>();
        String url = baseApiUrl + "/account/report/budgetvsexpense?date=" + dateAsString;
        HttpEntity<String> entity = new HttpEntity<>(headers());
        try{
            ResponseEntity<Map<String, double[]>> response = restTemplate.exchange(url,HttpMethod.GET,entity,
                    new ParameterizedTypeReference<Map<String, double[]>>() {});
            budgetVsSpending = response.getBody();
        } catch(RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + ": " + e.getMessage());
        } catch(ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return budgetVsSpending;
    }


    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
