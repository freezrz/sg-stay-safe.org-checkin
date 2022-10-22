package com.iss.project.checkin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.util.StringUtils;
import com.iss.project.checkin.Constants;
import com.iss.project.checkin.model.*;
import com.iss.project.checkin.service.SecurityService;
import com.iss.project.checkin.utils.HashUtil;
import org.omg.CORBA.TRANSACTION_MODE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class SecurityController {
    @Autowired
    SecurityService securityService;

    Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @RequestMapping(value = "/getQR", method = RequestMethod.POST)
    @ResponseBody
    public SafeResponse encryptQRInfo(@RequestBody(required = false) SecurityRequest request, HttpServletRequest httpServletRequest) {
        if (request != null)
            logger.info("SecurityController.encryptQRInfo: {}", JSON.toJSONString(request));
        if (!validateRequest(request))
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_INVALID_PARAM, "Miss required param");
        // todo validate api key
        String apiKey = httpServletRequest.getHeader("apiKey");
        if (StringUtils.isNullOrEmpty(apiKey)) {
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_TOKEN_EMPTY, "Forbidden request, need to provide valid api key");
        }
        // todo recover
//        if(!apiKey.equals(securityService.getSecret(Constants.SECURITY_MANAGER_API_KEY))) {
//            return SafeResponse.responseFail(Constants.RESPONSE_CODE_SECURITY_INVALID_API_KEY, "Forbidden request, need to provide valid api key");
//        }
        // todo encrypt QR info
        try {
            String clearText = HashUtil.enCrypt(JSON.toJSONString(request));
            logger.info("encryptQRInfo clearText: " + JSON.toJSONString(request));
            String ciphertext = securityService.encryptInfoWithAES(JSON.toJSONString(request));
            logger.info("encryptQRInfo ciphertext: " + ciphertext);
            String decryptStr = securityService.decryptInfoWithAES(ciphertext);
            logger.info("encryptQRInfo decryptStr: " + decryptStr);

            logger.info((String) JSONObject.parseObject(decryptStr).get("issueTime"));
            return SafeResponse.responseSuccess(Constants.RESPONSE_MSG_AUTH_SUCCESS, ciphertext);

        } catch (Exception e) {
            logger.error("encryptQRInfo", e);
            return SafeResponse.responseFail(Constants.RESPONSE_CODE_UNKNOWN, "Encrypt QR info failed, pls try again");
        }

    }

    private Boolean validateRequest (SecurityRequest request) {
        if (Objects.isNull(request)
                || StringUtils.isNullOrEmpty(request.getSiteId())
                || StringUtils.isNullOrEmpty(request.getIssueTime())) {
            logger.error("SecurityController.encryptQRInfo invalid request={}", JSON.toJSONString(request));
            return false;
        }
        return true;
    }
}
