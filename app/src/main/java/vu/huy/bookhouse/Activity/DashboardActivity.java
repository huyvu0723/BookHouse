package vu.huy.bookhouse.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.Fragment.AccountFragment;
import vu.huy.bookhouse.Fragment.HomeFragment;
import vu.huy.bookhouse.Fragment.LibraryFragment;
import vu.huy.bookhouse.adapter.CustomCatogoryListAdapter;
import vu.huy.bookhouse.model.Category;
import vu.huy.bookhouse.utilities.BookUtilities;

public class DashboardActivity extends AppCompatActivity {

    Fragment homeFrag, bookcaseFrag, accountFrag;
    BottomNavigationView bottomNavigationView;
    FrameLayout fragment_layout;
    NavigationView navigationView;
    DrawerLayout drawer_home;
    TextView headerName;
    Bundle extras;
    EditText searchHome;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<Category> listTitle;
    private HashMap<Category, List<Category>> listChild;
    BookUtilities utilities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        homeFrag = new HomeFragment();
        bookcaseFrag = new LibraryFragment();
        accountFrag = new AccountFragment();
        utilities = new BookUtilities();
        initView();
        extras =this.getIntent().getExtras();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        homeFrag.setArguments(extras);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                homeFrag).commit();
        drawer_home = findViewById(R.id.home_drawer);
        navigationView = findViewById(R.id.naviDrawer);
        searchHome = findViewById(R.id.txtSearchHome);

        //set cái header cho navigation trong drawer
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.header_account);
        //lấy cái header ra
        TextView header = headerLayout.findViewById(R.id.txtHeaderName);
        drawer_home.setDrawerListener(new ActionBarDrawerToggle(this, drawer_home, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });


        //set cho header drawer
        header.setText(extras.getString("HeaderName"));
        //set cho cái menu
        expandableListView = findViewById(R.id.list_cate);
        initdataCategory();
        listAdapter = new CustomCatogoryListAdapter(this, listTitle, listChild);
        expandableListView.setAdapter(listAdapter);
        expandableListView.setIndicatorBounds(expandableListView.getWidth(), expandableListView.getWidth());
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                List<Category> child = utilities.getChildCategory(listTitle.get(groupPosition).getCatId());
                Category selectedCat = child.get(childPosition);
                drawer_home.closeDrawers();
                Intent reload = getIntent();
                reload.putExtra("FilterBook", selectedCat.getCatId());

                extras = reload.getExtras();
                homeFrag = new HomeFragment();
                homeFrag.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFrag).commit();
//                finish();
//                startActivity(reload);
                return true;
            }
        });
    }
    private void initdataCategory(){
        listTitle = new ArrayList<>();
        listChild = new HashMap<>();
        listTitle = utilities.getParentCategory();
        for (int i = 0; i < listTitle.size(); i++) {
            List<Category> child = utilities.getChildCategory(listTitle.get(i).getCatId());
            listChild.put(listTitle.get(i), child);
        }
    }
    private void initView(){
        fragment_layout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawer_home = findViewById(R.id.home_drawer);
        headerName = findViewById(R.id.txtHeaderName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return btnOpenDrawer.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    //Chạy khi ấn bên ngoài tab để đóng menu
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        bottomNavigationView.bringToFront();
//        fragment_layout.bringToFront();
//        drawer_home.closeDrawer(Gravity.START);
//        return super.dispatchTouchEvent(ev);
//    }

    //mở các fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
//                    selectedFrag = new HomeFragment();
                    homeFrag.setArguments(extras);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFrag).commit();
                    break;
                case R.id.nav_library:
//                    selectedFrag = new LibraryFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookcaseFrag).commit();
                    break;
                case R.id.nav_account:
//                    selectedFrag = new AccountFragment();
                    accountFrag.setArguments(extras);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,accountFrag).commit();
                    break;
            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrag).commit();
            return true;
        }
    };

    //Mở menu
    public void clickToMenuHome(View view) {
        drawer_home.bringToFront();
            drawer_home.openDrawer(Gravity.START);


    }


    public void clickToGetBookDetail(View view) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("IdBook", "01");
        startActivity(intent);
    }

    public void clickToPayVIP(View view) {
        Intent intent = new Intent(this, PayVIPActivity.class);
        startActivity(intent);
    }

    public void clickToLogout(View view) {
        this.finish();
    }
}
