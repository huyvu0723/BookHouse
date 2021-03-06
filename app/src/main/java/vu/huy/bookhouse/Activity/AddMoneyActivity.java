package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.User;
import vu.huy.bookhouse.utilities.AccountUtilities;

public class AddMoneyActivity extends AppCompatActivity {

    AccountUtilities utilities;
    Spinner spinTypeCard, spinValueCard;
    EditText serial,code;
    TextView errCharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        utilities = new AccountUtilities();
        spinTypeCard = findViewById(R.id.spinTypeCard);
        spinValueCard = findViewById(R.id.spinValueCard);
        serial = findViewById(R.id.txtChargeSerial);
        code = findViewById(R.id.txtChargeCode);
        errCharge = findViewById(R.id.errAddMoney);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String[] typeCard = new String[]{"MobiPhone", "Viettel", "Vinaphone"};
        String[] valueCard = new String[]{"10000", "20000", "50000", "100000", "200000", "500000"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeCard);
        spinTypeCard.setAdapter(adapter);
        int userID = extras.getInt("UserID");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, valueCard);
        spinValueCard.setAdapter(adapter);

    }

    public void clickToAcceptAddMoney(View view) {
        if(code.getText().toString().length() > 0 && serial.getText().toString().length() >0) {
            SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
            String userId = sharedPreferences.getString(ConstainServer.ACCOUNTID, "0");
            int accId = Integer.parseInt(userId);
            String value = spinValueCard.getSelectedItem().toString();
            double amount = Double.parseDouble(value);
            utilities.chargeMoney(accId, amount);
            float balance = sharedPreferences.getFloat(ConstainServer.BALANCE, 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat(ConstainServer.BALANCE, (balance + (float) amount));


            //

            // Dashboard :3
            AccountUtilities accountUtilities = new AccountUtilities();
            Intent intent = new Intent(AddMoneyActivity.this, DashboardActivity.class);
            Date currentTime = Calendar.getInstance().getTime();
            String usernameLogin =sharedPreferences.getString(ConstainServer.USERNAME, null);
            User user = accountUtilities.getUserDetail(usernameLogin);

            long diff = 0;
            long vipAvaiable;

            diff = user.getVIPEndDate().getTime() - currentTime.getTime();
            if (diff <= 0) {
                vipAvaiable = 0;
            } else {
                vipAvaiable = diff / (24 * 60 * 60 * 1000);
                vipAvaiable += 1;
            }
            editor.putLong(ConstainServer.VIPDATE, vipAvaiable);
            editor.apply();
            intent.putExtra("HeaderName", user.getUsername());
            intent.putExtra("DayVIP", vipAvaiable);
            intent.putExtra("FilterBook", 0);
            intent.putExtra("SearchBook", "");
            intent.putExtra("GetBalance", 1);
            intent.putExtra("AddMoney", 1);
            startActivity(intent);




            //

            finish();
        }else{
            errCharge.setText("Code or serial is invalid. Please try agains");
        }
    }
}
