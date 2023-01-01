package model;

/**
 * This class creates the Country object.
 * It contains the constructors, getters, setters, and toString methods.
 */
public class Country {
    private int countryId;
    private String countryName;

    /**
     * This method contains the constructor for the Country object.
     *
     * @param countryId   the country ID
     * @param countryName the country name
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * This method contains an overloaded constructor for the Country object.
     */
    public Country(int countryId) {
        this.countryId = countryId;
    }

    /**
     * This method gets the country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * This method sets the country ID.
     *
     * @param countryId the country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * This method gets the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method sets the country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * This method returns the country name.
     */
    @Override
    public String toString() {
        return (countryName);
    }
}
