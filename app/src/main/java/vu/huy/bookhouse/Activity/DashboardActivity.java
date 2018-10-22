package vu.huy.bookhouse.Activity;

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

        fragment_layout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        drawer_home = findViewById(R.id.home_drawer);
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

        //get pdf file
        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("pdfexample.pdf")
                .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen

                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                .pageFitPolicy(FitPolicy.WIDTH)
                .pageSnap(true) // snap pages to screen boundaries
                .pageFling(false) // make a fling change only a single page like ViewPager
                .nightMode(false) // toggle night mode
                .load();
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
}
