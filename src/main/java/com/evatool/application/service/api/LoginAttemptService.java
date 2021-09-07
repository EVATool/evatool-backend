package com.evatool.application.service.api;

public interface LoginAttemptService {

    int MAX_ATTEMPTS = 10;

    void loginSucceeded(String key);

    public void loginFailed(String key);

    public boolean isBlocked(String key);

    public int getRemainingAttempts(String key);

}
