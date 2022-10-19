package com.iss.project.checkin.service.impl;

import com.iss.project.checkin.Constants;
import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.LambdaResponse;
import com.iss.project.checkin.service.LambdaInvokeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static com.iss.project.checkin.Constants.RESPONSE_CODE_SUCCESS;

@SpringBootTest
class LambdaInvokeServiceImplTest {

    @Autowired
    LambdaInvokeService lambdaInvokeService;

    @Test
    void invokeLambdaFunctionSanitiseCheck() {
        CheckinRequest checkinRequest = new CheckinRequest("testanonymous", "testsiteid");
        LambdaResponse lambdaResponse = lambdaInvokeService.invokeLambdaFunction(checkinRequest, Constants.LAMBDA_FUNCTION_SANITISE_CHECKIN);
        Assert.isTrue(lambdaResponse.getCode() == RESPONSE_CODE_SUCCESS, "Sanitise check pass");
    }
}