package com.evatool.application.service.api;

public interface LoginAttemptService {

    int MAX_ATTEMPTS = 10;

    void loginSucceeded(String key);

     void loginFailed(String key);

    boolean isBlocked(String key);

    int getRemainingAttempts(String key);

}
