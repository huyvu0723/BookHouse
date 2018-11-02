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

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.adapter.BookcaseRecyclerViewAdapter;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.Bookcase;
import vu.huy.bookhouse.model.DatabaseHelper;
import vu.huy.bookhouse.utilities.BookcaseUtilities;

public class LibraryFragment extends Fragment {
//    ListView listView;
    RecyclerView myr;

    DatabaseHelper bookCaseManager;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_bookcase, container, false);
//        listView = view.findViewById(R.id.listBookCase);
//
//        bookCaseManager = new DatabaseHelper(view.getContext());
//        ArrayList<Book> arrayList = bookCaseManager.getAllBook();
//        ArrayAdapter<Book> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Book book = (Book) listView.getItemAtPosition(position);
//                Intent intent = new Intent(view.getContext(), BookCaseDetailActivity.class);
//                intent.putExtra("Id", book.getBook_id());
//                intent.putExtra("Name", book.getBook_name());
//                intent.putExtra("Author", book.getBook_author());
//                intent.putExtra("Description", book.getBook_description());
//                intent.putExtra("Link", book.getBook_link());
//                startActivity(intent);
//            }
//        });
        //       return view;
//    }

    //TinLM 29/10/2018
// set ting gridlayout for bookcase
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookcase, container, false);
        bookCaseManager = new DatabaseHelper(v.getContext());
        BookcaseUtilities utilities = new BookcaseUtilities();
        List<Bookcase> arrayList = utilities.getBookcaseByDId(1);
        myr =  v.findViewById(R.id.recycleviewBookId);
        BookcaseRecyclerViewAdapter myAdapter = new BookcaseRecyclerViewAdapter( v.getContext() , arrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);
        myr.setLayoutManager(layoutManager);
        myr.setAdapter(myAdapter);
        return v;
    }
}
