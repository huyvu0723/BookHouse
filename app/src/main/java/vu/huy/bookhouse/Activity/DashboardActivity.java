package vu.huy.bookhouse.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import vu.huy.bookhouse.Constant.ConstainServer;
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
    int positionCategoryAuthor;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<Category> listTitle;
    private HashMap<Category, List<Category>> listChild;
    BookUtilities utilities;
    private int lastExpandedPosition = -1;
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
                Category selectedCat = new Category();
                if(groupPosition != positionCategoryAuthor) {
                    List<Category> child = utilities.getChildCategory(listTitle.get(groupPosition).getCatId());
                    selectedCat = child.get(childPosition);
                }else{
                    List<Category> lstAut = utilities.getAllAuthor();
                    selectedCat = lstAut.get(childPosition);
                }
                drawer_home.closeDrawers();
                Intent reload = getIntent();
                reload.putExtra("FilterBook", selectedCat.getCatId());

                extras = reload.getExtras();
                homeFrag = new HomeFragment();
                homeFrag.setArguments(extras);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFrag).commit();

                bottomNavigationView.bringToFront();
                fragment_layout.bringToFront();
                return true;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        drawer_home.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                fragment_layout.bringToFront();
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        // TinLM choose bookcase layout when is delete
        Intent checkDelete = getIntent();
        int isDelete = checkDelete.getIntExtra("IsDelete",0);
        if (isDelete == 1) {
            drawer_home.closeDrawers();
            bottomNavigationView.setSelectedItemId(R.id.nav_library);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookcaseFrag).commit();
        }
        int addMoney = checkDelete.getIntExtra("AddMoney",0);
        if (addMoney == 1) {
            drawer_home.closeDrawers();
            bottomNavigationView.setSelectedItemId(R.id.nav_account);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,accountFrag).commit();
        }


    }
    private void initdataCategory(){
        listTitle = new ArrayList<>();
        listChild = new HashMap<>();
        listTitle = utilities.getParentCategory();
        for (int i = 0; i < listTitle.size(); i++) {
            List<Category> child = utilities.getChildCategory(listTitle.get(i).getCatId());
            listChild.put(listTitle.get(i), child);
        }
        //Add author
        Category author = new Category(0, "Author");
        List<Category> lstAut = utilities.getAllAuthor();
        listTitle.add(author);
        listChild.put(author, lstAut);
        positionCategoryAuthor = listChild.size() -1;
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
//        fragment_layout.bringToFront();
//        drawer_home.closeDrawers();
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

                    drawer_home.closeDrawers();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookcaseFrag).commit();
                    break;
                case R.id.nav_account:
//                    selectedFrag = new AccountFragment();
                    accountFrag.setArguments(extras);

                    drawer_home.closeDrawers();
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

        drawer_home.openDrawer(GravityCompat.START);


    }


    public void clickToGetBookDetail(View view) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        startActivity(intent);
    }

    public void clickToPayVIP(View view) {
        Intent oldIntent = getIntent();
        Bundle extras = oldIntent.getExtras();
        String userId = extras.getString("UserID");
        Intent intent = new Intent(this, PayVIPActivity.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void clickToLogout(View view) {
        logoutAccount();
        this.finish();
    }

    // TinLM 4/11/2018 logout account
    public void logoutAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences(ConstainServer.SHARE_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
