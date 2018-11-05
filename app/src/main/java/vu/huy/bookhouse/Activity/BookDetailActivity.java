package vu.huy.bookhouse.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

import vu.huy.bookhouse.Constant.ConstainServer;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Bookcase;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.model.User;
import vu.huy.bookhouse.utilities.AccountUtilities;
import vu.huy.bookhouse.utilities.BookcaseUtilities;

public class BookDetailActivity extends AppCompatActivity {

    private static final int  MEGABYTE = 1024 * 1024;
    private String filepath = "BookCase";
    File myInternalFile, directory;
    TextView nameBook, authorBook, descriptionBook, viewBook;
    private int STORAGE_PERMISSION_CODE = 1;
    Intent intent;
    private ProgressDialog mProgressDialog;
    private TextView tvDownloadBook;

    DatabaseHelper bookCaseManager;

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //Tạo internal folder
        ContextWrapper contextWrapper = new ContextWrapper(
                getApplicationContext());
        directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);

        nameBook = findViewById(R.id.txtNameBook);
        authorBook = findViewById(R.id.txtAuthorBook);
        descriptionBook = findViewById(R.id.txtDescriptionBook);
        viewBook = findViewById(R.id.txtViewBook);
        intent = getIntent();
        String name = getIntent().getStringExtra("Name");
        String author = getIntent().getStringExtra("Author");
        String description = getIntent().getStringExtra("Description");
        int view = 1000;

        nameBook.setText(name);
        authorBook.setText(author);
        descriptionBook.setText(description);
        viewBook.setText(view + "");

        //TinLM change text download
        tvDownloadBook = findViewById(R.id.tvDownloadBook);
        if(intent.getBooleanExtra("VipBook",false)) {
            tvDownloadBook.setText("Book is vip");
        }

            //SQLite save
        bookCaseManager = new DatabaseHelper(this);
        //TinLM check book is download

        tvDownloadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookcaseUtilities utilities = new BookcaseUtilities();
                SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                String accId =sharedPreferences.getString(ConstainServer.ACCOUNTID, "0");
                boolean checkInBookcase = utilities.checkBookcase(accId, intent.getIntExtra("Id",0));
                // check book in bookcase
                if(checkInBookcase) {

                    if (ContextCompat.checkSelfPermission(BookDetailActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        new DownloadFile().execute(intent.getStringExtra("Link"), intent.getStringExtra("Name") + ".pdf");

                    } else {
                        requestStoragePermission();
                    }
                } else {
                    //check book is vip
                    if(intent.getBooleanExtra("VipBook",false)) {

                        // check date for vip acount
                        Date currentTime = Calendar.getInstance().getTime();
                        AccountUtilities accountUtilities = new AccountUtilities();
                        SharedPreferences sharedPreferences1 = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                        String usernameLogin = sharedPreferences.getString(ConstainServer.USERNAME, null);
                        User user = accountUtilities.getUserDetail(usernameLogin);
                        long diff = 0;
                        long vipAvaiable;
                        diff = user.getVIPEndDate().getTime() - currentTime.getTime();

                        if(diff > 0) {
                            if (ContextCompat.checkSelfPermission(BookDetailActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                new DownloadFile().execute(intent.getStringExtra("Link"), intent.getStringExtra("Name") + ".pdf");

                            } else {
                                requestStoragePermission();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Bạn chưa có vip", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (ContextCompat.checkSelfPermission(BookDetailActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            new DownloadFile().execute(intent.getStringExtra("Link"), intent.getStringExtra("Name") + ".pdf");

                        } else {
                            requestStoragePermission();
                        }
                    }
                }
            }
        });


    }

    public void clickToGetPDF(View view) {


    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BookDetailActivity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }
    private class DownloadFile extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... strings) {

            int count;
            String fileUrl = strings[0];
// -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
            //change pdf name;
            //default name theo link pdf
//            String fileName = strings[1];
            //tên mình thay đổi lại
            String fileName = strings[1];

            File folder = new File(Environment.getExternalStorageDirectory(), "BOOKHOUSE PDF"
            );

            //File folder = new File("/sdcard/Download", "PDF DOWNLOAD");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                URL url = new URL(fileUrl);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(pdfFile);

                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            addToDatabaseHelper(fileName);
            return null;
        }
        protected void addToDatabaseHelper(String nameLink){
            Book newBook = new Book(
                    intent.getIntExtra("Id",0),
                    nameBook.getText().toString(),
                    authorBook.getText().toString(),
                    descriptionBook.getText().toString(),
                    0,
                    Environment.getExternalStorageDirectory() + "/BOOKHOUSE PDF" +"/"+ nameLink,
                    "for future"
                    );
            bookCaseManager.addBook(newBook);

            //TinLM 2/11/2018 post bookcase
            BookcaseUtilities utilities = new BookcaseUtilities();

            boolean resultAdd = utilities.postBookcase( getAccId() ,getIntent().getIntExtra("Id",0));

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

        }

        // TinLM get id account from reference
        private String getAccId() {
            SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
            String accId = sharedPreferences.getString(ConstainServer.ACCOUNTID, "0");
            return accId;
        }
    }
}