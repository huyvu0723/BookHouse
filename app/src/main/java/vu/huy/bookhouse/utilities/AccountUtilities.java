package vu.huy.bookhouse.utilities;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.User;

import static vu.huy.bookhouse.utilities.ReadStream.readStream;

//TinLM 30/10/2018 Create utilities for Account

public class AccountUtilities {


    private static final String userIDfield = "accid";
    private static final String userNamefield = "accusername";
    private static final String userPasswordfield = "accpassword";
    private static final String userFullnamefield = "accfullname";
    private static final String userVIPDatefield = "accdateendvip";
    private static final String userBalancefield = "accwallet";

    // TinLM 30/10/2018 Create checkLogin
    public boolean checkLogin(String username, String password){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "Login?username=" + username + "&password=" + password;
        String respone = "";

        try {
            URL urll = new URL(url);
                respone = readStream(urll.openStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            Log.e("ErrorLogin", e.getMessage());
        }
        return result;
    }

    // TinLM 30/10/2018 Create getUserDetail
    public User getUserDetail(String username){
        User result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "GetUser?username=" + username;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = readStream(urll.openStream());

            JSONArray arr = new JSONArray(respone);
            JSONObject jsonObj = arr.getJSONObject(0);

            String userID = jsonObj.getString(userIDfield);
            String name = jsonObj.getString(userNamefield);
            String pass = jsonObj.getString(userPasswordfield);
            String fullname = jsonObj.getString(userFullnamefield);
            double balance = jsonObj.getDouble(userBalancefield);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObj.getString(userVIPDatefield));

            result = new User(userID, name, pass, fullname, balance, endDate);
        }catch (Exception e){
            Log.e("ErrorGetUser", e.getMessage());
        }

        return result;
    }

    //TinLM 30/10/2018 Create account

    public boolean addUser(String username, String password, String fullname){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "AddUser?username=" + username + "&password=" + password + "&fullname=" + fullname;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = readStream(urll.openStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            Log.e("ErrorAddUser", e.getMessage());
            return result;
        }
        return result;
    }
    public boolean chargeMoney(int accId, double amount){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "Charge?ID=" + accId + "&amount=" + amount;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = readStream(urll.openStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            Log.e("ErrorAddUser", e.getMessage());
            return result;
        }
        return result;
    }

}
