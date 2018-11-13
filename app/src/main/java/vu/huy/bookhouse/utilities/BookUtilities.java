package vu.huy.bookhouse.utilities;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Category;

public class BookUtilities {

    private static final String bookIDfield = "bookId";
    private static final String bookNamefield = "bookName";
    private static final String bookauthorfield = "autName";
    private static final String isVIPfield = "isVip";
    //private static final String datepublishfield = "datePublish";
    private static final String bookLinkfield = "bookLink";
    //private static final String publisherfield = "pubname";
    private static final String bookIMGfield = "bookImage";
    private static final String bookDescriptionfield = "bookDescription";

    private static final String catIDfield = "catId";
    private static final String catNamefield = "catName";
    private static final String catParentfield = "catParent";

    private static final String authorIDField = "autId";
    private static final String authorNameField = "autName";


    public List<Book> getBookByDate(int filterID, String search){
        List<Book> lstBook = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url;
        if(search.length() > 0){
            url = ConstainServer.BaseURL + ConstainServer.BookURL + "SearchBook/" + search;
        }else{
            if(filterID > 0){
                url = ConstainServer.BaseURL + ConstainServer.BookURL + "GetBookByCat/" + filterID;
            }else {
                if(filterID < 0){
                    //Chuyển id âm thành dương
                    filterID = filterID - (filterID *2);
                    url = ConstainServer.BaseURL + ConstainServer.BookURL + "GetBooksByAuthor/" + filterID;
                }else {
                    url = ConstainServer.BaseURL + ConstainServer.BookURL + "GetBookByDate";
                }
            }
        }
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
                if(!jsonObj.getString(bookauthorfield).contains("null")){
                    book.setBook_author(jsonObj.getString(bookauthorfield));
                }
                if(!jsonObj.getString(bookIMGfield).contains("null")){
                    book.setBook_img(jsonObj.getString(bookIMGfield));
                }
                if(!jsonObj.getString(bookLinkfield).contains("null")){
                    book.setBook_link(jsonObj.getString(bookLinkfield));
                }
                if(!jsonObj.getString(bookDescriptionfield).contains("null")){
                    book.setBook_description(jsonObj.getString(bookDescriptionfield));
                }
                if(jsonObj.has(isVIPfield)){
                    if(jsonObj.getInt(isVIPfield) == 0){
                        book.setVip(false);
                    }else{
                        book.setVip(true);
                    }
                    //book.setVip(jsonObj.getBoolean(isVIPfield));
                }

                lstBook.add(book);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }
    public List<Category> getParentCategory(){
        List<Category> lstCatParent =  null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CategoryURL + "GetAllCat";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstCatParent = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Category cat = new Category();
                if(jsonObj.has(catIDfield)){
                    cat.setCatId(jsonObj.getInt(catIDfield));
                }
                if(jsonObj.has(catNamefield)){
                    cat.setCatName(jsonObj.getString(catNamefield));
                }
                if(jsonObj.has(catParentfield)){
                    if(jsonObj.getInt(catParentfield) == 0){
                        lstCatParent.add(cat);
                    }
                }else{
                    lstCatParent.add(cat);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstCatParent;
    }
    public List<Category> getAllAuthor(){
        List<Category> lstAuthor =  null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.AuthorURL + "GetAllAuthor";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstAuthor = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Category author = new Category();
                if(jsonObj.has(authorIDField)){
                    //Đổi id sang số âm để tránh trùng với kiếm theo category
                    author.setCatId(jsonObj.getInt(authorIDField) - (jsonObj.getInt(authorIDField) * 2));
                }
                if(jsonObj.has(authorNameField)){
                    author.setCatName(jsonObj.getString(authorNameField));
                }
                    lstAuthor.add(author);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstAuthor;
    }
    public List<Category> getChildCategory(int catParentID){
        List<Category> lstCatChild =  null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.CategoryURL + "GetAllCat";
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            lstCatChild = new ArrayList<>();
            JSONArray arr = new JSONArray(respone);

            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);
                Category cat = new Category();
                if(jsonObj.has(catIDfield)){
                    cat.setCatId(jsonObj.getInt(catIDfield));
                }
                if(jsonObj.has(catNamefield)){
                    cat.setCatName(jsonObj.getString(catNamefield));
                }
                if(jsonObj.has(catParentfield)){
                    if(catParentID == jsonObj.getInt(catParentfield)){
                        lstCatChild.add(cat);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstCatChild;
    }

    public Book searchBookByID(String ID){
        Book lstBook = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = ConstainServer.BaseURL + ConstainServer.BookURL + "SearchBookByID/" + ID;
        String respone = "";

        try {
            URL urll = new URL(url);
            respone = ReadStream.readStream(urll.openStream());

            JSONArray arr = new JSONArray(respone);
            lstBook = new Book();
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject jsonObj = arr.getJSONObject(i);


                if(jsonObj.has(bookIDfield)){
                    lstBook.setBook_id(jsonObj.getInt(bookIDfield));
                }
                if(jsonObj.has(bookNamefield)){
                    lstBook.setBook_name(jsonObj.getString(bookNamefield));
                }
                if(jsonObj.has(bookauthorfield)){
                    lstBook.setBook_author(jsonObj.getString(bookauthorfield));
                }
                if(jsonObj.has(bookLinkfield)){
                    lstBook.setBook_link(jsonObj.getString(bookLinkfield));
                }
                if(jsonObj.has(bookIMGfield)){
                    lstBook.setBook_img(jsonObj.getString(bookIMGfield));
                }
                if(jsonObj.has(bookDescriptionfield)){
                    lstBook.setBook_description(jsonObj.getString(bookDescriptionfield));
                }
                if(jsonObj.has(isVIPfield)){
                    if(jsonObj.getInt(isVIPfield) == 0){
                        lstBook.setVip(false);
                    }else{
                        lstBook.setVip(true);
                    }
                    //book.setVip(jsonObj.getBoolean(isVIPfield));
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lstBook;
    }

}
