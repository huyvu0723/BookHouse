package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vu.huy.bookhouse.R;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INPUT = 2011;
    TextView errLogin;
    EditText txtUsername, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String user = "";
        String pass = "";
        String name = "huy";
        int balance = 500000;
        String email = "...";
        if((txtUsername.getText().toString().equals(user)) && (txtPassword.getText().toString().equals(pass))){
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            Date currentTime = Calendar.getInstance().getTime();
            Date dateVIP = Calendar.getInstance().getTime();
            try {
                dateVIP = new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-21");
            }catch (Exception e){

            }


//            dateVIP.setYear(2018);
            long diff = dateVIP.getTime() - currentTime.getTime();
            long vipAvaiable = diff / (24 * 60 * 60 * 1000);
            intent.putExtra("HeaderName", name);
            intent.putExtra("Balance", balance);
            intent.putExtra("DayVIP", vipAvaiable);
            intent.putExtra("Email", email);
            startActivity(intent);
        }else{
            errLogin.setText("Username or Password is invalid!");
        }
    }
}
