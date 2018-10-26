package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import vu.huy.bookhouse.R;

public class MainActivity extends AppCompatActivity {

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
        String user = "a";
        String pass = "a";

        if((txtUsername.getText().toString().equals(user)) && (txtPassword.getText().toString().equals(pass))){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }else{
            errLogin.setText("Username or Password is invalid!");
        }
    }
}
