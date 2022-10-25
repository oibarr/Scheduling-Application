package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int appId;
    private String appTitle;
    private String appDesc;
    private String appLoc;
    private String appType;
    private LocalDateTime appStart;
    private LocalDateTime appEnd;
    private int appCustId;
    private int appUserId;
    private int appContId;


    public Appointment(int appId, String appTitle, String appDesc, String appLoc, String appType, LocalDateTime appStart, LocalDateTime appEnd, int appCustId, int appUserId, int appContId){
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.appLoc = appLoc;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.appCustId = appCustId;
        this.appUserId = appUserId;
        this.appContId = appContId;
    }

    public Appointment(String appType) {
        this.appType = appType;
    }

    public Appointment(String appTitle, String appLoc){
        this.appTitle = appTitle;
        this.appLoc = appLoc;
        this.appType = appLoc;
    }

    public Appointment(String appTitle, LocalDateTime appStart){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM");
        this.appTitle = appTitle;
        this.appStart = appStart;
        this.appType = dateTimeFormatter.format(appStart);
    }

    public Appointment(int appContId, String appLoc){
        this.appContId = appContId;
        this.appLoc = appLoc;
        this.appType = appLoc;
    }

    public int getAppId(){
        return appId;
    }

    public void setAppId(int appId){
        this.appId = appId;
    }

    public String getAppTitle(){
        return appTitle;
    }

    public void setAppTitle(String appTitle){
        this.appTitle = appTitle;
    }

    public String getAppDesc(){
        return appDesc;
    }

    public void setAppDesc(String appDesc){
        this.appDesc = appDesc;
    }

    public String getAppLoc(){
        return appLoc;
    }

    public void setAppLoc(String appLoc){
        this.appLoc = appLoc;
    }

    public String getAppType(){
        return appType;
    }

    public void setAppType(String appType){
        this.appType = appType;
    }

    public LocalDateTime getAppStart() {
        return appStart;
    }

    public void setAppStart(LocalDateTime appStart){
        this.appStart = appStart;
    }

    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    public void setAppEnd(LocalDateTime appEnd){
        this.appEnd = appEnd;
    }

    public int getAppCustId(){
        return appCustId;
    }

    public void setAppCustId(int appCustId){
        this.appCustId = appCustId;
    }

    public int getAppUserId(){
        return appUserId;
    }

    public void setAppUserId(int appUserId){
        this.appUserId = appUserId;
    }

    public int getAppContId(){
        return appContId;
    }

    public void setAppContId(int appContId){
        this.appContId = appContId;
    }

    @Override public String toString(){
        return (appType);
    }
}