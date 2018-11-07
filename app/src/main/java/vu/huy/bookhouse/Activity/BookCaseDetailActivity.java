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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.Fragment.LibraryFragment;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.model.User;
import vu.huy.bookhouse.utilities.AccountUtilities;
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

    // TinLM setting rating
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_case_detail);
        utilities = new BookcaseUtilities();
        setData();
        setEvents();
    }

    private void setEvents() {
        float asdf = ratingBar.getRating();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                BookcaseUtilities bookcaseUtilities = new BookcaseUtilities();
                SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                String useId = sharedPreferences.getString(ConstainServer.ACCOUNTID, null);
                boolean resultRate = bookcaseUtilities.rateBook(useId, String.valueOf(id), (int) rating);

                if( !resultRate ) {
                    bookcaseUtilities.insertRateBook(useId, String.valueOf(id), (int) rating);

                }
                Toast.makeText(getApplicationContext(), "Đánh giá thành công", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setData() {
        name = findViewById(R.id.txtNameBookInCase);
        author = findViewById(R.id.txtAuthorBookInCase);
        description = findViewById(R.id.txtDescriptionBookInCase);
        imgBookcaseId = findViewById(R.id.imgBookcaseId);

        // TinLM setting rating
        ratingBar = findViewById(R.id.ratingBar);


        bookCaseManager = new DatabaseHelper(this);
        intent = getIntent();
        extras = intent.getExtras();
        name.setText(extras.getString("Name"));
        author.setText(extras.getString("Author"));
        description.setText(extras.getString("Description"));
        link = extras.getString("Link");
        id = extras.getInt("Id");
        //TinLM 7/11/2018
        //mark = extras.getInt("Mark");

    }

    public void clickToOpenPDF(View view) {

        Intent intent = new Intent(this, ReadPDFActivity.class);
        //mark = bookCaseManager.getMarkBook(id);
        // TinLM
        BookcaseUtilities bookcaseUtilities = new BookcaseUtilities();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        int accId = Integer.parseInt(sharedPreferences.getString(ConstainServer.ACCOUNTID, "0"));
        mark = bookcaseUtilities.getMark(accId, id);

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

//        Fragment bookcaseFrag = new LibraryFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, bookcaseFrag);
//        transaction.commit();
//        finish();

        // Dashboard :3
        AccountUtilities accountUtilities = new AccountUtilities();
        Intent intent = new Intent(BookCaseDetailActivity.this, DashboardActivity.class);
        Date currentTime = Calendar.getInstance().getTime();
        String usernameLogin =sharedPreferences.getString(ConstainServer.USERNAME, null);
        User user = accountUtilities.getUserDetail(usernameLogin);

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
        intent.putExtra("IsDelete", 1);
        startActivity(intent);

    }
}
