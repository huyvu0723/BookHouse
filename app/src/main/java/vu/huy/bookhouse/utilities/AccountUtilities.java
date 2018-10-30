package vu.huy.bookhouse.utilities;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import vu.huy.bookhouse.model.User;

import static vu.huy.bookhouse.utilities.ReadStream.readStream;

//TinLM 30/10/2018 Create utilities for Account

public class AccountUtilities {
    private static final String BaseURL = "http://192.168.100.5:8080/RestAPI/"; //cmd ipconfig
    private static final String UserURL = "webresources/User/";

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
        String url = BaseURL + UserURL + "Login?username=" + username + "&password=" + password;
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

    // TinLM 30/10/2018 Create getUserDetail
    public User getUserDetail(String username){
        User result = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = BaseURL + UserURL + "GetUser?username=" + username;
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
            e.printStackTrace();
        }

        return result;
    }
}
