package model;

public class Customer {
    private int custId;
    private String custName;
    private String custAddress;
    private String custPost;
    private String custPhone;
    private int custDivId;

    public Customer(int custId, String custName, String custAddress, String custPost, String custPhone, int custDivId) {
        this.custId = custId;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPost = custPost;
        this.custPhone = custPhone;
        this.custDivId = custDivId;
    }

    public Customer(String custName, String custAddress, String custPost, String custPhone, int custDivId) {
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPost = custPost;
        this.custPhone = custPhone;
        this.custDivId = custDivId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName(){
        return custName;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }

    public String getCustAddress(){
        return custAddress;
    }

    public void getCustAddress(String custAddress){
        this.custAddress = custAddress;
    }

    public String getCustPost() {
        return custPost;
    }

    public void setCustPost(String custPost) {
        this.custPost = custPost;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void set(String custNum) {
        this.custPhone = custNum;
    }

    public int getCustDivId() {
        return custDivId;
    }

    public void setCustDivId(int custDivId){
        this.custDivId = custDivId;
    }

    @Override
    public String toString(){
        return (custName);
    }
}
