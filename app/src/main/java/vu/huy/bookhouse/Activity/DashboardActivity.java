package vu.huy.bookhouse.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import vu.huy.bookhouse.R;
import vu.huy.bookhouse.Fragment.AccountFragment;
import vu.huy.bookhouse.Fragment.HomeFragment;
import vu.huy.bookhouse.Fragment.LibraryFragment;

public class DashboardActivity extends AppCompatActivity {

    Fragment selectedFrag = null;
    BottomNavigationView bottomNavigationView;
    FrameLayout fragment_layout;
    DrawerLayout drawer_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

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


    }
    private void initView(){
        fragment_layout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        drawer_home = findViewById(R.id.home_drawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return btnOpenDrawer.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
}

//Chạy khi ấn bên ngoài tab để đóng menu
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        bottomNavigationView.bringToFront();
        fragment_layout.bringToFront();
        drawer_home.closeDrawer(Gravity.START);
        return super.dispatchTouchEvent(ev);
    }

    //mở các fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFrag = new HomeFragment();
                    break;
                case R.id.nav_library:
                    selectedFrag = new LibraryFragment();
                    break;
                case R.id.nav_account:
                    selectedFrag = new AccountFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrag).commit();
            return true;
        }
    };

    //Mở menu
    public void clickToMenuHome(View view) {
        drawer_home.bringToFront();
        drawer_home.openDrawer(Gravity.START);
    }

    public void clickToOpenPDF(View view) {
        Intent intent = new Intent(this, ReadPDFActivity.class);
        startActivity(intent);
    }

    public void clickToGetBookDetail(View view) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        startActivity(intent);
    }
}