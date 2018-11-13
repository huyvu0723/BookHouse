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


    private static final String userIDfield = "accId";
    private static final String userNamefield = "accUsername";
    private static final String userPasswordfield = "accPassword";
    private static final String userFullnamefield = "accFullname";
    private static final String userVIPDatefield = "accDateEndVip";
    private static final String userBalancefield = "accWallet";

    // TinLM 30/10/2018 Create checkLogin
    public boolean checkLogin(String username, String password){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "Login/" + username + "/" + password;
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
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "GetUser/" + username;
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
            float balance = (float) jsonObj.getDouble(userBalancefield);
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
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "AddUser/" + username + "/" + password + "/" + fullname;
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
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "chargeBalance/" + accId + "/" + amount;
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

    public boolean buyPack(String userID, String packID, int day, double amount){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "BuyPack/" + userID + "/" + packID + "/" + day + "/" + amount;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = readStream(urll.openStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
