package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.gandalf.piquatro.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class descProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        NetworkCall myCall = new NetworkCall();
        // Temporário
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos";
        // Executa a thread, passando null como parâmetro

        // Luiz, o desc é algo fixo para acessar 1 produto especifico, o numero "1" é o ID do produto, deve ser passado
        // pelo botão
        myCall.execute(url + "/desc/1");
    }

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
                int idProduto;
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;


                idProduto = json.getInt("idProduto");
                nomeProduto = json.getString("nomeProduto");
                descProduto = json.getString("descProduto");
                precProduto = json.getDouble("precProduto");
                descontoPromocao = json.getDouble("descontoPromocao");
                imagem = json.getString("imagem");

                //AQUI ATRIBUIR OS VALORES DO PRODUTO
                /* Intent para pegar os dados da ListaProdutos e passar para descProduto
                Intent intent = getIntent();

                if (intent != null){
                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                       String nome = bundle.getString("nomeproduto");
                        Double precoprod = bundle.getDouble("precoprod");
                        Double descprecoprod = bundle.getDouble("descprecoprod");

                        TextView txtnomeprod = (TextView) findViewById(R.id.txtnomeprod);
                        TextView txtprecoprod = (TextView) findViewById(R.id.txtpreco);
                        TextView txtdescpreco = (TextView) findViewById(R.id.txtprecodesc);

                        txtnomeprod.setText(nome);
                        txtprecoprod.setText(precoprod.toString());
                        txtdescpreco.setText(descprecoprod.toString());
                    }
                }
*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}