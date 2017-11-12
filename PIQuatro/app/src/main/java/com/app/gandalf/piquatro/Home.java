package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


     private TabItem tabcarrinho;
     private TabItem tabpedidos;
     private TabItem tabhome;
     private TabItem tablogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(null);

        SharedPreferences prefs = getSharedPreferences("SessionLogin", MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email != null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }


        tablogin = (TabItem) findViewById(R.id.tablogin);

    }
/*
        public void CarregaHome(){
            tabhome = (TabItem) findViewById(R.id.tabhome);

            Intent intent = new Intent(Home.this, ListaProdutos.class);
            Bundle params = new Bundle();



            startActivity(intent);
        }

*/

        @Override
        public void onBackPressed () {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){

            getMenuInflater().inflate(R.menu.home, menu);



            return false;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Intent intent = new Intent(this, ListaProdutos.class);



            if (id == R.id.op_promocoes) {
<<<<<<< HEAD
                // Handle the camera action
            } else if (id == R.id.op_colecionaveis) {
                 intent.putExtra("categoria",1 );
=======

                
            } else if (id == R.id.op_camisetas) {

>>>>>>> 9e4f3ced5d492d16779589e7c8943c783edba4ac
            } else if (id == R.id.op_canecas) {
                intent.putExtra("categoria",5 );
            } else if (id == R.id.op_decoracao) {
                intent.putExtra("categoria",6 );
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            startActivity(intent);
            return true;


        }
    }

/*
//Lincoln

    public void produtos(View v) {
        Intent intent = new Intent(this, ListaProdutos.class);
        startActivity(intent);
    }


    public void promocoes(View v) {
        Intent intent = new Intent(this, ListaProdutos.class);
        startActivity(intent);
    }

    public void categorias(View v) {
        Intent intent = new Intent(this, ListaProdutos.class);
        startActivity(intent);
    }


    public void filtros(View v) {
        Intent intent = new Intent(this, ListaProdutos.class);
        startActivity(intent);
    }

    public void sobrenos(View v) {
        Intent intent = new Intent(this, ListaProdutos.class);
        startActivity(intent);
    }
//Lincoln termina aqui

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        // Verifica de quem veio a resposta
        if (requestCode == 1) {
            // Se foi resposta de sucesso
            if (resultCode == RESULT_OK) {
                // Faz alguma coisa
            }
        }
    }
}

*/