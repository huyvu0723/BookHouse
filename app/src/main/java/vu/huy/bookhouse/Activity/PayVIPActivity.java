package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vu.huy.bookhouse.R;

public class PayVIPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_vip);
    }

    public void clickToAddMoney(View view) {
        Intent intent = new Intent(this, AddMoneyActivity.class);
        startActivity(intent);
    }
}
