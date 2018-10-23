package vu.huy.bookhouse.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import vu.huy.bookhouse.R;

public class BookDetailActivity extends AppCompatActivity {

    private String filepath = "BookCase";
    private String filename = "pdf.pdf";
    File myInternalFile, directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //Tạo internal folder
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);

    }

    public void clickToGetPDF(View view) {
        myInternalFile = new File(directory, filename);
        try {
            FileOutputStream fos = new FileOutputStream(myInternalFile);
            fos.write(123);
            fos.close();
        }catch (Exception e){

        }

    }
}
