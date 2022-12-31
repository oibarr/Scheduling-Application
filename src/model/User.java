package model;

/**
 * This class creates the User object.
 * It contains the constructors, getters, setters, and toString methods.
 */
public class User {
    private int userId;
    private String userName;
    private String password;

    /**
     * This method is the constructor for the User object.
     *
     * @param userId   the user ID
     * @param userName the username
     * @param password the password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * This method gets the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method sets the user ID.
     *
     * @[param userId the user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * This method gets the username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets the username.
     *
     * @param userName the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method gets the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method returns the username.
     */
    @Override
    public String toString() {
        return (userName);
    }
}
