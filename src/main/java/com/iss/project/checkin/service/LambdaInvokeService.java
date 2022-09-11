package com.iss.project.checkin.service;

import com.iss.project.checkin.model.CheckinRequest;
import com.iss.project.checkin.model.LambdaResponse;
import com.iss.project.checkin.model.SafeResponse;

public interface LambdaInvokeService {

    LambdaResponse invokeLambdaFunction(CheckinRequest request, String function);
}
