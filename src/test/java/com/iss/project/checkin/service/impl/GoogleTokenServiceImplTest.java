package com.iss.project.checkin.service.impl;

import com.iss.project.checkin.service.GoogleTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static com.iss.project.checkin.Constants.UNIT_TEST_MOCK_IDTOKEN;
import static com.iss.project.checkin.Constants.UNIT_TEST_MOCK_IDTOKEN_2;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GoogleTokenServiceImplTest {

    @Autowired
    GoogleTokenService googleTokenService;
    @Test
    void validateIdToken() {
        Boolean success = googleTokenService.validateIdToken(UNIT_TEST_MOCK_IDTOKEN_2);
        Assert.isTrue(!success, "Id token is invalid");
    }

    @Test
    void validateIdTokenReturnId() {
        String responseStr = googleTokenService.validateIdTokenReturnId(UNIT_TEST_MOCK_IDTOKEN_2);
        Assert.isNull(responseStr, "Id token is invalid");
    }
}