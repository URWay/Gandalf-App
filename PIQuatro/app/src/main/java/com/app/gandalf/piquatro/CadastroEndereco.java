package com.app.gandalf.piquatro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import com.app.gandalf.piquatro.models.Endereco;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CadastroEndereco extends AppCompatActivity {

private EditText txtnomeendereco;
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
private Button btnEnviarEndereco;
private EditText txtcidade;
private EditText txtuf;
private EditText txtpais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtnomeendereco = (EditText) findViewById(R.id.txtnomeendereco);
        txtendereco = (EditText) findViewById(R.id.txtendereco);
        txtnum = (EditText) findViewById(R.id.txtnum);
        txtcep = (EditText) findViewById(R.id.txtcep);
        txtcomplemento = (EditText) findViewById(R.id.txtcomplemento);
        txtcidade = (EditText) findViewById(R.id.txtcidade);
        txtuf = (EditText) findViewById(R.id.txtuf);
        txtpais = (EditText) findViewById(R.id.txtpais);
        btnEnviarEndereco = (Button) findViewById(R.id.btnEnviarEndereco);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Comunicação com WS e de Endereço
                String endereco = txtnomeendereco.getText().toString().trim();
                String lograudouro = txtendereco.getText().toString().trim();
                int num = Integer.parseInt(txtnum.getText().toString().trim());
                String cep = txtcep.getText().toString().trim();
                String complemento = txtcomplemento.getText().toString().trim();
                String cidade = txtcidade.getText().toString().trim();
                String uf = txtuf.getText().toString().trim();
                String pais = txtpais.getText().toString().trim();

                Endereco end = new Endereco(5, endereco, lograudouro, num, cep, complemento, cidade, uf, pais);
                Gson g = new Gson();

                String json = g.toJson(end);

                String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/endereco";

                CadastroEndereco.NetworkCall myCall = new CadastroEndereco.NetworkCall();
                myCall.execute(url, json);
            }
        };

        btnEnviarEndereco.setOnClickListener(listener);
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
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

                JSONObject json = new JSONObject();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                        line = bufferedReader.readLine();
                    }

                    StringBuilder resultado = new StringBuilder();
                    String linha = bufferedReader.readLine();

                    while (linha != null) {
                        resultado.append(linha);
                        linha = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    Log.d ("tag",sb.toString());

                    return new String ("true : " + responseCode);
                } else {
                    return new String ("false : " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Functions f = new Functions();

            try {
                if(!result.equals("true : 200")){
                    f.showDialog("Erro","Endereço não cadastrado", CadastroEndereco.this);
                } else {
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                f.showDialog("Erro","Erro ao obter o resultado", CadastroEndereco.this);
            }
        }
    }


}
