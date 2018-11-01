package vu.huy.bookhouse.utilities;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.Book;

public class BookUtilities {

    private static final String bookIDfield = "bookid";
    private static final String bookNamefield = "bookname";
    private static final String bookauthorfield = "autname";
    private static final String isVIPfield = "isvip";
    private static final String datepublishfield = "datepublish";
    private static final String bookLinkfield = "booklink";
    private static final String publisherfield = "pubname";
    private static final String bookIMGfield = "bookimage";
    private static final String bookDescriptionfield = "bookdescription";

    public List<Book> getBookByDate(){
        List<Book> lstBook = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookURL + "GetBookByDate";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstBook = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);

                Book book = new Book();
                if(jsonObj.has(bookIDfield)){
                    book.setBook_id(jsonObj.getInt(bookIDfield));
                }
                if(jsonObj.has(bookNamefield)){
                    book.setBook_name(jsonObj.getString(bookNamefield));
                }
                if(jsonObj.has(bookauthorfield)){
                    book.setBook_author(jsonObj.getString(bookauthorfield));
                }
                if(jsonObj.has(bookIMGfield)){
                    book.setBook_img(jsonObj.getString(bookIMGfield));
                }
                lstBook.add(book);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }

    public Book getBookByID(){
        Book book = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookURL + "SearchBookByID";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());


            JSONObject jsonObj = new JSONObject(respone);


               book = new Book();
                if(jsonObj.has(bookIDfield)){
                    book.setBook_id(jsonObj.getInt(bookIDfield));
                }
                if(jsonObj.has(bookNamefield)){
                    book.setBook_name(jsonObj.getString(bookNamefield));
                }
                if(jsonObj.has(bookauthorfield)){
                    book.setBook_author(jsonObj.getString(bookauthorfield));
                }
                if(jsonObj.has(bookIMGfield)){
                    book.setBook_img(jsonObj.getString(bookIMGfield));
                }
                if(jsonObj.has(bookDescriptionfield)){
                    book.setBook_description(jsonObj.getString(bookDescriptionfield));
                }
                if(jsonObj.has(bookLinkfield)){
                    book.setBook_link(jsonObj.getString(bookLinkfield));
                }


        }catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }
}
