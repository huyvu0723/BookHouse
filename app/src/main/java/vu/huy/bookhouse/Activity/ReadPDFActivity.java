package vu.huy.bookhouse.Activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import vu.huy.bookhouse.R;

public class ReadPDFActivity extends AppCompatActivity implements OnPageChangeListener {

    TextView pagePDF;
    int totalPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);
        pagePDF = findViewById(R.id.page_pdf);
        pagePDF.bringToFront();
        PDFView pdfView = findViewById(R.id.pdfView);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/BOOKHOUSE PDF", "Book_01.pdf"
        );

        pdfView.fromFile(pdfFile)
                .enableAntialiasing(true)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .pageFling(true)
                .onPageChange(this)
                .defaultPage(5)//tính cả số 0 nên sẽ ra page 6
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
         pagePDF.setText("Page " + (page + 1) + "/ " + pageCount);
        //do what you want with the pageNumber
    }

}
