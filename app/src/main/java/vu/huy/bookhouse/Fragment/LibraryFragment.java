package vu.huy.bookhouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vu.huy.bookhouse.Activity.BookCaseDetailActivity;
import vu.huy.bookhouse.R;
import vu.huy.bookhouse.model.Book;
import vu.huy.bookhouse.model.DatabaseHelper;

public class LibraryFragment extends Fragment {
    ListView listView;

    DatabaseHelper bookCaseManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookcase, container, false);
        listView = view.findViewById(R.id.listBookCase);

        bookCaseManager = new DatabaseHelper(view.getContext());
        ArrayList<Book> arrayList = bookCaseManager.getAllBook();
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) listView.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), BookCaseDetailActivity.class);
                intent.putExtra("Id", book.getBook_id());
                intent.putExtra("Name", book.getBook_name());
                intent.putExtra("Author", book.getBook_author());
                intent.putExtra("Description", book.getBook_description());
                intent.putExtra("Link", book.getBook_link());
                startActivity(intent);
            }
        });
        return view;
    }

}
