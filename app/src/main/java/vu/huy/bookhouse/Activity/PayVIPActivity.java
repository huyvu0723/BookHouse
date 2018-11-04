package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.PackVIP;
import vu.huy.bookhouse.utilities.PackVipUtilities;

public class PayVIPActivity extends AppCompatActivity {

    List<PackVIP> listPack;
    ListView listViewPack;
    PackVipUtilities utilities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_vip);
        utilities = new PackVipUtilities();
        listViewPack = findViewById(R.id.list_packVip);
        listPack = utilities.getAllPackVip();
        ArrayAdapter<PackVIP> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listPack);
        listViewPack.setAdapter(adapter);
    }

    public void clickToAddMoney(View view) {
        Intent oldIntent = getIntent();
        Bundle extras = oldIntent.getExtras();
        String userId = extras.getString("UserID");
        Intent intent = new Intent(this, AddMoneyActivity.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
}
