package vu.huy.bookhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.DatabaseHelper;

public class BookCaseDetailActivity extends AppCompatActivity {

    TextView name, author, description;
    int mark, id;
    String link;

    DatabaseHelper bookCaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_case_detail);
        name = findViewById(R.id.txtNameBookInCase);
        author = findViewById(R.id.txtAuthorBookInCase);
        description = findViewById(R.id.txtDescriptionBookInCase);
        bookCaseManager = new DatabaseHelper(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
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
}
