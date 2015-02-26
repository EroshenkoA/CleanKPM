package ru.mipt.apps.cleankpm.tabObjects;

import java.io.Serializable;
import java.util.ArrayList;

import ru.mipt.apps.cleankpm.userObjects.User;

/**
 * Created by User on 2/10/2015.
 */
public class Event implements Serializable {
    //subtle
    public static final int SECRET = 1; //no one sees your intention
    public static final int SUBTLE = 2; // shown on your profile
    public static final int IMPOSING = 3; // everyone sees in his feed
    public final int UNSET = -1;
    //
    //private User creator;
    private User creator;
    private String description="";
    private String eventName;
    private int subtle = IMPOSING;//виден или не виден всем
    private int day = UNSET;
    private int month = UNSET;
    private int year = UNSET;
    private int hoursStart=UNSET;
    private int hoursEnd=UNSET;
    private int minutesStart=UNSET;
    private int minutesEnd=UNSET;
    private int minFriends = UNSET;
    private ArrayList<User> mayAttendPeople;
    private ArrayList<User> willAttendPeople;
    private boolean conditionsComplied = false;

    public Event(){
        //setCreator
        mayAttendPeople = new ArrayList<>();
        willAttendPeople = new ArrayList<>();
        eventName = null;
    }

    public void setMinFriends(int f){
        minFriends = f;
    }

    public void setSubtle(int s){
        if ((s>=0)||(s<=2)){
            subtle = s;
        }else{
            System.out.println("wong argument for setSubtle()");
        }

    }
    public void setDay(int d){
        day = d;
    }
    public void setMonth(int m){
        month = m;
    }
    public void setYear(int y){
        year = y;
    }

    public void setEventName(String name){
        eventName = name;
    }

    public boolean isYetUnset(){

        if (minFriends == UNSET){
            return true;
        }
        if (day == UNSET){
            return true;
        }
        if (month == UNSET){
            return true;
        }
        if (year == UNSET){
            return true;
        }
        if (hoursEnd == UNSET){
            return true;
        }
        if (hoursStart == UNSET){
            return true;
        }
        if (minutesEnd == UNSET){
            return true;
        }
        if (minutesStart == UNSET){
            return true;
        }
        return false;
    }

    public String getEventName(){
        return eventName;
    }

    public int getHoursStart() {
        return hoursStart;
    }

    public void setHoursStart(int hoursStart) {
        this.hoursStart = hoursStart;
    }

    public int getHoursEnd() {
        return hoursEnd;
    }

    public void setHoursEnd(int hoursEnd) {
        this.hoursEnd = hoursEnd;
    }

    public int getMinutesStart() {
        return minutesStart;
    }

    public void setMinutesStart(int minutesStart) {
        this.minutesStart = minutesStart;
    }

    public int getMinutesEnd() {
        return minutesEnd;
    }

    public void setMinutesEnd(int minutesEnd) {
        this.minutesEnd = minutesEnd;
    }
}
