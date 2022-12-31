package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class creates the Appointment object.
 * It contains the constructors, getters, setters, and toString methods.
 */
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

    /**
     * This method contains the constructor for the Appointment object.
     *
     * @param appId     the appointment ID
     * @param appTitle  the appointment title
     * @param appDesc   the appointment description
     * @param appLoc    the appointment location
     * @param appType   the appointment type
     * @param appStart  the appointment start date and start time
     * @param appEnd    the appointment end date and end time
     * @param appCustId the associated customer ID
     * @param appUserId the associated user ID
     * @param appContId the associated contact ID
     */
    public Appointment(int appId, String appTitle, String appDesc, String appLoc, String appType, LocalDateTime appStart, LocalDateTime appEnd, int appCustId, int appUserId, int appContId) {
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

    /** This method contains an overloaded constructor for the appointment object. */
    public Appointment(String appTitle, String appDesc, String appLoc, String appType, LocalDateTime appStart, LocalDateTime appEnd, int appCustId, int appUserId, int appContId) {
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

    /** This method contains an overloaded constructor for the appointment object. */
    public Appointment(String appTitle, String appLoc) {
        this.appTitle = appTitle;
        this.appLoc = appLoc;
        this.appType = appLoc;
    }

    /** This method contains an overloaded constructor for the appointment object. */
    public Appointment(String appTitle, LocalDateTime appStart){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM");
        this.appTitle = appTitle;
        this.appStart = appStart;
        this.appType = dateTimeFormatter.format(appStart);
    }

    /**
     * This method contains an overloaded constructor for the appointment object.
     */
    public Appointment(int appContId, String appLoc) {
        this.appContId = appContId;
        this.appLoc = appLoc;
        this.appType = appLoc;
    }

    /**
     * This method contains an overloaded constructor for the appointment object.
     */
    public Appointment(String appType) {
        this.appType = appType;
    }

    /**
     * This method gets the appointment ID.
     */
    public int getAppId() {
        return appId;
    }

    /**
     * This method sets the appointment ID.
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * This method gets the appointment title.
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * This method sets the appointment title.
     *
     * @param appTitle the appointment title
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * This method gets the appointment description.
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * This method sets the appointment description.
     *
     * @param appDesc the appointment description
     */
    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    /**
     * This method gets the appointment location.
     */
    public String getAppLoc() {
        return appLoc;
    }

    /**
     * This method sets the appointment location.
     *
     * @param appLoc the appointment location
     */
    public void setAppLoc(String appLoc) {
        this.appLoc = appLoc;
    }

    /**
     * This method gets the appointment type.
     */
    public String getAppType() {
        return appType;
    }

    /**
     * This method sets the appointment type.
     *
     * @param appType the appointment type
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * This method gets the appointment start date and start time.
     */
    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     * This method sets the appointment start date and start time.
     *
     * @param appStart the appointment start date and time
     */
    public void setAppStart(LocalDateTime appStart) {
        this.appStart = appStart;
    }

    /**
     * This method gets the appointment end date and end time.
     */
    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    /**
     * This method sets the appointment end date and end time.
     *
     * @param appEnd the appointment end date and time
     */
    public void setAppEnd(LocalDateTime appEnd) {
        this.appEnd = appEnd;
    }

    /**
     * This method gets the associated customer ID.
     */
    public int getAppCustId() {
        return appCustId;
    }

    /**
     * This method sets the associated customer ID.
     *
     * @param appCustId the associated customer ID
     */
    public void setAppCustId(int appCustId) {
        this.appCustId = appCustId;
    }

    /**
     * This method gets the associated user ID.
     */
    public int getAppUserId() {
        return appUserId;
    }

    /**
     * This method sets the associated user ID.
     *
     * @param appUserId the associated user ID
     */
    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    /**
     * This method gets the associated contact ID.
     */
    public int getAppContId() {
        return appContId;
    }

    /**
     * This method sets the associated contact ID.
     *
     * @param appContId the associated contact ID
     */
    public void setAppContId(int appContId) {
        this.appContId = appContId;
    }

    /**
     * This method returns the appointment type.
     */
    @Override
    public String toString(){
        return (appType);
    }
}