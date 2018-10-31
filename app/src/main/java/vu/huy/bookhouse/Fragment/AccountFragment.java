package vu.huy.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vu.huy.bookhouse.R;

public class AccountFragment extends Fragment {

    TextView headerName, balance, dayVIP;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Bundle extras = getArguments();
        headerName = view.findViewById(R.id.txtHeaderName);
        balance = view.findViewById(R.id.txtInfoBalance);
        dayVIP = view.findViewById(R.id.txtInfoDayVIP);
            headerName.setText(extras.getString("HeaderName"));
            balance.setText(extras.getInt("Balance") + "");
            dayVIP.setText(extras.getLong("DayVIP") + "");
        return view;
    }
}
