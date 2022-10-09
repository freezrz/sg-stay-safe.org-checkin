package com.iss.project.checkin.service;

public interface GoogleTokenService {

    Boolean validateIdToken(String idToken);
    abstract String validateIdTokenReturnId(String idToken);
}
