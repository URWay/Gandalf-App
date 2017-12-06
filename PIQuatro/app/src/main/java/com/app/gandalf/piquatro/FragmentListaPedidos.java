package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class FragmentListaPedidos extends Fragment {
    private ListView pedidoslista;

    private static final String TAG = "Meus Pedidos";
    private int idPed = 0;
    private ViewGroup hospedeiro;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_desc_pedido, container, false);

        hospedeiro = v.findViewById(R.id.container);
        NetworkCall myCall = new NetworkCall();
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/pedido/";
        Fragment fragment = null;
        Class fragmentClass = null;
        //instanciar para n mudar o código
        Functions f = new Functions();

        int idCliente = f.getId(getActivity());

        if(idCliente <= 0){
            fragmentClass = FragmentLogin.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();
        }
        Toast toast = Toast.makeText(getActivity(),"Carregando...",Toast.LENGTH_LONG);
        toast.show();
        myCall.execute(url + "/"+idCliente);

        return v;
    }

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

                int to = json.length();

                int idProduto,idPedido, qtd;
                String nomeProduto, imagem, status;
                double precProduto, preco;

                for (int i = 0; i <= to; i++) {
                   idProduto = 1;//json.getJSONObject(i).getInt("idProduto");
                    idPedido = json.getJSONObject(i).getInt("idPedido");
                    nomeProduto = json.getJSONObject(i).getString("nomeProduto");
                    preco = json.getJSONObject(i).getDouble("precoVendaItem");
                    imagem = json.getJSONObject(i).getString("imagem");
                    qtd = json.getJSONObject(i).getInt("qtdProduto");
                    status = json.getJSONObject(i).getString("descStatus");

                    addItem(idPedido, idProduto, nomeProduto, preco, imagem, qtd, status);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void addItem(int idPedido,int idProduto, String nomeProduto, double preco, String imagem, int qtd, String status) {
            CardView cardView = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.card_pedido, hospedeiro,false);


            final int produto1 = idProduto;

            final TextView nome = (TextView) cardView.findViewById(R.id.nomeProduto);
            TextView prec = (TextView) cardView.findViewById(R.id.precProduto);
            TextView vQtd = (TextView) cardView.findViewById(R.id.qtdProd);
            TextView vPreco = (TextView) cardView.findViewById(R.id.preco);
            TextView vStatus = (TextView) cardView.findViewById(R.id.status);
            final ImageView image = (ImageView) cardView.findViewById(R.id.imageViewListaProdutos);
            final byte[] image64 = Base64.decode(imagem, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);


            nome.setText(nomeProduto);
            prec.setText(new DecimalFormat("R$ #,##0.00").format(preco));
            vQtd.setText(String.valueOf(qtd));
            vPreco.setText(new DecimalFormat("R$ #,##0.00").format(preco*qtd));
            vStatus.setText(status);
            image.setImageBitmap(bitmap);

            ((LinearLayout) hospedeiro).addView(cardView);

        }

        }
    }






