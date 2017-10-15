package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class CadastroEnderecoCEP extends AppCompatActivity {
    private EditText txtcep;
    private TextView txtteste;
    private Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco_cep);
        txtcep = (EditText) findViewById(R.id.txtcep);
        btnok = (Button) findViewById(R.id.btnok);
        txtteste = (TextView) findViewById(R.id.txtteste);


       txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));


       btnok.setOnClickListener(new View.OnClickListener() {


           @Override
           public void onClick(View view) {
               Integer vcep = null;
               try{
                   vcep = Integer.parseInt(txtcep.getText().toString());
               }catch(Exception e){

               }
               // Cria um NetworkCall, que estende de AsynkTask (abaixo)
               NetworkCall myCall = new NetworkCall();
               // Executa a thread, passando null como parâmetro
               myCall.execute("https://www.viacep.com.br/ws/"+vcep+"/json/");

           }
       });

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

                // Pega um dado do JSON
                String cep = json.getString("cep");

                txtteste.setText(cep);


            } catch (Exception e) {
                e.printStackTrace();



             }
        }
    }
}