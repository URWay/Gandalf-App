package com.app.gandalf.piquatro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Index extends AppCompatActivity {

    private static final String TAG = "Home";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


<<<<<<< HEAD
=======
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        setTitle(null);

        SharedPreferences prefs = getSharedPreferences("SessionLogin", MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email != null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }

>>>>>>> 3c25de59aea4b2a2ef51820265ca2782ceae2718
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHomeListaProduto(), "Home");
        //adapter.addFragment(new FragmentCarrinho(),"Carrinho");
        //adapter.addFragment(new FragmentPedidos(),"Pedidos");
        adapter.addFragment(new FragmentLogin(), "Login");

        viewPager.setAdapter(adapter);
    }

}