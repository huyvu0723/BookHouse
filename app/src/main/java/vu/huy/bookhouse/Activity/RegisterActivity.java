package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import vu.huy.bookhouse.R;

public class RegisterActivity extends AppCompatActivity {

    TextView notiRegister;
    EditText txtName, txtUsername, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtName = findViewById(R.id.txtRegisterName);
        txtUsername = findViewById(R.id.txtRegisterUsername);
        txtPassword = findViewById(R.id.txtRegisterPassword);
        notiRegister = findViewById(R.id.notiRegister);
    }

    public void clickToBackSignIn(View view) {
        finish();
    }

    public void clickToCreateAccount(View view) {
        boolean checked = true;
        if(TextUtils.isEmpty(txtName.getText())){
            txtName.setError("Name is required");
            checked =false;
        }
        if(TextUtils.isEmpty(txtUsername.getText())){
            txtUsername.setError("Username is required");
            checked =false;
        }
        if(TextUtils.isEmpty(txtPassword.getText())){
            txtPassword.setError("Password is required");
            checked =false;
        }

        String user = "ab";

        if(checked){
            if(txtUsername.getText().toString().equals(user)){
                notiRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                notiRegister.setText("Account is created successfully");
            }else{
                notiRegister.setTextColor(getResources().getColor(R.color.colorDanger));
                notiRegister.setText("Username or Password is invalid!");
            }
        }

    }
}
