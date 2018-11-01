package vu.huy.bookhouse.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Book;

public class BookHomeRecycleViewAdapter extends RecyclerView.Adapter<BookHomeRecycleViewAdapter.MyViewHolder> {

    Context mContext;
    List<Book> mData;

    public BookHomeRecycleViewAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cardview_item_book, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tvBookName.setText(mData.get(position).getBook_name());
        myViewHolder.tvBookAuthor.setText(mData.get(position).getBook_author());
        if( mData.get(position).getBook_img() == null ) {
            myViewHolder.imgBookId.setImageResource(R.drawable.bookhouse);
        }else {
            Picasso.get().load(mData.get(position).getBook_img()).into(myViewHolder.imgBookId);
        }
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
