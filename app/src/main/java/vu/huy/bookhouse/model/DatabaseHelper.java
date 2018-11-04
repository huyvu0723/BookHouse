package vu.huy.bookhouse.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {



    static final String DATABASE_NAME = "BookCase";
    static final String TABLE_NAME = "Book";
    static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String DESCRIPTION = "description";
    private static final String MARK = "mark";
    private static final String LINK = "link";
    private static final String IMG = "img";

    //TinLM 30/10/2018
    //add IMG for Book
//    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
//            + " (id INTEGER PRIMARY KEY, "
//            + " name TEXT NOT NULL, "
//            + " author TEXT NOT NULL, "
//            + " description TEXT NOT NULL, "
//            + " mark INTEGER NOT NULL, "
//            + " link TEXT NOT NULL "
//            +" );";

    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY, "
            + " name TEXT NOT NULL, "
            + " author TEXT NOT NULL, "
            + " description TEXT NOT NULL, "
            + " mark INTEGER NOT NULL, "
            + " link TEXT NOT NULL, "
            + IMG + " TEXT NOT NULL "
            +" );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, book.getBook_id());
        values.put(NAME, book.getBook_name());
        values.put(AUTHOR, book.getBook_author());
        values.put(DESCRIPTION, book.getBook_description());
        values.put(MARK, book.getBook_mark());
        values.put(LINK, book.getBook_link());
        values.put(IMG, book.getBook_img());
        db.insert(TABLE_NAME, null,values);
        db.close();
    }
    public List<Book> getAllBook(){
        ArrayList<Book> listBook = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Book book = new Book();
                book.setBook_id(cursor.getInt(0));
                book.setBook_name(cursor.getString(1));
                book.setBook_author(cursor.getString(2));
                book.setBook_description(cursor.getString(3));
                book.setBook_mark(cursor.getInt(4));
                book.setBook_link(cursor.getString(5));
                listBook.add(book);
            }while(cursor.moveToNext());
        }
        db.close();
        return listBook;
    }
    public void updateMarkBook(int id, int mark){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mark", mark);
        db.update(TABLE_NAME, contentValues, ID + "=" + id, null);
        db.close();
    }
    public int getMarkBook(int id){
        int mark = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT mark FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            mark = cursor.getInt(0);
        }

        db.close();
        return mark;
    }
    public void deleteBook(int id){
        int mark = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            mark = cursor.getInt(0);
        }
        db.close();
    }
}
