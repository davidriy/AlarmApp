package com.example.alarmapp_v1;

import java.io.Serializable;

public class DaysOfWeek implements Serializable {
    boolean day1;
    boolean day2;
    boolean day3;
    boolean day4;
    boolean day5;
    boolean day6;
    boolean day7;

    public DaysOfWeek(boolean day1, boolean day2, boolean day3, boolean day4, boolean day5, boolean day6, boolean day7) {
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
    }

    public DaysOfWeek() {}

    public void setDay1(boolean day1) {
        this.day1 = day1;
    }

    public void setDay2(boolean day2) {
        this.day2 = day2;
    }

    public void setDay3(boolean day3) {
        this.day3 = day3;
    }

    public void setDay4(boolean day4) {
        this.day4 = day4;
    }

    public void setDay5(boolean day5) {
        this.day5 = day5;
    }

    public void setDay6(boolean day6) {
        this.day6 = day6;
    }

    public void setDay7(boolean day7) {
        this.day7 = day7;
    }



    public boolean isDay1() {
        return day1;
    }

    public boolean isDay2() {
        return day2;
    }

    public boolean isDay3() {
        return day3;
    }

    public boolean isDay4() {
        return day4;
    }

    public boolean isDay5() {
        return day5;
    }

    public boolean isDay6() {
        return day6;
    }

    public boolean isDay7() {
        return day7;
    }
}
