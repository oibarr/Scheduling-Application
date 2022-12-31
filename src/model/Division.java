package model;

/**
 * This class creates the Division object.
 * It contains the constructors, getters, setters, and toString methods.
 */
public class Division {
    private int divId;
    private String divName;
    private int divCountryId;

    /** This method contains the constructor for the Division object.
     * @param divId the division ID
     * @param divName the division name
     * @param divCountryId the associated country ID */
    public Division(int divId, String divName, int divCountryId) {
        this.divId = divId;
        this.divName = divName;
        this.divCountryId = divCountryId;
    }

    /** This method gets the division ID. */
    public int getDivId() {
        return divId;
    }

    /** This  method sets the division ID.
     * @param divId the division ID */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /** This method gets the division name. */
    public String getDivName() {
        return divName;
    }

    /** This method sets the division name.
     * @param divName the division name */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /** This method gets the associated country ID. */
    public int getDivCountryId() {
        return divCountryId;
    }

    /** This method sets the associated country ID.
     * @param divCountryId the associated country ID */
    public void setDivCountryId(int divCountryId) {
        this.divCountryId = divCountryId;
    }

    /** This method returns the division name. */
    @Override
    public String toString() {
        return (divName);
    }

}
