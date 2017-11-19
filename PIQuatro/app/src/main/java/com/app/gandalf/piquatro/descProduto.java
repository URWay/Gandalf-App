package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class descProduto extends AppCompatActivity {
    private TextView txtnomeprod;
    private ImageView imgproduto;
    private TextView txtdescricao;
    private TextView txtprecodesc;
    private TextView txtpreco;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        txtnomeprod = (TextView) findViewById(R.id.txtnomeprod);
        imgproduto = (ImageView) findViewById(R.id.imgproduto);
        txtdescricao = (TextView) findViewById(R.id.txtdescricao);
        txtprecodesc = (TextView) findViewById(R.id.txtprecodesc);
        txtpreco = (TextView) findViewById(R.id.txtpreco);

        Intent intent = getIntent();

        if(intent != null){
            try {

                if(!intent.getStringExtra("idProduto").equals("0")){
                    final int idProduto = Integer.parseInt(intent.getStringExtra("idProduto"));
                    id = idProduto;

                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        String nome = bundle.getString("nomeProduto");
                        String desc = bundle.getString("descProduto");
                        String image = bundle.getString("image");

                        Double precoprod = Double.parseDouble(bundle.getString("precProd"));
                        Double descprecoprod = Double.parseDouble(bundle.getString("descPromocao"));

                        // Sentando os valores passado via Intent
                        TextView txtnomeprod = (TextView) findViewById(R.id.txtnomeprod);
                        TextView txtprecoprod = (TextView) findViewById(R.id.txtpreco);
                        TextView txtdescpreco = (TextView) findViewById(R.id.txtprecodesc);
                        TextView txtdescricao = (TextView) findViewById(R.id.txtdescricao);
                        ImageView imgproduto = (ImageView) findViewById(R.id.imgproduto);

                        txtnomeprod.setText(nome);
                        txtdescricao.setText(desc);
                        txtprecoprod.setText(new DecimalFormat("R$ #,##0.00").format(precoprod));
                        txtprecoprod.setPaintFlags(txtprecoprod.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        txtdescpreco.setText(new DecimalFormat("R$ #,##0.00").format(descprecoprod));

                        final byte[] image64 = Base64.decode(image, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
                        imgproduto.setImageBitmap(bitmap);
                    }

                    //NetworkCall myCall = new NetworkCall();
                    //String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";
                    //myCall.execute(url + "/desc/" + id);
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }
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
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;

                // Armazena o valor do id para passar para o carrinho
                id = json.getInt("idProduto");

                nomeProduto = json.getString("nomeProduto");
                descProduto = json.getString("descProduto");
                precProduto = json.getDouble("precProduto");
                descontoPromocao = json.getDouble("descontoPromocao");
                imagem = json.getString("imagem");

                //AQUI ATRIBUIR OS VALORES DO PRODUTO
                TextView txtnomeprod = (TextView) findViewById(R.id.txtnomeprod);
                TextView txtprecoprod = (TextView) findViewById(R.id.txtpreco);
                TextView txtdescpreco = (TextView) findViewById(R.id.txtprecodesc);
                TextView txtdescricao = (TextView) findViewById(R.id.txtdescricao);
                ImageView imgproduto = (ImageView) findViewById(R.id.imgproduto);

                txtnomeprod.setText(nomeProduto);
                txtdescricao.setText(descProduto);
                txtprecoprod.setText(new DecimalFormat("R$ #,##0.00").format(precProduto));
                txtprecoprod.setPaintFlags(txtprecoprod.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                txtdescpreco.setText(new DecimalFormat("R$ #,##0.00").format(descontoPromocao));

                final byte[] image64 = Base64.decode(imagem, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
                imgproduto.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}