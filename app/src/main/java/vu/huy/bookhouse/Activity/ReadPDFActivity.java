package vu.huy.bookhouse.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

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
        pdfView.fromAsset("pdf.pdf")
                .enableAntialiasing(true)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .pageFling(true)
                .onPageChange(this)
                .load();
        totalPage = pdfView.getCurrentPage();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
         pagePDF.setText("" + (page + 1) + " / "+ totalPage);
        //do what you want with the pageNumber
    }

}
