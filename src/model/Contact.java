package model;

public class Contact {
    private int contId;
    private String contName;
    private String contEmail;

    public Contact(int contId, String contName, String contEmail){
        this.contId = contId;
        this.contName = contName;
        this.contEmail = contEmail;
    }


    public Contact(String contName){
        this.contName = contName;
    }

    public int getContId(){
        return contId;
    }

    public void setContId(int contId){
        this.contId = contId;
    }

    public String getContName(){
        return contName;
    }

    public void setContName(String contName){
        this.contName = contName;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    @Override
    public String toString(){
        return (contName);
    }
}
