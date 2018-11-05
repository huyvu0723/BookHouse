package vu.huy.bookhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vu.huy.bookhouse.Activity.AddMoneyActivity;
import vu.huy.bookhouse.Activity.BookDetailActivity;
import vu.huy.bookhouse.Activity.DashboardActivity;
import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.PackVIP;
import vu.huy.bookhouse.model.User;
import vu.huy.bookhouse.utilities.AccountUtilities;

public class PackVipRecycleViewAdapter extends RecyclerView.Adapter<PackVipRecycleViewAdapter.MyViewHolder> {

    Context mContext;
    List<PackVIP> mData;

    public PackVipRecycleViewAdapter(Context mContext, List<PackVIP> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.pack_vip_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvPackCost.setText(mData.get(position).getPackCost() + " VND");
        myViewHolder.tvPackDate.setText(mData.get(position).getPackDay() + "ngày");

        myViewHolder.cardPackVipItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
                float accBalance = sharedPreferences.getFloat(ConstainServer.BALANCE, 0);
                String accId = sharedPreferences.getString(ConstainServer.ACCOUNTID, "0");
                if( accBalance < (float) mData.get(position).getPackCost()) {
                    Toast.makeText(mContext, "Bạn không đủ tiền mua vip", Toast.LENGTH_SHORT).show();
                } else {
                    AccountUtilities accountUtilities = new AccountUtilities();
                    String acv = String.valueOf(mData.get(position).getPackId());
                    boolean resultPay = accountUtilities.buyPack(accId,
                            String.valueOf(mData.get(position).getPackId()),
                            mData.get(position).getPackDay(),
                            mData.get(position).getPackCost());

                    Intent intent = new Intent(mContext, DashboardActivity.class);

                    Date currentTime = Calendar.getInstance().getTime();
                    String usernameLogin =sharedPreferences.getString(ConstainServer.USERNAME, null);
                    User user = accountUtilities.getUserDetail(usernameLogin);
                    // setting balance
                    float balance = sharedPreferences.getFloat(ConstainServer.BALANCE, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat(ConstainServer.BALANCE, (balance - (float) mData.get(position).getPackCost()));
                    editor.apply();

                    long diff = 0;
                    long vipAvaiable;

                    diff = user.getVIPEndDate().getTime() - currentTime.getTime();
                    if (diff <= 0) {
                        vipAvaiable = 0;
                    } else {
                        vipAvaiable = diff / (24 * 60 * 60 * 1000);
                    }
                    intent.putExtra("HeaderName", user.getUsername());
                    intent.putExtra("DayVIP", vipAvaiable);
                    intent.putExtra("FilterBook", 0);
                    intent.putExtra("SearchBook", "");
                    intent.putExtra("GetBalance", 1);
                    intent.putExtra("AddMoney", 1);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPackDate,tvPackCost;

        CardView cardPackVipItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPackDate = itemView.findViewById(R.id.tvPackDate);
            tvPackCost = itemView.findViewById(R.id.tvPackCost);
            cardPackVipItem = itemView.findViewById(R.id.cardPackVipItem);

        }
    }

}
