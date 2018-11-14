package vu.huy.bookhouse.utilities;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Bookcase;

import static vu.huy.bookhouse.utilities.ReadStream.getDelConnection;
import static vu.huy.bookhouse.utilities.ReadStream.getPostConnection;
import static vu.huy.bookhouse.utilities.ReadStream.getPutConnection;
import static vu.huy.bookhouse.utilities.ReadStream.readStream;

// TinLM 2/11/2018 Create
public class BookcaseUtilities {

    private static final String bookIdfield = "bookId";
    //private static final String accIdfield = "accId";
    private static final String bookMarkfield = "bookMark";
    private static final String autNamefield = "autName";
    private static final String bookImagefield = "bookImage";
    private static final String bookLinkfield = "bookLink";
    private static final String bookNamefield = "name";
    private static final String ratefield = "rate";
    private static final String countfield = "countDownload";
    private static final String bookDescription = "bookDescription";

    public boolean postBookcase(String accId, int bookId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookcaseURL
                + "PostBookcase/" + accId + "/" + bookId;
        String respone = "";
        boolean result = false;
        try {
            URL urll = new URL(url);
            //respone = readStream(urll.openStream());
            HttpURLConnection client = getPostConnection(urll);
            respone = readStream(client.getInputStream());
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

        url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "GetBookCaseByUserId/" + accId;

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
                if(!jsonObj.getString(bookImagefield).contains("null")) {
                    bookcase.setBookImage(jsonObj.getString(bookImagefield));
                }
                if(!jsonObj.getString(bookLinkfield).contains("null")) {
                    bookcase.setBookLink(jsonObj.getString(bookLinkfield));
                }
                if(!jsonObj.getString(bookDescription).contains("null")) {
                    bookcase.setBookDescription(jsonObj.getString(bookDescription));
                }
                if(!jsonObj.getString(bookIdfield).contains("null")) {
                    bookcase.setBookId(Integer.parseInt(jsonObj.getString(bookIdfield)));
                }
                if(!jsonObj.getString(bookMarkfield).contains("null")) {
                    bookcase.setBookMark(Integer.parseInt(jsonObj.getString(bookMarkfield)));
                }
                lstBook.add(bookcase);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }

    public boolean checkBookcase(String accId, int bookId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookcaseURL
                + "CheckBookcase/" + accId + "/" + bookId;
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
    public void deleteBook(int accId, int bookId){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;

        url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "DeleteBookInBookcase/" + accId+"/" + bookId;

        String respone = "";

        try {
            URL urll = new URL(url);
            HttpURLConnection client = getDelConnection(urll);
            respone = readStream(client.getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean rateBook(String userID, String bookID, int rate){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "rateBook/" + userID +"/" + bookID +"/" + rate;
        String respone = "";

        try {
            URL urll = new URL(url);
            HttpURLConnection client = getPutConnection(urll);
            respone = readStream(client.getInputStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    public boolean insertRateBook(String userID, String bookID, int rate){
        boolean result = false;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.UserURL + "insertRateBook/" + userID +"/" + bookID +"/" + rate;
        String respone = "";

        try {
            URL urll = new URL(url);
            //respone = readStream(urll.openStream());
            HttpURLConnection client = getPostConnection(urll);
            respone = readStream(client.getInputStream());
            if(respone.contains("true")){
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public Bookcase getBookcaseRate(int bookId){
        Bookcase lstBook = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;

        url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "GetBookcaseRate/" + bookId;

        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            JSONObject jsonObj = new JSONObject(respone);
//            JSONArray arr = new JSONArray(respone);
//
//
//                JSONObject jsonObj = arr.getJSONObject(0);

            lstBook = new Bookcase();
                if(jsonObj.has(ratefield)){
                    lstBook.setRate(jsonObj.getDouble(ratefield));
                }
                if(jsonObj.has(countfield)){
                    lstBook.setCountDownload(jsonObj.getInt(countfield));
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }

    public void updateBookMark(int accId, int bookId, int mark){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;

            url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "UpdateBookMar/" + mark +  "/"+ accId+"/" + bookId;

        String respone = "";

        try {
            URL urll = new URL(url);
            HttpURLConnection client = getPutConnection(urll);
            respone = ReadStream.readStream(client.getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getMark(int accId, int bookId) {
        int mark = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;

        url = ConstainServer.BaseURL + ConstainServer.BookcaseURL + "GetMark/"+ accId+"/" + bookId;

        String respone = "";
        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());
            JSONObject jsonObj = new JSONObject(respone);
//            JSONArray arr = new JSONArray(respone);
//            JSONObject jsonObj = arr.getJSONObject(0);
            if(jsonObj.has(bookMarkfield)) {
                mark = jsonObj.getInt(bookMarkfield);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return mark;
    }

}
