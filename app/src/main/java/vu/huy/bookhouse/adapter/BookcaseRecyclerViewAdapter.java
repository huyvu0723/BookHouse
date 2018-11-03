package vu.huy.bookhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.util.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import vu.huy.bookhouse.Activity.BookCaseDetailActivity;
import vu.huy.bookhouse.Activity.BookDetailActivity;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Bookcase;
import vu.huy.bookhouse.utilities.AccountUtilities;
import vu.huy.bookhouse.utilities.BookUtilities;

// TinLM 30/10/2018 Create

public class BookcaseRecyclerViewAdapter extends RecyclerView.Adapter<BookcaseRecyclerViewAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Bookcase> mData;

    public BookcaseRecyclerViewAdapter(Context mContext, List<Bookcase> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mView = mInflater.inflate(R.layout.cardview_item_book, viewGroup, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvBookName.setText(mData.get(position).getName());
        myViewHolder.tvBookAuthor.setText(mData.get(position).getAutName());


        // check book image link to set image
        if(mData.get(position).getBookImage() == null) {
            myViewHolder.imgBookId.setImageResource(R.drawable.bookhouse);
        } else {
            // we need to check link exists
//            File imgFile = new  File(mData.get(position).getBook_img());
//            if(imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                myViewHolder.imgBookId.setImageBitmap(myBitmap);

//            } else {
//                myViewHolder.imgBookId.setImageResource(R.drawable.book);
//            }
            Picasso.get().load(mData.get(position).getBookImage()).into(myViewHolder.imgBookId);
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bookcase book = (Bookcase) mData.get(position);

                //check book is download
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/BOOKHOUSE PDF/" + book.getName()+".pdf");
                if(pdfFile.exists()) {
                    Intent intent = new Intent(mContext, BookCaseDetailActivity.class);
                    intent.putExtra("Id", book.getBookId());
                    intent.putExtra("Name", book.getName());
                    intent.putExtra("Author", book.getAutName());
                    intent.putExtra("Description", book.getBookDescription());

                    intent.putExtra("Image", book.getBookImage());

                    intent.putExtra("Link", Environment.getExternalStorageDirectory() + "/BOOKHOUSE PDF/" + book.getName()+".pdf");
                    mContext.startActivity(intent);
                } else {
                    BookUtilities utilities = new BookUtilities();

                    Book bookHome = utilities.searchBookByID(String.valueOf(mData.get(position).getBookId()));
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra("Id", bookHome.getBook_id());
                    intent.putExtra("Name", bookHome.getBook_name());
                    intent.putExtra("Author", bookHome.getBook_author());
                    intent.putExtra("Description", bookHome.getBook_description());
                    intent.putExtra("Link", bookHome.getBook_link());
                    intent.putExtra("Image", bookHome.getBook_img());
                    intent.putExtra("VipBook", bookHome.isVip());
                    mContext.startActivity(intent);
                }


//                File folder = new File(Environment.getExternalStorageDirectory(), "BOOKHOUSE PDF"
//                );
//                File pdfFile = new File(folder, book.getName()+".pdf");
//                try {
//                    FileInputStream fin = new FileInputStream(pdfFile);
//                    String link = convertStreamToString(fin);
//                    fin.close();
//                    intent.putExtra("Link", link);
//                } catch (Exception e) {
//                    Log.e("ERRORREAD", e.getMessage().toString());
//                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBookName,tvBookAuthor;
        ImageView imgBookId;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            imgBookId = itemView.findViewById(R.id.imgBookId);
            cardView = itemView.findViewById(R.id.cardviewBookId);
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
