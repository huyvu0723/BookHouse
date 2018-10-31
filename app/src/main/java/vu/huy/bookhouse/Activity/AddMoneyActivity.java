package vu.huy.bookhouse.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import vu.huy.bookhouse.R;

public class AddMoneyActivity extends AppCompatActivity {

    Spinner spinTypeCard, spinValueCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        spinTypeCard = findViewById(R.id.spinTypeCard);
        spinValueCard = findViewById(R.id.spinValueCard);
        String[] typeCard = new String[]{"MobiPhone", "Viettel", "Vinaphone"};
        String[] valueCard = new String[]{"10.000", "20.000", "50.000", "100.000", "200.000", "500.000"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeCard);
        spinTypeCard.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, valueCard);
        spinValueCard.setAdapter(adapter);

    }
}
