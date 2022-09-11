package com.iss.project.checkin.model;

public class ViolationEvent {

    private CheckinRequest checkin;
    private LambdaResponse resp;

    public ViolationEvent(CheckinRequest checkin, LambdaResponse resp) {
        this.checkin = checkin;
        this.resp = resp;
    }

    public CheckinRequest getCheckin() {
        return checkin;
    }

    public void setCheckin(CheckinRequest checkin) {
        this.checkin = checkin;
    }

    public LambdaResponse getResp() {
        return resp;
    }

    public void setResp(LambdaResponse resp) {
        this.resp = resp;
    }
}
