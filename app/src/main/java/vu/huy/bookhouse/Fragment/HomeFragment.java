package vu.huy.bookhouse.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        BookUtilities utilities = new BookUtilities();
        List<Book> arrayList = utilities.getBookByDate();
        myr =  v.findViewById(R.id.recyleBookHome);
        BookHomeRecycleViewAdapter myAdapter = new BookHomeRecycleViewAdapter( v.getContext() , arrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);
        myr.setLayoutManager(layoutManager);
        myr.setAdapter(myAdapter);
        return v;
    }
}
