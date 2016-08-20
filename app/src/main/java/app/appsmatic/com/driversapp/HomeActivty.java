package app.appsmatic.com.driversapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.Adabters.VeiwpagerAdb;
import app.appsmatic.com.driversapp.Fragments.Archived;
import app.appsmatic.com.driversapp.Fragments.Orders;
import app.appsmatic.com.driversapp.Fragments.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivty extends AppCompatActivity {

    public static String id;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       // window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));



        ViewPager p=(ViewPager)findViewById(R.id.veiwp);
        setupViewPager(p);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(p);



        id=this.getIntent().getStringExtra("DriverID");







    }
    private void setupViewPager(ViewPager viewPager) {
        VeiwpagerAdb adapter = new VeiwpagerAdb(getSupportFragmentManager());
        adapter.addFragment(new Orders(), "Orders");
        adapter.addFragment(new Archived(), "Archived");
        adapter.addFragment(new Profile(), "Profile");
        viewPager.setAdapter(adapter);
    }


}
