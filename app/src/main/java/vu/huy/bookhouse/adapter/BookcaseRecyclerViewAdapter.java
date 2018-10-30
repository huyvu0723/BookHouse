package vu.huy.bookhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import vu.huy.bookhouse.Activity.BookCaseDetailActivity;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Book;

// TinLM 30/10/2018 Create

public class BookcaseRecyclerViewAdapter extends RecyclerView.Adapter<BookcaseRecyclerViewAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Book> mData;

    public BookcaseRecyclerViewAdapter(Context mContext, List<Book> mData) {
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
        myViewHolder.tvBookName.setText(mData.get(position).getBook_name());
        myViewHolder.tvBookAuthor.setText(mData.get(position).getBook_author());

        // check book image link to set image
        if(mData.get(position).getBook_img() == null) {
            myViewHolder.imgBookId.setImageResource(R.drawable.bookhouse);
        } else {
            // we need to check link exists
            File imgFile = new  File(mData.get(position).getBook_img());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myViewHolder.imgBookId.setImageBitmap(myBitmap);
            } else {
                myViewHolder.imgBookId.setImageResource(R.drawable.book);
            }
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = (Book) mData.get(position);
                Intent intent = new Intent(mContext, BookCaseDetailActivity.class);
                intent.putExtra("Id", book.getBook_id());
                intent.putExtra("Name", book.getBook_name());
                intent.putExtra("Author", book.getBook_author());
                intent.putExtra("Description", book.getBook_description());
                intent.putExtra("Link", book.getBook_link());
                intent.putExtra("Image", book.getBook_img());
                mContext.startActivity(intent);
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
}
