package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.app.gandalf.piquatro.Carrinho.FragmentCarrinho;
import com.app.gandalf.piquatro.Carrinho.empty;

public class NewIndex extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = new Bundle();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //SharedPreferencesCart sh = new SharedPreferencesCart();
        //sh.removeSharedItens(NewIndex.this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                Class fragmentClass = null;

                SharedPreferences prefs = NewIndex.this.getSharedPreferences("PRODUCT_APP", MODE_PRIVATE);
                String product = prefs.getString("Product", null);
                if(product == null || product.equals("") || product.equals("[]"))
                    fragmentClass = empty.class;
                else


                    fragmentClass = FragmentCarrinho.class;


                getSupportActionBar().setTitle("Carrinho");
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Home");

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
            bundle.putInt("categoria",0);
            getSupportActionBar().setTitle("Home");
            fab.show();

        }
        else if (id == R.id.nav_promo) {

            fragmentClass = FragmentHomeListaProduto.class;

            getSupportActionBar().setTitle("Promoções");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
        } else if (id == R.id.nav_colec) {

            fragmentClass = FragmentHomeListaProduto.class;

            bundle.putInt("categoria",1);
            getSupportActionBar().setTitle("Colecionáveis");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
        } else if (id == R.id.nav_jogos) {

            fragmentClass = FragmentHomeListaProduto.class;

            bundle.putInt("categoria",2);
            getSupportActionBar().setTitle("Jogos");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
        } else if (id == R.id.nav_quad) {

            fragmentClass = FragmentHomeListaProduto.class;

            bundle.putInt("categoria",3);
            getSupportActionBar().setTitle("Quadrinhos");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
        } else if (id == R.id.nav_canec) {

            fragmentClass = FragmentHomeListaProduto.class;

            bundle.putInt("categoria",5);
            getSupportActionBar().setTitle("Canecas");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();

        } else if (id == R.id.nav_deco) {

            fragmentClass = FragmentHomeListaProduto.class;

            bundle.putInt("categoria",6);
            getSupportActionBar().setTitle("Decoração");
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();

        } else if (id == R.id.nav_login) {

            fragmentClass = FragmentLogin.class;

            getSupportActionBar().setTitle("Login");     //Titulo para ser exibido na sua Action Bar em frente à seta


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

        } else if (id== R.id.nav_pedidos){

            fragmentClass = FragmentListaPedidos.class;

            getSupportActionBar().setTitle("Meus Pedidos");     //Titulo para ser exibido na sua Action Bar em frente à seta
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

        } else if(id== R.id.nav_config){

            fragmentClass = FragmentConfiguracoes.class;

            getSupportActionBar().setTitle("Configurações");     //Titulo para ser exibido na sua Action Bar em frente à seta
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();


        } else if(id == R.id.nav_sobre){

            fragmentClass = FragmentSobre.class;

            getSupportActionBar().setTitle("Sobre nós");     //Titulo para ser exibido na sua Action Bar em frente à seta
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();


        } else if(id == R.id.nav_qrcode){

            fragmentClass = QRCode.class;

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);


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
