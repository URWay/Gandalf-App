package com.app.gandalf.piquatro.Checkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.Functions;
import com.app.gandalf.piquatro.Mask;
import com.app.gandalf.piquatro.NewIndex;
import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.CartItensPedido;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.CheckoutPedido;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class Checkout extends AppCompatActivity {


    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";
    private int id;
    private int idEndereco;
    private int idPedido;
    private int idnumcard;
    private EditText txtnumcod;
    private EditText idcod;
    private Spinner spinnerano;
    private Spinner spinnermes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Checkout");     //Titulo para ser exibido na sua Action Bar em frente à seta

        txtnumcod = (EditText) findViewById(R.id.txtnumcod);
        idcod = (EditText) findViewById(R.id.idcod);

        txtnumcod.addTextChangedListener(Mask.insert("####.####.####.####", txtnumcod));
        idcod.addTextChangedListener(Mask.insert("###", idcod ));

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(PRODUCTS, null);




        // Login
        SharedPreferences prefsLogin = getSharedPreferences("SessionLogin", MODE_PRIVATE);
        id = prefsLogin.getInt("id", 0);

        List<Cart_List> cart = new ArrayList<>();

        SharedPreferencesCart sh = new SharedPreferencesCart();
        cart = sh.getItens(this);


        if(json != null){
            double total = 0;
            int qtd = 0;
            double precoProdutos = 0;

            for(int i = 0; i < cart.size(); i++){
                // Quantidade do carrinho
                qtd += cart.get(i).getQtd();

                // Preço dos produtos
                precoProdutos += cart.get(i).getPromocao();

                // Total
                total += cart.get(i).getQtd() * cart.get(i).getPromocao();
            }

            // Quantidade do carrinho
            TextView textVQtdProduto = (TextView) findViewById(R.id.textVQtdProduto);
            textVQtdProduto.setText(String.valueOf(qtd) + " produto(s)");

            // Total
            TextView editTotal = (TextView) findViewById(R.id.editTotal);
            editTotal.setText(new DecimalFormat("R$ #,##0.00").format(total));

            // Carrega sempre o primeiro endereço cadastrado do cliente
            NetworkCallEndereco myCall = new NetworkCallEndereco();
            myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/all/" + id );


            //BTN VOLTAR
             setTitle("Checkout");
            // Get a support ActionBar corresponding to this toolbar
            ActionBar ab = getSupportActionBar();
            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);



            // CARTÃO
            //----------------------- Parcela -------------------//
            String[] listParcela = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            final ArrayAdapter<String> spinnerArrayAdapterParcela = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listParcela);
            Spinner spinner = (Spinner) findViewById(R.id.spinnerParcela);
            spinnerArrayAdapterParcela.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapterParcela);


            String[] mes = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            final ArrayAdapter<String> spinnerArrayAdapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mes);
            Spinner spinnerM = (Spinner) findViewById(R.id.spinParcelas);
            spinnerArrayAdapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerM.setAdapter(spinnerArrayAdapterMes);

            String[] ano = new String[] {"2017"};
            List<String> anoS = new ArrayList<>();

            for(int i = 2012; i <= 2099; i++){
                anoS.add(String.valueOf(i));
            }

            final ArrayAdapter<String> spiAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anoS);
            Spinner spinnerAno = (Spinner) findViewById(R.id.spiAno);
            spiAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAno.setAdapter(spiAno);



            Button comprar = (Button) findViewById(R.id.comprar);

            comprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    String json = prefs.getString(PRODUCTS, null);

                    if(idEndereco <= 0){
                        Toast.makeText(getApplicationContext(), "Cadastre um endereço antes de realizar o pedido",Toast.LENGTH_SHORT).show();
                    } else {

                        if(json != null){
                            // Pedido
                            CheckoutPedido ckp = new CheckoutPedido();

                            // idCliente
                            ckp.setIdCliente(id);

                            /* idStatus
                                1 = Aberto
                                2 = Aguardando Pagamento
                                3 = Enviado para Transportadora
                                4 = Entregue
                                5 = Cancelado
                            */

                            ckp.setIdStatus(3);

                            // Data pedido
                            Date date = new Date();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            ckp.setDataPedido(dateFormat.format(date));

                            /* idTipoPagto
                                1 = Cartão de Crédito
                                2 = Boleto
                                3 = pagSeguro
                                4 = PayPal
                             */

                            ckp.setIdTipoPagto(1);

                            // idEndereco
                            ckp.setIdEndereco(idEndereco);

                            /* idAplicacao
                                1 = SQL
                                2 = Mobile
                                3 = Web
                            */

                            ckp.setIdAplicacao(2);

                            // Gravando pedido
                            Gson g = new Gson();
                            String jsonPedido = g.toJson(ckp);

                            NetworkCallPedido myCallpedido = new NetworkCallPedido();
                            String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/checkout";
                            myCallpedido.execute(url, jsonPedido, "POST");

                        }
                    }

                }
            });

        }
    }

    // Buscando informação do Endereço
    public class NetworkCallEndereco extends AsyncTask<String, Void, String> {

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

                int numero;
                String nomeEndereco, CEPEndereco, estado, cidade, logradouro, complementoEndereco, pais;

                // Primeiro endereço
                idEndereco = json.getJSONObject(0).getInt("idEndereco");
                numero = json.getJSONObject(0).getInt("numeroEndereco");
                nomeEndereco = json.getJSONObject(0).getString("nomeEndereco");
                logradouro = json.getJSONObject(0).getString("logradouroEndereco");
                estado = json.getJSONObject(0).getString("ufendereco");
                cidade = json.getJSONObject(0).getString("cidadeEndereco");
                CEPEndereco = json.getJSONObject(0).getString("cependereco");
                complementoEndereco = json.getJSONObject(0).getString("complementoEndereco");
                pais = json.getJSONObject(0).getString("paisEndereo");

                // Endereço de entrega
                TextView textEndereco = (TextView) findViewById(R.id.textEndereco);
                String nome = "";

                if(logradouro.equals("")){
                    nome = nomeEndereco;
                } else {
                    nome = logradouro;
                }

                // Setando valor do endereço na textView
                String endereco = nome + ", N°: " + numero + " complemento: " + complementoEndereco +
                        "\n" + CEPEndereco + " - " + cidade + "/" + estado + " - " + pais;

                textEndereco.setText(endereco);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // Grava o pedido
    public class NetworkCallPedido extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(params[2]);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty ("Content-Type", "application/json");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(params[1]);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                        line = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    Log.d ("tag",sb.toString());

                    return sb.toString();
                } else {
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject json = new JSONObject(result);
                idPedido = json.getInt("idPedido");

                if(idPedido != 0){
                    addItensPedido(idPedido);
                } else {
                    Functions f = new Functions();
                    f.showDialog("Erro", "Problema ao incluir o pedido!", Checkout.this);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Inclusão dos itens do pedido
    public void addItensPedido(int idPedido){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(PRODUCTS, null);

        if(json != null){
            try{
                JSONArray jitens = new JSONArray(json);
                List<CartItensPedido> itens = new ArrayList<>();
                CartItensPedido cartPed = null;

                // Itens do pedido
                if (jitens.length() > 0) {
                    for(int i = 0; i < jitens.length(); i++){
                        itens.add(new CartItensPedido());
                        itens.get(i).setIdPedido(idPedido);
                        itens.get(i).setIdProduto(jitens.getJSONObject(i).getInt("id"));
                        itens.get(i).setQtdProduto(jitens.getJSONObject(i).getInt("qtd"));
                        itens.get(i).setPrecoVendaItem(jitens.getJSONObject(i).getDouble("promocao"));
                    }

                    if(itens != null){
                        Gson g = new Gson();
                        String gItens = g.toJson(itens);
                        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/checkout/inserirItem";
                        NetworkCallPedidoItens myCallpedidoItens = new NetworkCallPedidoItens();
                        myCallpedidoItens.execute(url, gItens, "POST");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Problema ao gravar os itens do pedido", Toast.LENGTH_LONG).show();
                }

            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    // Grava itens do pedido
    public class NetworkCallPedidoItens extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(params[2]);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty ("Content-Type", "application/json");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(params[1]);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    return "Ok";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                if(result == null){
                    Toast.makeText(getApplicationContext(), "Erro ao realizar o pedido", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Pedido realizado com sucesso", Toast.LENGTH_SHORT).show();
                    SharedPreferencesCart sh = new SharedPreferencesCart();
                    sh.removeSharedItens(Checkout.this);

                }

                startActivity(new Intent(Checkout.this, NewIndex.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
