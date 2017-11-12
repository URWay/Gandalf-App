package com.app.gandalf.piquatro;

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

        if(email != null){
            Toast toast = Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
        }


       tablogin = (TabItem) findViewById(R.id.tablogin);
       tablogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (savedInstanceState == null) {

                //}
                    //MyFragment fragment = new MyFragment();
                    //getSupportFragmentManager().beginTransaction()
                            //.replace(R.id.frag_container,fragment).commit();



                }
        });

/*
       op_sobre = (MenuItem) findViewById(R.id.op_sobre);

      op_sobre.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {

              Intent i = new Intent(Home.this, Sobre.class);
              startActivity(i);

              return false;
          }
      });









    tabcarrinho.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Home.this, Carrinho.class);
            startActivity(intent);
        }
    });

    tabpedidos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Pedidos.class);
                startActivity(intent);
            }

    });


    tabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Home.class);
                startActivity(intent);
            }
    });


    tablogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
    });

        */
    }



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

            if (id == R.id.op_promocoes) {
                // Handle the camera action
            } else if (id == R.id.op_camisetas) {

            } else if (id == R.id.op_canecas) {

            } else if (id == R.id.op_posteres) {
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;


        }
    }

/*
//Lincoln

    public void produtos(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void promocoes(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void camisetas(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void canecas(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void posteres(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void categorias(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void filtros(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void sobrenos(View v) {
        Intent intent = new Intent(this, Home.class);
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