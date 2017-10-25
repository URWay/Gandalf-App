package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(null);


        Intent intent = new Intent(this, CadastroCliente.class);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.op_promocoes) {
            // Handle the camera action
        } else if (id == R.id.op_camisetas) {

        } else if (id == R.id.op_canecas) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        } else if (id == R.id.op_posteres) {

        }else if (id == R.id.op_sobre){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    Intent intent = new Intent(MainActivity.this,
            MinhaAtividade.class);

    Intent intent = new
            Intent("com.senac.luiz.piquatro.Produtos");

    Intent intent = new Intent(MainActivity.this, MinhaAtividade.class);
    startActivity(intent);

    Intent i = new Intent(Intent.ACTION_VIEW,
            Uri.parse("http://www.google.com")); //trocar o endereço

    Intent i = new Intent();
        i.putExtra("nome", objetoComODado);
        i.putExtra("id", 5);
    startActivity(intent);

    Intent intent = getIntent();
    int id = intent.getIntExtra("id", 0);
    String name = intent.getStringExtra("name");

    startActivityForResult(intent, 1);


    Intent returnIntent = new Intent();
    returnIntent.putExtra("retorno", "valor de
    retorno");
    setResult(RESULT_OK, returnIntent);


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
