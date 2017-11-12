package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ListaProdutos extends AppCompatActivity {
    private ViewGroup mensagens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        mensagens = (ViewGroup) findViewById(R.id.container);
        NetworkCall myCall = new NetworkCall();

        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";

        // Executa a thread, passando null como parâmetro

        //Pega a categoria para busca
        Intent intent = getIntent();
        int idCat = intent.getIntExtra("categoria", 0);

        //AP = id inicial do produto
        myCall.execute(url+"/"+idCat+"?ap=0");
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
                
                int idProduto;
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;
                int to = json.length();

                //Só pode retornar 14
                if(to >= 14){
                    to = 14;
                }

                for (int i = 0; i <= to; i++) {
                    idProduto = json.getJSONObject(i).getInt("idProduto");
                    nomeProduto = json.getJSONObject(i).getString("nomeProduto");
                    descProduto = json.getJSONObject(i).getString("descProduto");
                    precProduto = json.getJSONObject(i).getDouble("precProduto");
                    descontoPromocao = json.getJSONObject(i).getDouble("descontoPromocao");
                    imagem = json.getJSONObject(i).getString("imagem");
                    addItem(idProduto, nomeProduto, descProduto, precProduto, descontoPromocao, imagem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addItem(int idProduto, String nomeProd, String descProd, double precProd, double descPromocao, String img) {
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.activity_produtos, mensagens, false);


        TextView nome = (TextView) cardView.findViewById(R.id.nomeProduto);
        TextView prec = (TextView) cardView.findViewById(R.id.precProduto);

        double precodescontado = precProd - descPromocao;
        TextView precodesconto = (TextView) cardView.findViewById(R.id.precodesconto);


        ImageView image = (ImageView) cardView.findViewById(R.id.imageViewListaProdutos);
        byte[] image64 = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);


        nome.setText(nomeProd);
        prec.setText(new DecimalFormat("R$ #,##0.00").format(precProd));
        prec.setPaintFlags(prec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        precodesconto.setText(new DecimalFormat("R$ #,##0.00").format(precodescontado));
        image.setImageBitmap(bitmap);
        mensagens.addView(cardView);


    }

  /*  private void CarregarHome(){
        Intent intent = new Intent(ListaProdutos.this, Home.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("",);
        intent.putExtra(bundle);
        startActivity(intent);
    }
*/
}