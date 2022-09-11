package com.iss.project.checkin.model;

public class CheckinRequest {
    private String anonymous_id;
    private String site_id;

    public String getAnonymous_id() {
        return anonymous_id;
    }

    public void setAnonymous_id(String anonymous_id) {
        this.anonymous_id = anonymous_id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
}
