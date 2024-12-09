package com.example.lbycpa2_finalproject;

import static com.example.lbycpa2_finalproject.Main.scheduleLength;

public class CompiledSchedule {
    private int[] mondayCommonTime;
    private int[] tuesdayCommonTime;
    private int[] wednesdayCommonTime;
    private int[] thursdayCommonTime;
    private int[] fridayCommonTime;
    private int[] saturdayCommonTime;

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
    public void setTuesdayFridayCommonTime(int[] tuesdayFridayCommonTime) {
        this.tuesdayCommonTime = combineArrays(this.tuesdayCommonTime, tuesdayFridayCommonTime);
        this.fridayCommonTime = combineArrays(this.fridayCommonTime, tuesdayFridayCommonTime);
    }

    public void setMondayThursdayCommonTime(int[] mondayThursdayCommonTime) {
        this.mondayCommonTime = combineArrays(this.mondayCommonTime, mondayThursdayCommonTime);
        this.thursdayCommonTime = combineArrays(this.thursdayCommonTime, mondayThursdayCommonTime);
    }

    // Helper method to combine two arrays based on the logic provided
    private int[] combineArrays(int[] original, int[] newArray) {
        int[] combinedArray = new int[original.length];

        for (int i = 0; i < original.length; i++) {
            if (original[i] == 0 || newArray[i] == 0) {
                combinedArray[i] = 0;
            } else {
                combinedArray[i] = 1;
            }
        }

        return combinedArray;
    }
}
