package com.example.lbycpa2_finalproject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

import static com.example.lbycpa2_finalproject.Main.scheduleLength;

public class CompiledSchedule {
    private int[] mondayCommonTime;
    private int[] tuesdayCommonTime;
    private int[] wednesdayCommonTime;
    private int[] thursdayCommonTime;
    private int[] fridayCommonTime;
    private int[] saturdayCommonTime;

    @FXML
    private Rectangle rectangle;
    public CompiledSchedule(){

        mondayCommonTime = new int[scheduleLength];
        tuesdayCommonTime = new int[scheduleLength];
        wednesdayCommonTime = new int[scheduleLength];
        thursdayCommonTime = new int[scheduleLength];
        fridayCommonTime = new int[scheduleLength];
        saturdayCommonTime = new int[scheduleLength];
        for(int i = 0; i < scheduleLength; i++){
            mondayCommonTime[i] = 1;
            tuesdayCommonTime[i] = 1;
            wednesdayCommonTime[i] = 1;
            thursdayCommonTime[i] = 1;
            fridayCommonTime[i] = 1;
            saturdayCommonTime[i] = 1;
        }
    }


    public void setMondayCommonTime(int[] mondayCommonTime) {
        this.mondayCommonTime = mondayCommonTime;
    }

    public int[] getMondayCommonTime(){
        return this.mondayCommonTime;
    }

    public int[] getTuesdayCommonTime() {
        return this.tuesdayCommonTime;
    }

    public void setTuesdayCommonTime(int[] tuesdayCommonTime) {
        this.tuesdayCommonTime = tuesdayCommonTime;
    }

    public int[] getWednesdayCommonTime() {
        return this.wednesdayCommonTime;
    }

    public void setWednesdayCommonTime(int[] wednesdayCommonTime) {
        this.wednesdayCommonTime = wednesdayCommonTime;
    }

    public int[] getThursdayCommonTime() {
        return this.thursdayCommonTime;
    }

    public void setThursdayCommonTime(int[] thursdayCommonTime) {
        this.thursdayCommonTime = thursdayCommonTime;
    }

    public int[] getFridayCommonTime() {
        return this.fridayCommonTime;
    }

    public void setFridayCommonTime(int[] fridayCommonTime) {
        this.fridayCommonTime = fridayCommonTime;
    }

    public int[] getSaturdayCommonTime() {
        return this.saturdayCommonTime;
    }

    public void setSaturdayCommonTime(int[] saturdayCommonTime) {
        this.saturdayCommonTime = saturdayCommonTime;
    }
}
