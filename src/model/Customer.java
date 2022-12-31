package model;

/**
 * This class creates the Customer object.
 * It contains the constructors, getters, setters, and toString methods.
 */
public class Customer {
    private int custId;
    private String custName;
    private String custAddress;
    private String custPost;
    private String custPhone;
    private int custDivId;

    /** This method contains the constructor for the customer object.
     * @param custId the customer ID
     * @param custName the customer name
     * @param custAddress the customer address
     * @param custPost the customer postal code
     * @param custPhone the customer phone number
     * @param custDivId the associated division ID */
    public Customer(int custId, String custName, String custAddress, String custPost, String custPhone, int custDivId) {
        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPost = custPost;
        this.custPhone = custPhone;
        this.custDivId = custDivId;
    }

    /** This method contains an overloaded constructor for the customer object. */
    public Customer(String custName, String custAddress, String custPost, String custPhone, int custDivId) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPost = custPost;
        this.custPhone = custPhone;
        this.custDivId = custDivId;
    }

    /**
     * This method gets the customer ID.
     */
    public int getCustId() {
        return custId;
    }

    /**
     * This method sets the customer ID.
     *
     * @param custId the customer ID
     */
    public void setCustId(int custId) {
        this.custId = custId;
    }

    /**
     * This method gets the customer name.
     */
    public String getCustName() {
        return custName;
    }

    /**
     * This method sets the customer name.
     *
     * @param custName the customer name
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * This method gets the customer address.
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * This method sets the customer address.
     *
     * @param custAddress the customer address
     */
    public void getCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    /**
     * This method gets the customer postal code.
     */
    public String getCustPost() {
        return custPost;
    }

    /**
     * This method sets the customer postal code.
     *
     * @param custPost the customer postal code
     */
    public void setCustPost(String custPost) {
        this.custPost = custPost;
    }

    /**
     * This method gets the customer phone number.
     */
    public String getCustPhone() {
        return custPhone;
    }

    /**
     * This method sets the customer phone number.
     *
     * @param custNum the customer phone number
     */
    public void set(String custNum) {
        this.custPhone = custNum;
    }

    /**
     * This method gets the associated division ID.
     */
    public int getCustDivId() {
        return custDivId;
    }

    /**
     * This method sets the associated division ID.
     *
     * @param custDivId the associated division ID
     */
    public void setCustDivId(int custDivId) {
        this.custDivId = custDivId;
    }

    /**
     * This method returns the customer name.
     */
    @Override
    public String toString() {
        return (custName);
    }
}
