package com.iss.project.checkin;

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

    public static final int LAMBDA_RESPONSE_CODE_SITE_BANNED = 452001;
    public static final int LAMBDA_RESPONSE_CODE_USER_BANNED = 451001;
    public static final int LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED = 451004;

    public static Map<String, String> tokenMap = new HashMap<>();
}
