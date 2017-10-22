package com.senac.luiz.piquatro.models;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senac.luiz.piquatro.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Produtos extends AppCompatActivity {
    private ViewGroup produto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);


        NetworkCall myCall = new NetworkCall();
        // Temporário
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";
        // Executa a thread, passando null como parâmetro

        // Luiz, o valor de ap tem que ser o ID inicial dos produtos (Ex: 2 retorna os 15 primeiros produtos acima do ID 2, será utilziado para paginação)
        //desc diz qual a ordem é decrescente ou n
        //O primeiro parametro no path é o id da categoria (vamos ter q pegar ele do menu)
        //O segundo é a ordem (usado para filtros)
        //Para pesquisa será utilizado o parametro via get = pesq
        myCall.execute(url+"/1/idProduto?ap=0&desc=1");
    }

    // Implementa o AsynkTask para criar uma thread
    public class NetworkCall extends AsyncTask<String, Void, String> {

        // Esse é o método que executa a tarefa em segundo plano
        @Override
        protected String doInBackground(String... params) {
            try {
                // Cria o objeto de conexão
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();

                // Executa a requisição pegando os dados
                InputStream in = urlConnection.getInputStream();

                // Cria um leitor para ler a resposta
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuilder resultado = new StringBuilder();
                String linha = bufferedReader.readLine();

                // Lê linha a linha a resposta e armazena no StringBuilder
                while (linha != null) {
                    resultado.append(linha);
                    linha = bufferedReader.readLine();
                }

                // Transforma o StringBuilder em String, que contém a resposta final
                String respostaCompleta = resultado.toString();

                // Retorna a string final contendo a resposta retornada
                return respostaCompleta;

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Caso tenha dado algum erro, retorna null
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                // Cria um objeto JSON a partir da resposta
                JSONObject json = new JSONObject(result);

                //Objeto do layout
                produto = (ViewGroup) findViewById(R.id.container);
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


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addItem(int idProduto, String nomeProd, String descProd, double precProd, double descPromocao, String img) {
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.produto, produto, false);
        TextView nome = (TextView) cardView.findViewById(R.id.nomeProduto);
        TextView desc = (TextView) cardView.findViewById(R.id.descProduto);
        TextView prec = (TextView) cardView.findViewById(R.id.precProduto);
        TextView promo = (TextView) cardView.findViewById(R.id.descontoPromocao);
        nome.setText(nomeProd);
        desc.setText(descProd);
        prec.setText(String.valueOf(precProd));
        promo.setText(String.valueOf(descPromocao));

        produto.addView(cardView);
    }
}

