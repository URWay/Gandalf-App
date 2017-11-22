package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gandalf.piquatro.Carrinho.Carrinho;
import com.app.gandalf.piquatro.Carrinho.FragmentCarrinho;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class NewIndex extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewGroup mensagens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mensagens = (ViewGroup) findViewById(R.id.container);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(NewIndex.this, Login.class);
                startActivity(i);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Home");



        NetworkCall myCall = new NetworkCall();
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";

        //Pega a categoria para busca
        myCall.execute(url + "/0");
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



    // SÒ PRODUTO
    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respostaCompleta = "";
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuilder resultado = new StringBuilder();
                String linha = bufferedReader.readLine();

                while (linha != null) {
                    resultado.append(linha);
                    linha = bufferedReader.readLine();
                }

                respostaCompleta = resultado.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return respostaCompleta;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray json = new JSONArray(result);

                int idProduto, qtd;
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;
                int to = json.length();

                //Só pode retornar 14
                if (to >= 14) {
                    to = 14;
                }

                for (int i = 0; i <= to; i++) {
                    idProduto = json.getJSONObject(i).getInt("idProduto");
                    nomeProduto = json.getJSONObject(i).getString("nomeProduto");
                    descProduto = json.getJSONObject(i).getString("descProduto");
                    precProduto = json.getJSONObject(i).getDouble("precProduto");
                    descontoPromocao = json.getJSONObject(i).getDouble("descontoPromocao");
                    imagem = json.getJSONObject(i).getString("imagem");
                    qtd = json.getJSONObject(i).getInt("qtdMinEstoque");
                    addItem(idProduto, nomeProduto, descProduto, precProduto, descontoPromocao, imagem, qtd);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addItem(int idProd, String nomeProd, String descProd, final double precProd, double descPromocao, String img, int qtd) {
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.activity_produtos, mensagens, false);

        final int produto1 = idProd;

        final TextView nome = (TextView) cardView.findViewById(R.id.nomeProduto);
        TextView prec = (TextView) cardView.findViewById(R.id.precProduto);

        final double precodescontado = precProd - descPromocao;
        TextView precodesconto = (TextView) cardView.findViewById(R.id.precodesconto);


        final ImageView image = (ImageView) cardView.findViewById(R.id.imageViewListaProdutos);
        final byte[] image64 = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);


        nome.setText(nomeProd);
        prec.setText(new DecimalFormat("R$ #,##0.00").format(precProd));
        prec.setPaintFlags(prec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        precodesconto.setText(new DecimalFormat("R$ #,##0.00").format(precodescontado));
        image.setImageBitmap(bitmap);

        final String descP = descProd;
        final String nomeP = nomeProd;
        final String imageP = img;
        final double precoP = precProd;
        final int qtdP = qtd;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewIndex.this, descProduto.class);
                i.putExtra("idProduto", String.valueOf((produto1)));
                i.putExtra("nomeProduto", nomeP);
                i.putExtra("descProduto", descP);
                i.putExtra("image", imageP);
                i.putExtra("precProd", String.valueOf(precoP));
                i.putExtra("descPromocao", String.valueOf(precodescontado));
                i.putExtra("qtdMinEstoque", String.valueOf(qtdP));

                startActivity(i);
            }
        });
        mensagens.addView(cardView);
    }


}
