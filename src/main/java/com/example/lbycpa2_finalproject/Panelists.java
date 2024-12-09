package com.example.lbycpa2_finalproject;

public class Panelists {
    private String panelistName;
    private String scheduleUnavailableDay;
    private String scheduleTime;

    public Panelists(String panelistName, String scheduleUnavailableDay, String scheduleTime) {
        this.panelistName = panelistName;
        this.scheduleUnavailableDay = scheduleUnavailableDay;
        this.scheduleTime = scheduleTime;
    }


    public String getPanelistName() {
        return panelistName;
    }

    public void setPanelistName(String panelistName) {
        this.panelistName = panelistName;
    }

    public String getScheduleUnavailableDay() {
        return scheduleUnavailableDay;
    }

    public void setScheduleUnavailableDay(String scheduleUnavailableDay) {
        this.scheduleUnavailableDay = scheduleUnavailableDay;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}

