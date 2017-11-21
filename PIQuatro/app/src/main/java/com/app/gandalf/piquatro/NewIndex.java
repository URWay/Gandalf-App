package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.gandalf.piquatro.Carrinho.Carrinho;

public class NewIndex extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewIndex.this, Carrinho.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Home");


        FragmentHomeListaProduto fragment = new FragmentHomeListaProduto();


        if (savedInstanceState == null) {
            FragmentHomeListaProduto fragmentlista = new FragmentHomeListaProduto();
            Bundle bundle = new Bundle();


        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NewIndex.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_index, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;


        int id = item.getItemId();
        if(id == R.id.nav_home){
            fragmentClass = FragmentHomeListaProduto.class;
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
            getSupportActionBar().setTitle("Home");
        }
        else if (id == R.id.nav_promo) {
            getSupportActionBar().setTitle("Promoções");
        } else if (id == R.id.nav_poster) {
            getSupportActionBar().setTitle("Pôsteres");
        } else if (id == R.id.nav_caneca) {
            getSupportActionBar().setTitle("Canecas");
        } else if (id == R.id.nav_camiseta) {
            getSupportActionBar().setTitle("Camisetas");
        } else if (id == R.id.nav_login) {
            fragmentClass = FragmentLogin.class;
            //btn voltar


            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Login");     //Titulo para ser exibido na sua Action Bar em frente à seta

        } else if (id== R.id.nav_conta){

        } else if(id== R.id.nav_config){
            fragmentClass = FragmentConfiguracoes.class;
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Configurações");     //Titulo para ser exibido na sua Action Bar em frente à seta

        } else if(id == R.id.nav_sobre){

            fragmentClass = Sobre.class;
            getSupportActionBar().setTitle("Sobre nós");     //Titulo para ser exibido na sua Action Bar em frente à seta

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
