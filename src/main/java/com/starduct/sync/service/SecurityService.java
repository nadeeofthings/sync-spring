package com.starduct.sync.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
