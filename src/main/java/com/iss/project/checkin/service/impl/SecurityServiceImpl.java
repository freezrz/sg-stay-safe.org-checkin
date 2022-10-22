package com.iss.project.checkin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.iss.project.checkin.security.AESEncryptServiceBo;
import com.iss.project.checkin.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.iss.project.checkin.Constants.SECURITY_MANAGER_AES_KEY;

@Service
public class SecurityServiceImpl implements SecurityService {

    final String keyArn = "arn:aws:kms:ap-southeast-1:574191674376:key/61ab07fa-247c-47b0-b670-c0ebc4348ba1";

    @Override
    public String encryptInfoWithAES(String encryptStr) throws Exception {
        String pass = getSecret(SECURITY_MANAGER_AES_KEY);
//        String pass = "nq65xX2FBqtYgJrA";
        SecretKeySpec keySpec = new SecretKeySpec(pass.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher encryptCipher = Cipher.getInstance("AES/CFB/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(new byte[encryptCipher.getBlockSize()]));
        byte[] newByte = encryptCipher.doFinal(encryptStr.getBytes(StandardCharsets.ISO_8859_1));
        return AESEncryptServiceBo.parseByte2HexStr(newByte);
    }

    @Override
    public String decryptInfoWithAES(String ciphertext) throws Exception {
        String pass = getSecret(SECURITY_MANAGER_AES_KEY);
//        String pass = "nq65xX2FBqtYgJrA";
        SecretKeySpec keySpec = new SecretKeySpec(pass.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher decryptCipher = Cipher.getInstance("AES/CFB/NoPadding");
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(new byte[decryptCipher.getBlockSize()]));
        byte[] newByte = decryptCipher.doFinal(AESEncryptServiceBo.parseHexStr2Byte(ciphertext));
        return new String(newByte, StandardCharsets.ISO_8859_1);
    }

    @Override
    public String getSecret(String key) {

        String secretName = "prod/apiKey";
        String region = "ap-southeast-1";

        // Create a Secrets Manager client
        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.

        String secret = null, decodedBinarySecret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS key.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        }
        else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }
        System.out.println("getSecret: " + secret);
        JSONObject secretjson = JSON.parseObject(secret);
        secret = secretjson.getString(key);
        return secret;
    }
}
