package com.app.gandalf.piquatro;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class FragmentHomeListaProduto extends Fragment {
    private static final String TAG = "Produtos";

    private ViewGroup hospedeiro;
    private int cat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_lista_produtos,container,false);

         hospedeiro = v.findViewById(R.id.container);

        NetworkCall myCall = new NetworkCall();
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";

        cat = 0;

        Bundle mBundle = new Bundle();
        if(mBundle != null){
            mBundle = getArguments();
            cat = mBundle.getInt("categoria");
        }

        //Pega a categoria para busca
        Toast toast = Toast.makeText(getActivity(),"Carregando...",Toast.LENGTH_LONG);
        toast.show();
        myCall.execute(url+"/"+cat);

        return v;
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
        CardView cardView = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.activity_produtos, hospedeiro,false);


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
                Intent i = new Intent(getActivity(), descProduto.class);
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

        ((LinearLayout) hospedeiro).addView(cardView);

    }


}