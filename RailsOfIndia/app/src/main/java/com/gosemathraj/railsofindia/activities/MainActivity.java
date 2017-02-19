package com.gosemathraj.railsofindia.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.fragments.FragmentHome;
import com.gosemathraj.railsofindia.retrofit.NetworkCallsRetrofitImpl;
import com.gosemathraj.railsofindia.services.ServiceCalls;
import com.gosemathraj.railsofindia.utility.StringConstants;
import com.gosemathraj.railsofindia.utility.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;

    int fragmentId;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try{
            init(savedInstanceState);
        }catch(Exception e){
            Log.e(StringConstants.EXCEPTION,e.toString());
        }
    }

    private void init(Bundle savedInstanceState) {

        setActionBar();
        setNavigationDrawer();
        setUpSearchView();
        setOnClickListener();
        if(savedInstanceState == null){
            Utils.getInstance().addFragmentfromActivity(this,new FragmentHome(),R.id.frame_container);
        }
    }

    private void setOnClickListener() {


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawers();
                switch(item.getItemId()){

                    case R.id.cancelled_trains:
                        fragmentId = 4;
                        break;

                    case R.id.rescheduled_trains:
                        fragmentId = 5;
                        break;

                    case R.id.facts:
                        fragmentId = 6;
                        break;

                    case R.id.settings:
                        fragmentId = 7;
                        break;

                    case R.id.about:
                        fragmentId = 8;
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.fragmentId),fragmentId);
                Utils.getInstance().startActivity(MainActivity.this,bundle, HelperActivity.class);
                return true;

            }
        });
    }

    private void setActionBar() {

        setSupportActionBar(toolbar);
    }

    private void setNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem searchtem = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(searchtem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifications:
                Utils.getInstance().startActivity(this,null,NotificationActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpSearchView() {

        materialSearchView.setHint(getResources().getString(R.string.searchString));
        materialSearchView.setHintTextColor(getResources().getColor(R.color.lightBlack));

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.search_String),query);
                materialSearchView.setQuery("",false);
                if(Utils.getInstance().checkForNull(query) && query != null){
                    Utils.getInstance().startActivity(MainActivity.this,bundle,SearchActivity.class);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ServiceCalls.getInstance().setNetworkCallsimpl(new NetworkCallsRetrofitImpl());
    }

    @Override
    public void onBackPressed() {

        if(materialSearchView.isSearchOpen()){
            materialSearchView.closeSearch();
        }else{
            super.onBackPressed();
        }
    }
}
