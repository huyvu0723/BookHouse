package vu.huy.bookhouse.model;


import java.util.Date;

public class User {
    private String userID = "";
    private String username = "";
    private String passsword = "";
    private String fullname = "";
    private float balance = 0;
    private Date VIPEndDate;

    public User() {
    }

    public User(String userID, String username, String passsword, String fullname, float balance, Date VIPEndDate) {
        this.userID = userID;
        this.username = username;
        this.passsword = passsword;
        this.fullname = fullname;
        this.balance = balance;
        this.VIPEndDate = VIPEndDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getVIPEndDate() {
        return VIPEndDate;
    }

    public void setVIPEndDate(Date VIPEndDate) {
        this.VIPEndDate = VIPEndDate;
    }
}
