package vu.huy.bookhouse.utilities;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.Category;
import vu.huy.bookhouse.model.PackVIP;

public class PackVipUtilities {

    private static final String packIDfield = "pvid";
    private static final String packDayfield = "bookdate";
    private static final String packCostfield = "pvcost";

    public List<PackVIP> getAllPackVip(){
        List<PackVIP> lstPack =  null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "GetPackVip";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstPack = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                PackVIP pack = new PackVIP();
                if(jsonObj.has(packIDfield)){
                    pack.setPackId(jsonObj.getInt(packIDfield));
                }
                if(jsonObj.has(packDayfield)){
//                    JSONObject big = jsonObj.getJSONObject(packDayfield);
                    pack.setPackDay(jsonObj.getInt(packDayfield));
                }
                if(jsonObj.has(packCostfield)){
                    pack.setPackCost(jsonObj.getDouble(packCostfield));
                }
                lstPack.add(pack);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstPack;
    }
}
