package com.iss.project.checkin.service.impl;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import com.iss.project.checkin.Constants;
import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.LambdaResponse;
import com.iss.project.checkin.model.ViolationEvent;
import com.iss.project.checkin.service.LambdaInvokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
@Service
public class LambdaInvokeServiceImpl implements LambdaInvokeService {

    Logger logger = LoggerFactory.getLogger(LambdaInvokeServiceImpl.class);

    @Override
    public LambdaResponse invokeLambdaFunction(CheckinRequest request, String function) {
        if (request != null)
            logger.info("LambdaInvokeService.invokeLambdaFunction request={}", JSON.toJSONString(request));
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(function)
                .withPayload(JSON.toJSONString(request));
        InvokeResult invokeResult = null;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withRegion(Regions.AP_SOUTHEAST_1).build();

            invokeResult = awsLambda.invoke(invokeRequest);

            String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

            LambdaResponse lambdaResponse = JSON.parseObject(ans, LambdaResponse.class);

            if (lambdaResponse.getCode() == Constants.LAMBDA_RESPONSE_CODE_SITE_BANNED) {
                //todo invoke another lambda function
                ViolationEvent violationEvent = new ViolationEvent(request, lambdaResponse);
                invokeRequest = new InvokeRequest()
                        .withFunctionName(Constants.LAMBDA_FUNCTION_PRODUCE_SITE_VIOLATION_CHECKIN)
                        .withPayload(JSON.toJSONString(violationEvent));
                invokeResult = awsLambda.invoke(invokeRequest);
//                return SafeResponse.responseFail(lambdaResponse.getCode(), lambdaResponse.getMsg());
            } else if (lambdaResponse.getCode() == Constants.LAMBDA_RESPONSE_CODE_USER_BANNED || lambdaResponse.getCode() == Constants.LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED) {
                //todo invoke another lambda function
//                return SafeResponse.responseFail(lambdaResponse.getCode(), lambdaResponse.getMsg());
                ViolationEvent violationEvent = new ViolationEvent(request, lambdaResponse);
                invokeRequest = new InvokeRequest()
                        .withFunctionName(Constants.LAMBDA_FUNCTION_PRODUCE_USER_VIOLATION_CHECKIN)
                        .withPayload(JSON.toJSONString(violationEvent));
                invokeResult = awsLambda.invoke(invokeRequest);
            }
            return lambdaResponse;
        } catch (ServiceException e) {
            logger.error("InvokeSanitiseCheckIn hit error", e);
            return new LambdaResponse(Constants.RESPONSE_CODE_UNKNOWN, "Unknown error, pls try again");
        }
    }

}
