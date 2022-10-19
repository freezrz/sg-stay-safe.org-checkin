package com.iss.project.checkin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.iss.project.checkin.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CheckinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIn() throws Exception {
        String requestStr = "{\"anonymous_id\":\"myidisfabulous\",\"site_id\":\"doesntcaremycharm\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",UNIT_TEST_MOCK_IDTOKEN)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_TOKEN_EXPIRED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("RESPONSE_CODE_EXPIRED"))
                .andDo(print());
    }

    @Test
    void checkInWithEmptyToken() throws Exception {
        String requestStr = "{\"anonymous_id\":\"myidisfabulous\",\"site_id\":\"doesntcaremycharm\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_TOKEN_EMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Forbidden request, need to provide valid token"))
                .andDo(print());
    }

    @Test
    void checkInWithEmptySiteId() throws Exception {
        String requestStr = "{\"anonymous_id\":\"myidisfabulous\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",UNIT_TEST_MOCK_IDTOKEN)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_INVALID_PARAM))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Miss required param"))
                .andDo(print());
    }

    @Test
    void authToken() throws Exception {
        String requestStr = "{\"anonymousId\":\"myidisfabulous\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authentication")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",UNIT_TEST_MOCK_IDTOKEN)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_TOKEN_EXPIRED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("RESPONSE_CODE_EXPIRED"))
                .andDo(print());
    }

    @Test
    void authTokenWithEmptyAnonymousId() throws Exception {
        String requestStr = "{\"anonymousId\":\"\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authentication")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",UNIT_TEST_MOCK_IDTOKEN)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_INVALID_PARAM))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Miss required param"))
                .andDo(print());
    }

    @Test
    void authTokenWithEmptyToken() throws Exception {
        String requestStr = "{\"anonymousId\":\"myidisfabulous\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authentication")
                        .content(requestStr.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(RESPONSE_CODE_TOKEN_EMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("Forbidden request, need to provide valid token"))
                .andDo(print());
    }

}