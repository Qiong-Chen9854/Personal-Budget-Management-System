package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

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


    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
