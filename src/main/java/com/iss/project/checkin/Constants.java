package com.iss.project.checkin;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int RESPONSE_CODE_SUCCESS = 0;
    public static final int RESPONSE_CODE_UNKNOWN = 502;
    public static final int RESPONSE_CODE_EXPIRED_TOKEN = 501;

    public static final String RESPONSE_MSG_CHECKIN_SUCCESS = "check in successfully";
    public static final String RESPONSE_MSG_AUTH_SUCCESS = "Validate token successfully";

    public static final String LAMBDA_FUNCTION_SANITISE_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:sanitise_checkin";
    public static final String LAMBDA_FUNCTION_ANTIFRAUD_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:anti_fraud_checkin";
    public static final String LAMBDA_FUNCTION_RECORD_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:record_checkin";
    public static final String LAMBDA_FUNCTION_PRODUCE_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:produce_checkin_event";
    public static final String LAMBDA_FUNCTION_VERIFY_RULES_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:verify_rules";
    public static final String LAMBDA_FUNCTION_PRODUCE_USER_VIOLATION_CHECKIN = "produce_user_violation_event";
    public static final String LAMBDA_FUNCTION_PRODUCE_SITE_VIOLATION_CHECKIN = "produce_site_violation_event";

    public static final Integer LAMBDA_RESPONSE_CODE_SITE_BANNED = 452001;
    public static final Integer LAMBDA_RESPONSE_CODE_USER_BANNED = 451001;
    public static final Integer LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED = 451004;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_BANNED_CACHE = 451002;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_MAX_CHECKIN_CACHE = 451003;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_24HRS_CHECKIN = 451005;

    public static final Integer LAMBDA_RESPONSE_CODE_SITE_BANNED_CACHE = 452002;

    public static Map<String, String> tokenMap = new HashMap<>();

    private static Map<Integer, String> msgMap = ImmutableMap.of(
            LAMBDA_RESPONSE_CODE_SITE_BANNED, "This site has been banned."
            ,LAMBDA_RESPONSE_CODE_USER_BANNED_CACHE, "You have been banned, pls contact relevant team."
            ,LAMBDA_RESPONSE_CODE_SITE_BANNED_CACHE, "This site has been banned."
            ,LAMBDA_RESPONSE_CODE_USER_BANNED, "You have been banned, pls contact relevant team."
            ,LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED, "You have reached the max checkin limits, pls stay home."
            ,LAMBDA_RESPONSE_CODE_USER_24HRS_CHECKIN, "This site has reached the max number of visitor today.");

    public static String getMsg(Integer msgCode, String msg) {
        String msgOfMap = msgMap.get(msgCode);
        if (msgOfMap == null) {
            return "Unable to checkin, pls stay home.";
        }
        if (msgOfMap.isEmpty()) {
            return msg;
        }
        return msgOfMap;
    }
}
