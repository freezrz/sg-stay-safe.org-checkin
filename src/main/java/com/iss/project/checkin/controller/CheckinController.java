package com.iss.project.checkin.controller;

import com.alibaba.fastjson.JSON;
import com.amazonaws.util.StringUtils;
import com.iss.project.checkin.Constants;
import com.iss.project.checkin.model.AuthRequest;
import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.LambdaResponse;
import com.iss.project.checkin.model.SafeResponse;
import com.iss.project.checkin.service.GoogleTokenService;
import com.iss.project.checkin.service.LambdaInvokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class CheckinController {
    @Autowired
    LambdaInvokeService lambdaInvokeService;

    @Autowired
    GoogleTokenService googleTokenService;

    Logger logger = LoggerFactory.getLogger(CheckinController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public SafeResponse checkIn(@RequestBody(required = false) CheckinRequest request, HttpServletRequest httpServletRequest) {
        if (request != null)
            logger.info("CheckinController.checkIn: {}", JSON.toJSONString(request));
        String idToken = httpServletRequest.getHeader("Authorization");
        logger.info("CheckinController.header: {}", idToken);
        //validate idToken
        idToken = idToken.replace("Bearer ", "");
        try {
            String anonymuosId = googleTokenService.validateIdTokenReturnId(idToken);
            if (StringUtils.isNullOrEmpty(anonymuosId)) {
                return SafeResponse.responseFail(Constants.RESPONSE_CODE_EXPIRED_TOKEN, "");
            }
            logger.info("checkIn validate idToken success");
            request.setAnonymous_id(anonymuosId);
        } catch (Exception e) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_UNKNOWN, "Unknown error, pls try again");
        }

        //invoke Lambda functions
        List<String> functions = Arrays.asList(Constants.LAMBDA_FUNCTION_SANITISE_CHECKIN,
                Constants.LAMBDA_FUNCTION_ANTIFRAUD_CHECKIN,
                Constants.LAMBDA_FUNCTION_VERIFY_RULES_CHECKIN,
                Constants.LAMBDA_FUNCTION_RECORD_CHECKIN,
                Constants.LAMBDA_FUNCTION_PRODUCE_CHECKIN);
        for (String function : functions){
            LambdaResponse lambdaResponse = lambdaInvokeService.invokeLambdaFunction(request, function);
            if (lambdaResponse.getCode() != 0) {
                return SafeResponse.responseFail(lambdaResponse.getCode(), lambdaResponse.getMsg());
            }
        }

        return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_CHECKIN_SUCCESS);
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    @ResponseBody
    public SafeResponse authToken(@RequestBody(required = false) AuthRequest request, HttpServletRequest httpServletRequest) {
        if (request != null)
            logger.info("CheckinController.authToken: {}", JSON.toJSONString(request));
        if (!googleTokenService.validateIdToken(request.getIdToken())) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_EXPIRED_TOKEN, "RESPONSE_CODE_EXPIRED");
        }
        return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_AUTH_SUCCESS);
    }
}
