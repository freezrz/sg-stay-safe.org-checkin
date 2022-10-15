package com.iss.project.checkin.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.iss.project.checkin.Constants;
import com.iss.project.checkin.service.GoogleTokenService;
import com.iss.project.checkin.utils.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleTokenServiceImpl implements GoogleTokenService {
    Logger logger = LoggerFactory.getLogger(GoogleTokenServiceImpl.class);

    @Value("${clientId}")
    private String clientId;

    @Autowired
    private Environment env;

    @Override
    public Boolean validateIdToken(String idToken) {
        //todo invoke google service to validate id token
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(env.getProperty("clientId")))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
        try {
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj != null) {
                GoogleIdToken.Payload payload = idTokenObj.getPayload();

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                if (!emailVerified) {
                    logger.error("Email validation failed");
                    return false;
                }
//                String name = (String) payload.get("name");
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                String familyName = (String) payload.get("family_name");
//                String givenName = (String) payload.get("given_name");
//                Long exp = Long.parseLong(payload.get("exp").toString());

                // Use or store profile information
                String anonymuosId = HashUtil.enCrypt(email);
                String idTokenEncy = HashUtil.enCrypt(idToken);
                Constants.tokenMap.put(idTokenEncy, anonymuosId);
            } else {
                logger.error("Invalid ID token.");
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("validateIdToken exception", e);
            return false;
        }
    }

    @Override
    public String validateIdTokenReturnId(String idToken) {
        try {
            // invoke google service to validate id token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(env.getProperty("clientId")))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();
            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj != null) {
                GoogleIdToken.Payload payload = idTokenObj.getPayload();

                // Print user identifier
//                String userId = payload.getSubject();
//                logger.info("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                if (!emailVerified) {
                    logger.error("Email validation failed");
                    return null;
                }

                // Use or store profile information
                String anonymuosId = HashUtil.enCrypt(email);
                return anonymuosId;
            } else {
                logger.error("Invalid ID token.");
                return null;
            }
        } catch (Exception e) {
            logger.error("validateIdToken exception", e);
            return null;
        }
    }
}
