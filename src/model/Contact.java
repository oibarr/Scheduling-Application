package model;

/**
 * This class creates the Contact object.
 * It contains the constructors, getters, setters, and toString methods.
 */
public class Contact {
    private int contId;
    private String contName;
    private String contEmail;

    /**
     * This method contains the constructor for the Contact object.
     *
     * @param contId    the contact ID
     * @param contName  the contact name
     * @param contEmail the contact email
     */
    public Contact(int contId, String contName, String contEmail) {
        this.contId = contId;
        this.contName = contName;
        this.contEmail = contEmail;
    }

    /**
     * This method gets the contact name.
     */
    public Contact(String contName) {
        this.contName = contName;
    }

    /**
     * This method gets the contact ID.
     */
    public int getContId() {
        return contId;
    }

    /**
     * This method sets the contact ID.
     *
     * @param contId the contact ID
     */
    public void setContId(int contId) {
        this.contId = contId;
    }

    /**
     * This method gets the contact name.
     */
    public String getContName() {
        return contName;
    }

    /**
     * This method sets the contact name.
     *
     * @param contName the contact name
     */
    public void setContName(String contName) {
        this.contName = contName;
    }

    /**
     * This method gets the contact email.
     */
    public String getContEmail() {
        return contEmail;
    }

    /**
     * This method sets the contact email.
     *
     * @param contEmail the contact email
     */
    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    /**
     * This method return the contact name.
     */
    @Override
    public String toString() {
        return (contName);
    }
}
