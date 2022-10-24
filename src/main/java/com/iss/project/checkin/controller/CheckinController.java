package com.iss.project.checkin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.util.StringUtils;
import com.iss.project.checkin.Constants;
import com.iss.project.checkin.model.AuthRequest;
import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.LambdaResponse;
import com.iss.project.checkin.model.SafeResponse;
import com.iss.project.checkin.service.GoogleTokenService;
import com.iss.project.checkin.service.LambdaInvokeService;
import com.iss.project.checkin.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class CheckinController {
    @Autowired
    LambdaInvokeService lambdaInvokeService;

    @Autowired
    GoogleTokenService googleTokenService;

    @Autowired
    SecurityService securityService;

    Logger logger = LoggerFactory.getLogger(CheckinController.class);

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public SafeResponse checkIn(@RequestBody(required = true) CheckinRequest request, HttpServletRequest httpServletRequest) {
        if (request != null)
            logger.info("CheckinController.checkIn: {}", JSON.toJSONString(request));
        if (Objects.isNull(request) || StringUtils.isNullOrEmpty(request.getSite_id()))
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_INVALID_PARAM, "Miss required param");
        String idToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNullOrEmpty(idToken)) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_TOKEN_EMPTY, "Forbidden request, need to provide valid token");
        }
        logger.info("CheckinController.header: {}", idToken);
        //validate idToken
        idToken = idToken.replace("Bearer ", "");
        try {
            String anonymuosId = googleTokenService.validateIdTokenReturnId(idToken);
            if (StringUtils.isNullOrEmpty(anonymuosId)) {
                return SafeResponse.responseFail(Constants.RESPONSE_CODE_TOKEN_EXPIRED, "RESPONSE_CODE_EXPIRED");
            }
            logger.info("checkIn validate idToken success");
            String qrJson = securityService.decryptInfoWithAES(request.getSite_id());
            String siteId = JSONObject.parseObject(qrJson).getString("siteId");
            request.setSite_id(siteId);
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
                return SafeResponse.responseFail(lambdaResponse.getCode(), Constants.getMsg(lambdaResponse.getCode(), lambdaResponse.getMsg()));
            }
        }

        return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_CHECKIN_SUCCESS);
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    @ResponseBody
    public SafeResponse authToken(@RequestBody(required = true) AuthRequest request, HttpServletRequest httpServletRequest) {
        if (request != null)
            logger.info("CheckinController.authToken: {}", JSON.toJSONString(request));
        if (Objects.isNull(request) || StringUtils.isNullOrEmpty(request.getAnonymousId()))
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_INVALID_PARAM, "Miss required param");
        String idToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNullOrEmpty(idToken)) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_TOKEN_EMPTY, "Forbidden request, need to provide valid token");
        }
        //validate idToken
        idToken = idToken.replace("Bearer ", "");
        if (!googleTokenService.validateIdToken(idToken)) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_TOKEN_EXPIRED, "RESPONSE_CODE_EXPIRED");
        }
        return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_AUTH_SUCCESS);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public SafeResponse statusCheck(HttpServletRequest httpServletRequest) {
        return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_SERVER_STATUS);
    }
}
