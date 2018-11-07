package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Bookcase;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.utilities.BookcaseUtilities;

public class ReadPDFActivity extends AppCompatActivity implements OnPageChangeListener {

    TextView pagePDF;
    int mark, id;
    String link;
    DatabaseHelper bookCaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);
        pagePDF = findViewById(R.id.page_pdf);
        pagePDF.bringToFront();
        PDFView pdfView = findViewById(R.id.pdfView);
        bookCaseManager = new DatabaseHelper(this);
//        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/BOOKHOUSE PDF", "Book_01.pdf"
//        );
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        link = extras.getString("Link");
        id = extras.getInt("Id");
        mark = extras.getInt("Mark");
        File pdfFile = new File(link);

            pdfView.fromFile(pdfFile)
                    .enableAntialiasing(true)
                    .enableSwipe(true)
                    .swipeHorizontal(true)
                    .pageFling(true)
                    .onPageChange(this)
                    .defaultPage(mark)//tính cả số 0 nên sẽ ra page 6
                    .load();


    }

    @Override
    public void onPageChanged(int page, int pageCount) {
         pagePDF.setText("Page " + (page + 1) + "/ " + pageCount);
         bookCaseManager.updateMarkBook(id, page);
        mark = page;
        //do what you want with the pageNumber
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BookcaseUtilities bookcaseUtilities = new BookcaseUtilities();
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        int accId  = Integer.parseInt(sharedPreferences.getString(ConstainServer.ACCOUNTID, "0"));
        bookcaseUtilities.updateBookMark(accId, id, mark);

    }
}
