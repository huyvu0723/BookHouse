package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.Fragment.LibraryFragment;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.utilities.BookUtilities;
import vu.huy.bookhouse.utilities.BookcaseUtilities;

public class BookCaseDetailActivity extends AppCompatActivity {

    TextView name, author, description;
    int mark, id;
    String link;
    ImageView imgBookcaseId;
    Intent intent;
    Bundle extras;
    DatabaseHelper bookCaseManager;
    BookcaseUtilities utilities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_case_detail);
        utilities = new BookcaseUtilities();
        setData();
    }

    private void setData() {
        name = findViewById(R.id.txtNameBookInCase);
        author = findViewById(R.id.txtAuthorBookInCase);
        description = findViewById(R.id.txtDescriptionBookInCase);
        imgBookcaseId = findViewById(R.id.imgBookcaseId);
        bookCaseManager = new DatabaseHelper(this);
        intent = getIntent();
        extras = intent.getExtras();
        name.setText(extras.getString("Name"));
        author.setText(extras.getString("Author"));
        description.setText(extras.getString("Description"));
        link = extras.getString("Link");
        id = extras.getInt("Id");

    }

    public void clickToOpenPDF(View view) {

        Intent intent = new Intent(this, ReadPDFActivity.class);
        mark = bookCaseManager.getMarkBook(id);
        intent.putExtra("Mark", mark);
        intent.putExtra("Link", link);
        intent.putExtra("Id", id);
        startActivity(intent);
    }


    public void clickToDeleteBook(View view) {
        //Delete in sqlite
        bookCaseManager.deleteBook(id);
        //Delete in sdcard
        File file = new File(link);
        file.delete();
        //Delete in server
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        String userId =sharedPreferences.getString(ConstainServer.ACCOUNTID, "0");
        int accId = Integer.parseInt(userId);
        utilities.deleteBook(accId, id);
        Fragment bookcaseFrag = new LibraryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, bookcaseFrag);
        transaction.commit();

        finish();

    }
}
