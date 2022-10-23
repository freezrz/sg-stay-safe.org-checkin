package com.iss.project.checkin;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int RESPONSE_CODE_SUCCESS = 0;
    public static final int RESPONSE_CODE_UNKNOWN = 502;

    public static final int RESPONSE_CODE_TOKEN_EXPIRED = 501;

    public static final int RESPONSE_CODE_TOKEN_EMPTY = 403;

    public static final int RESPONSE_CODE_SECURITY_INVALID_API_KEY = 403;

    public static final int RESPONSE_CODE_INVALID_PARAM = 503;

    public static final String RESPONSE_MSG_CHECKIN_SUCCESS = "check in successfully";
    public static final String RESPONSE_MSG_AUTH_SUCCESS = "Validate token successfully";

    public static final String LAMBDA_FUNCTION_SANITISE_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:sanitise_checkin";
    public static final String LAMBDA_FUNCTION_ANTIFRAUD_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:anti_fraud_checkin";
    public static final String LAMBDA_FUNCTION_RECORD_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:record_checkin";
    public static final String LAMBDA_FUNCTION_PRODUCE_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:produce_checkin_event";
    public static final String LAMBDA_FUNCTION_VERIFY_RULES_CHECKIN = "arn:aws:lambda:ap-southeast-1:574191674376:function:verify_rules";
    public static final String LAMBDA_FUNCTION_PRODUCE_USER_VIOLATION_CHECKIN = "produce_user_violation_event";
    public static final String LAMBDA_FUNCTION_PRODUCE_SITE_VIOLATION_CHECKIN = "produce_site_violation_event";

    public static final Integer LAMBDA_RESPONSE_CODE_SANITISE = 41001;

    public static final Integer LAMBDA_RESPONSE_CODE_ANTIFRUAD = 44001;
    public static final Integer LAMBDA_RESPONSE_CODE_SITE_BANNED = 452001;
    public static final Integer LAMBDA_RESPONSE_CODE_USER_BANNED = 451001;
    public static final Integer LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED = 451004;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_BANNED_CACHE = 451002;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_MAX_CHECKIN_CACHE = 451003;

    public static final Integer LAMBDA_RESPONSE_CODE_USER_24HRS_CHECKIN = 451005;

    public static final Integer LAMBDA_RESPONSE_CODE_SITE_BANNED_CACHE = 452002;

    public static final String UNIT_TEST_MOCK_IDTOKEN = "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjIwOWMwNTdkM2JkZDhjMDhmMmQ1NzM5Nzg4NjMyNjczZjdjNjI0MGYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNjUxNDkzNjM3MTEtNDdtZDlmc3NrdGxhM2FycXR1ZzNucHBxNzA1a2FxMmIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxNjUxNDkzNjM3MTEtbWdzZ3NwazhpaXV2ZXU2dmdhajg2MHZwbmk0a29idTQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDIxMzE1ODI3MDQ1MTQyNzMzNzQiLCJlbWFpbCI6InJvbmd6ZXpoYW5nMzNAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJyb25nemUgemhhbmciLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUxtNXd1MDhQY2oyQmtjR2VQcllvc3ppdHd4Um1hN3FjWmg5eGtSc3pmcm49czk2LWMiLCJnaXZlbl9uYW1lIjoicm9uZ3plIiwiZmFtaWx5X25hbWUiOiJ6aGFuZyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjYzOTQ2NTE0LCJleHAiOjE2NjM5NTAxMTR9.lbtMjzRgplQWwBGsFvOaYXGw566YZV16MM5fEGA39vOj6qMLuJmIWk9WdIErput8qOMuuFn16dKGkDspQTAUZjdOHNP1BiW-2fqzO9ZZCYBy-lC01ZPGWpz2Ed5hdKvL0Z1SzkvrUiZsJL50u8niiy5likPE13AU_I2M1VgjRdKgNRxQOqm4tWmvw-VLjXxODazT0k8v7uvACxr2Hzdg8Xz7wFPRedSkWUEBM-JP9t_zZy1xPKNU3roTlB5O1IUmMtx8knYoNq4n1Jb8CGxqp6m_UM2YIMJLbRM2PzjopGvfUE_PA811THLNSPUuCYtfs5MH1pyJcZ6778qmnRXNJA";

    public static final String UNIT_TEST_MOCK_IDTOKEN_2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjIwOWMwNTdkM2JkZDhjMDhmMmQ1NzM5Nzg4NjMyNjczZjdjNjI0MGYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNjUxNDkzNjM3MTEtNDdtZDlmc3NrdGxhM2FycXR1ZzNucHBxNzA1a2FxMmIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxNjUxNDkzNjM3MTEtbWdzZ3NwazhpaXV2ZXU2dmdhajg2MHZwbmk0a29idTQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDIxMzE1ODI3MDQ1MTQyNzMzNzQiLCJlbWFpbCI6InJvbmd6ZXpoYW5nMzNAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJyb25nemUgemhhbmciLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUxtNXd1MDhQY2oyQmtjR2VQcllvc3ppdHd4Um1hN3FjWmg5eGtSc3pmcm49czk2LWMiLCJnaXZlbl9uYW1lIjoicm9uZ3plIiwiZmFtaWx5X25hbWUiOiJ6aGFuZyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjYzOTQ2NTE0LCJleHAiOjE2NjM5NTAxMTR9.lbtMjzRgplQWwBGsFvOaYXGw566YZV16MM5fEGA39vOj6qMLuJmIWk9WdIErput8qOMuuFn16dKGkDspQTAUZjdOHNP1BiW-2fqzO9ZZCYBy-lC01ZPGWpz2Ed5hdKvL0Z1SzkvrUiZsJL50u8niiy5likPE13AU_I2M1VgjRdKgNRxQOqm4tWmvw-VLjXxODazT0k8v7uvACxr2Hzdg8Xz7wFPRedSkWUEBM-JP9t_zZy1xPKNU3roTlB5O1IUmMtx8knYoNq4n1Jb8CGxqp6m_UM2YIMJLbRM2PzjopGvfUE_PA811THLNSPUuCYtfs5MH1pyJcZ6778qmnRXNJA";

    public static final String SECURITY_MANAGER_API_KEY = "API_KEY_ENCRYPTION";

    public static final String SECURITY_MANAGER_AES_KEY = "AES_KEY";

    public static final String SECURITY_MANAGER_AES_SALT = "AES_SALT";

    public static final String SECURITY_MANAGER_AES_KEY_VALUE = "AES_KEY";

    public static final String SECURITY_MANAGER_AES_SALT_VALUE  = "AES_SALT";

    public static Map<String, String> tokenMap = new HashMap<>();

    private static Map<Integer, String> msgMap = ImmutableMap.of(
            LAMBDA_RESPONSE_CODE_SITE_BANNED, "This site has been banned."
            ,LAMBDA_RESPONSE_CODE_USER_BANNED_CACHE, "You have been banned, pls contact relevant team."
            ,LAMBDA_RESPONSE_CODE_SITE_BANNED_CACHE, "This site has been banned."
            ,LAMBDA_RESPONSE_CODE_USER_BANNED, "You have been banned, pls contact relevant team."
            ,LAMBDA_RESPONSE_CODE_USER_EXCEED_MAX_BANNED, "You have reached the max checkin limits, pls stay home."
            ,LAMBDA_RESPONSE_CODE_USER_24HRS_CHECKIN, "This site has reached the max number of visitor today."
            ,LAMBDA_RESPONSE_CODE_ANTIFRUAD, "");

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
