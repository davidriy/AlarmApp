package com.example.alarmapp_v1;

import java.io.Serializable;

public class Alarm  implements Serializable {
    String title;
    String hour;
    String minute;
    boolean active;
    DaysOfWeek daysOfWeek;
    public Alarm(){}
    public Alarm(String title, boolean active, String hour, String minute, DaysOfWeek daysOfWeek){
        this.title = title;
        this.active = active;
        this.hour = hour;
        this.minute = minute;
        this.daysOfWeek = daysOfWeek;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public String getFullTime(){
        return this.hour + ":" + this.minute;
    }

    public void setHour(String hour){
        this.hour = hour;
    }
    public void setMinute(String minute){
        this.minute = minute;
    }
    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }
    public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

}
