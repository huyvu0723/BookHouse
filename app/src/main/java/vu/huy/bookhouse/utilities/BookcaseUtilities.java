package vu.huy.bookhouse.utilities;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Bookcase;

import static vu.huy.bookhouse.utilities.ReadStream.readStream;

// TinLM 2/11/2018 Create
public class BookcaseUtilities {

    private static final String bookIdfield = "bookid";
    private static final String accIdfield = "accid";
    private static final String bookMarkfield = "bookmard";
    private static final String autNamefield = "autname";
    private static final String bookImagefield = "bookimage";
    private static final String bookLinkfield = "booklink";
    private static final String bookNamefield = "bookname";
    private static final String ratefield = "rate";
    private static final String bookDescription = "bookdescription";

    public boolean postBookcase(String accId, String bookId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookcaseURL
                + "PostBookCase?accId=" + accId + "&bookId=" + bookId;
        String respone = "";
        boolean result = false;
        try {
            URL urll = new URL(url);
            respone = readStream(urll.openStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            Log.e("ErrorBookcase", e.getMessage());
        }
        return result;
    }

    public List<Bookcase> getBookcaseByDId(int accId){
        List<Bookcase> lstBook = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;

        url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "GetBookcaseById?id=" + accId;

        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstBook = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);

                Bookcase bookcase = new Bookcase();
                if(jsonObj.has(bookImagefield)){
                    bookcase.setAutName(jsonObj.getString(autNamefield));
                }
                if(jsonObj.has(bookNamefield)) {
                    bookcase.setName(jsonObj.getString(bookNamefield));
                }
                if(jsonObj.has(bookImagefield)) {
                    bookcase.setBookImage(jsonObj.getString(bookImagefield));
                }
                if(jsonObj.has(bookLinkfield)) {
                    bookcase.setBookLink(jsonObj.getString(bookLinkfield));
                }
                if(jsonObj.has(bookDescription)) {
                    bookcase.setBookDescription(jsonObj.getString(bookDescription));
                }

                lstBook.add(bookcase);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }


}
