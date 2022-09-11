package com.iss.project.checkin.service;

import com.iss.project.checkin.model.AuthRequest;
import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.SafeResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;

public interface GoogleTokenService {

    Boolean validateIdToken(String idToken);
    abstract String validateIdTokenReturnId(String idToken) throws Exception;
}
