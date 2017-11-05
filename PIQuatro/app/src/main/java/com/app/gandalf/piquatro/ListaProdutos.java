package com.app.gandalf.piquatro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListaProdutos extends AppCompatActivity {
    private ViewGroup container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        container = (ViewGroup) findViewById(R.id.container);
        NetworkCall myCall = new NetworkCall();

        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";
        // Executa a thread, passando null como parâmetro

        // Luiz, o valor de ap tem que ser o ID inicial dos produtos (Ex: 2 retorna os 15 primeiros produtos acima do ID 2, será utilziado para paginação)
        //desc diz qual a ordem é decrescente ou n
        //O primeiro parametro no path é o id da categoria (vamos ter q pegar ele do menu)
        //O segundo é a ordem (usado para filtros)
        //Para pesquisa será utilizado o parametro via get = pesq
        myCall.execute(url+"/1/idProduto?ap=0&desc=1");
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
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

                String respostaCompleta = resultado.toString();
                return respostaCompleta;

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

                //Objeto do layout
                container = (ViewGroup) findViewById(R.id.container);
                int idProduto;
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;

                for (int i = 0; i <= 14; i++) {
                    idProduto = json.getInt("idProduto");
                    nomeProduto = json.getString("nomeProduto");
                    descProduto = json.getString("descProduto");
                    precProduto = json.getDouble("precProduto");
                    descontoPromocao = json.getDouble("descontoPromocao");
                    imagem = json.getString("imagem");
                    addItem(idProduto, nomeProduto, descProduto, precProduto, descontoPromocao, imagem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addItem(int idProduto, String nomeProd, String descProd, double precProd, double descPromocao, String img) {
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.produto_container, container, false);

        TextView nome = (TextView) cardView.findViewById(R.id.nomeProduto);
        TextView prec = (TextView) cardView.findViewById(R.id.precProduto);
        TextView promo = (TextView) cardView.findViewById(R.id.precProduto);

        nome.setText(nomeProd);
        prec.setText(String.valueOf(precProd));
        promo.setText(String.valueOf(descPromocao));

        // Imagem
        ImageView image = (ImageView) cardView.findViewById(R.id.imageViewListaProdutos);
        byte[] image64 = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
        image.setImageBitmap(bitmap);

        container.addView(cardView);
    }
}