package vu.huy.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.adapter.BookHomeRecycleViewAdapter;
import vu.huy.bookhouse.adapter.BookcaseRecyclerViewAdapter;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.utilities.BookUtilities;

public class HomeFragment extends Fragment {
    RecyclerView myr;
    EditText searchHome;
    String searchValue = "";
    int filter;
    BookUtilities utilities;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        utilities = new BookUtilities();
        Bundle extras = getArguments();

        myr =  v.findViewById(R.id.recyleBookHome);
        filter = extras.getInt("FilterBook");
        searchHome = v.findViewById(R.id.txtSearchHome);

        searchHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchValue = s + "";
                List<Book> arrayList = utilities.getBookByDate(filter, searchValue);
                BookHomeRecycleViewAdapter myAdapter = new BookHomeRecycleViewAdapter( v.getContext() , arrayList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);
                myr.setLayoutManager(layoutManager);
                myr.setAdapter(myAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        List<Book> arrayList = utilities.getBookByDate(filter, searchValue);
        BookHomeRecycleViewAdapter myAdapter = new BookHomeRecycleViewAdapter( v.getContext() , arrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);
        myr.setLayoutManager(layoutManager);
        myr.setAdapter(myAdapter);
        return v;
    }
    public void loadBook(){

    }
}
