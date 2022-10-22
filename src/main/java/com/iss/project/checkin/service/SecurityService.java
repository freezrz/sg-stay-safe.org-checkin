package com.iss.project.checkin.service;

public interface SecurityService {

    String encryptInfoWithAES(String encryptStr) throws Exception;

    String decryptInfoWithAES(String ciphertext) throws Exception;

    String getSecret(String key);
}
