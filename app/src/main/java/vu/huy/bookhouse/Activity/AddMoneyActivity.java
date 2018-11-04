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

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
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
            finish();
        }else{
            errCharge.setText("Code or serial is invalid. Please try agains");
        }
    }
}
