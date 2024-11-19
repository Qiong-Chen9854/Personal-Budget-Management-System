package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Expense;
import com.techelevator.tenmo.model.Income;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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


    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
