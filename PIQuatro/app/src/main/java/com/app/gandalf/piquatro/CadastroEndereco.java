package com.app.gandalf.piquatro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.Endereco;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class CadastroEndereco extends AppCompatActivity {

private EditText txtnomeendereco;
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
private Button btnEnviarDados;
private Button btnbuscar;
private EditText txtcidade;
private EditText txtuf;
private EditText txtpais;
private Spinner spinnerUF;
private String[] arrayCep = {"", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO", "ZZ" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtnomeendereco = (EditText) findViewById(R.id.txtnomeendereco);
        txtendereco = (EditText) findViewById(R.id.txtendereco);
        txtnum = (EditText) findViewById(R.id.txtnumero);
        txtcep = (EditText) findViewById(R.id.txtcep);
        txtcomplemento = (EditText) findViewById(R.id.txtcomplemento);
        txtcidade = (EditText) findViewById(R.id.txtCidade);
        txtuf = (EditText) findViewById(R.id.txtuf);
        txtpais = (EditText) findViewById(R.id.txtpais);
        btnEnviarDados = (Button) findViewById(R.id.btnEnviarDados);
        btnbuscar = (Button) findViewById(R.id.btnbuscar);

        txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));

        // Spinner de UF
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayCep);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerUF);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


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

                Endereco end = new Endereco(5, endereco, lograudouro, num, cep, complemento, cidade, pais, uf);
                Gson g = new Gson();

                String json = g.toJson(end);
                String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/endereco";

                NetworkCall myCall = new NetworkCall();
                myCall.execute(url, json);
            }
        };

        View.OnClickListener listenerBuscar = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vcep = txtcep.getText().toString();

                if(vcep.isEmpty() || vcep.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "CEP inválido", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    EnderecoNetworkCall myCallEndereco = new EnderecoNetworkCall();
                    myCallEndereco.execute("https://viacep.com.br/ws/"+vcep+"/json/");
                }
            }
        };

        btnbuscar.setOnClickListener(listenerBuscar);
        btnEnviarDados.setOnClickListener(listener);
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

    public class EnderecoNetworkCall extends AsyncTask<String, Void, String>{
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
                String cep = json.getString("cep");
                String logradouro = json.getString("logradouro");
                String complemento = json.getString("complemento");
                String cidade = json.getString("localidade");
                String uf = json.getString("uf");

                // Setando valores do WebService
                txtcep.setText(cep);
                txtendereco.setText(logradouro);
                txtcomplemento.setText(complemento);
                txtcidade.setText(cidade);

                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CadastroEndereco.this, android.R.layout.simple_spinner_item, arrayCep);
                Spinner spinner = (Spinner) findViewById(R.id.spinnerUF);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if (!uf.equals(null)) {
                    if(Arrays.asList(arrayCep).contains(uf)){
                        int spinnerPosition = spinnerArrayAdapter.getPosition(uf);
                        spinner.setSelection(spinnerPosition);
                    } else {
                        int spinnerPosition = spinnerArrayAdapter.getPosition("ZZ");
                        spinner.setSelection(spinnerPosition);
                    }
                }

                Toast toast = Toast.makeText(getApplicationContext(), "CEP carregado", Toast.LENGTH_SHORT);
                toast.show();

            } catch (Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "CEP não encontrado", Toast.LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            }
        }
    }

}
