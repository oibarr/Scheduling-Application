package model;

public class Division {
    private int divId;
    private String divName;
    private int divCountryId;

    public Division(int divId, String divName, int divCountryId){
        this.divId = divId;
        this.divName = divName;
        this.divCountryId = divCountryId;
    }

    public int getDivId(){
        return divId;
    }

    public void setDivId(int divId){
        this.divId = divId;
    }

    public String getDivName(){
        return divName;
    }

    public void setDivName(String divName){
        this.divName = divName;
    }

    public int getDivCountryId(){
        return divCountryId;
    }

    public void setDivCountryId(int divCountryId){
        this.divCountryId = divCountryId;
    }

    @Override
    public String toString(){
        return (divName);
    }

}
