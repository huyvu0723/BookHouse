package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.User;
import vu.huy.bookhouse.utilities.AccountUtilities;

// TinLM 30/10/2018 Update login
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INPUT = 2011;
    TextView errLogin;
    EditText txtUsername, txtPassword;
    AccountUtilities accountUtilities;
    long vipAvaiable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userIsLogn = getAccountLogin();
        accountUtilities = new AccountUtilities();
        if(userIsLogn != null) {
            loginSucessful(userIsLogn);

        }

        setContentView(R.layout.activity_main);
        errLogin = findViewById(R.id.errLogin);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void clickToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void clickToLogin(View view) {
//        String user = "";
//        String pass = "";
//        String name = "huy";
//        int balance = 500000;
//        String email = "...";
//        if((txtUsername.getText().toString().equals(user)) && (txtPassword.getText().toString().equals(pass))){
//            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//            Date currentTime = Calendar.getInstance().getTime();
//            Date dateVIP = new Date(2018, 11,20);
//
//            dateVIP.setYear(2018);
//            long diff = dateVIP.getTime() - currentTime.getTime();
//            long vipAvaiable = diff / (24 * 60 * 60 * 1000);
//            intent.putExtra("HeaderName", name);
//            intent.putExtra("Balance", balance);
//            intent.putExtra("DayVIP", vipAvaiable);
//            intent.putExtra("Email", email);
//            startActivity(intent);
//        }else{
//            errLogin.setText("Username or Password is invalid!");
//        }
        // TinLM 30/10/2018 Update login - login account with json

        String username = txtUsername.getText().toString();
        String passsword = txtPassword.getText().toString();

        boolean checkLogin = accountUtilities.checkLogin(username, passsword);

        if(checkLogin == true) {
            loginSucessful(username);
        } else {
            errLogin.setText("Username or Password is invalid!");
        }
    }

    private void loginSucessful(String usernameLogin) {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        Date currentTime = Calendar.getInstance().getTime();
        User user = accountUtilities.getUserDetail(usernameLogin);
        long diff = 0;
        saveAccountPrefer(user.getUsername(), user.getPasssword(), user.getUserID(), user.getBalance());

        diff = user.getVIPEndDate().getTime() - currentTime.getTime();
        if (diff <= 0) {
            vipAvaiable = 0;
        } else {
            vipAvaiable = diff / (24 * 60 * 60 * 1000);
        }
        intent.putExtra("HeaderName", user.getUsername());
        //intent.putExtra("DayVIP", vipAvaiable);
        intent.putExtra("FilterBook", 0);
        intent.putExtra("SearchBook", "");
        startActivity(intent);
    }
    //TinLM load save account is login
    private void saveAccountPrefer(String username, String password, String userID, float balance) {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstainServer.USERNAME, username);
        editor.putString(ConstainServer.PASSWORD, password);
        editor.putString(ConstainServer.ACCOUNTID, userID);
        editor.putFloat(ConstainServer.BALANCE, balance);
        editor.putLong(ConstainServer.VIPDATE, vipAvaiable);
        editor.apply();
    }
    //TinLM load get account is login
    private String getAccountLogin() {
        String username;
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        username = sharedPreferences.getString(ConstainServer.USERNAME,null);
        return username;
    }
}