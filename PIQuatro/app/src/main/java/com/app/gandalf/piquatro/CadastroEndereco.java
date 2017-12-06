package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import javax.net.ssl.HttpsURLConnection;

public class CadastroEndereco extends AppCompatActivity {

private EditText txtnomeendereco;
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
private Button btnEnviarDados;
private Button btnbuscar;
//
private EditText txtcidade;
private EditText txtpais;
private TextView textView5;
private EditText txtuf;
private Functions f = new Functions();

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
        txtpais = (EditText) findViewById(R.id.txtpais);
        btnEnviarDados = (Button) findViewById(R.id.btnEnviarDados);
        btnbuscar = (Button) findViewById(R.id.btnbuscar);
        txtuf = (EditText) findViewById(R.id.txtuf);

        txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytool);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastro de Endereço");

        // Verificar quando for inclusão / alteração / Exclusão
        Intent intent = getIntent();
        if(intent != null){
            try{
                if(intent.getStringExtra("ACAO").equals("M")) {
                    // Carrega as informações de cadastro
                    NetworkCallCarregaDados myCall = new NetworkCallCarregaDados();
                    int id = Integer.parseInt(intent.getStringExtra("idEndereco"));
                    Toast toast = Toast.makeText(CadastroEndereco.this,"Consultando...",Toast.LENGTH_LONG);
                    toast.show();
                    myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/" + id);
                    textView5.setText("Atualizar cadastro");
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }

        // Incluí / Atualiza o Cadastro de Endereço
        btnEnviarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comunicação com WS e de Endereço
                String endereco = txtnomeendereco.getText().toString().trim();
                String lograudouro = txtendereco.getText().toString().trim();
                String num = txtnum.getText().toString().trim();
                String cep = txtcep.getText().toString().trim();
                String complemento = txtcomplemento.getText().toString().trim();
                String cidade = txtcidade.getText().toString().trim();
                String pais = txtpais.getText().toString().trim();
                String uf = txtuf.getText().toString();
                int id = f.getId(CadastroEndereco.this);

                int idEndereco = 0;

                Intent intent = getIntent();
                if (intent != null) {
                    if (!intent.getStringExtra("idEndereco").equals("0")) {
                        idEndereco = Integer.parseInt(intent.getStringExtra("idEndereco"));
                    }
                }

                Endereco end = new Endereco(id, idEndereco, endereco, lograudouro, Integer.parseInt(num), cep, complemento, cidade, pais, uf);
                Gson g = new Gson();

                String json = g.toJson(end);
                String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/endereco";

                NetworkCall myCall = new NetworkCall();

                if (idEndereco == 0){
                    Toast toast = Toast.makeText(CadastroEndereco.this,"Cadastrando...",Toast.LENGTH_LONG);
                    toast.show();
                    myCall.execute(url, json, "POST");
                }else {
                    Toast toast = Toast.makeText(CadastroEndereco.this,"Atualizando...",Toast.LENGTH_LONG);
                    toast.show();
                    myCall.execute(url, json, "PUT");
                }
            }
        });

        // Busca o endereço pelo CEP e preenche os campos de acordo com o retorno
        btnbuscar.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    // Cadastro / Atualiazação Endereço
    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(params[2]);
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

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

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

            Intent i = getIntent();
            Intent intent = new Intent(CadastroEndereco.this, Enderecos.class);

            try {
                if(!result.equals("true : 200")){
                    if(i.getStringExtra("ACAO").equals("A")){
                        intent.putExtra("Retorno", "A.F");
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    } else if(i.getStringExtra("ACAO").equals("M")) {
                        intent.putExtra("Retorno", "M.F");
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    } else {
                        finish();
                    }
                } else {

                    if(i != null){
                        try{
                            if(i.getStringExtra("ACAO").equals("A")) {
                                // Inclusão
                                intent.putExtra("Retorno", "A");
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                // Atualização
                                intent.putExtra("Retorno", "M");
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        } catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // WEBSERVICE CEP
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
                txtuf.setText(uf);

                Toast.makeText(getApplicationContext(), "CEP carregado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "CEP não encontrado", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    // WEBSERVICE carregar informação de endereço
    public class NetworkCallCarregaDados extends AsyncTask<String, Void, String> {

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

                return resultado.toString();

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

                String nomeEndereco = json.getString("nomeEndereco");
                String logradouroEndereco = json.getString("logradouroEndereco");
                String numeroEndereco = json.getString("numeroEndereco");
                String CEPEndereco = json.getString("cependereco");
                String complementoEndereco = json.getString("complementoEndereco");
                String cidadeEndereco = json.getString("cidadeEndereco");
                String paisEndereco = json.getString("paisEndereo");
                String UFEndereco = json.getString("ufendereco");

                if (!nomeEndereco.equals("")) {
                    txtnomeendereco.setText(nomeEndereco);
                }
                if (!logradouroEndereco.equals("")) {
                    txtendereco.setText(logradouroEndereco);
                }
                if (!numeroEndereco.equals("")) {
                    txtnum.setText(numeroEndereco);
                }
                if (!CEPEndereco.equals("")) {
                    txtcep.setText(CEPEndereco);
                }
                if (!complementoEndereco.equals("")) {
                    txtcomplemento.setText(complementoEndereco);
                }
                if (!cidadeEndereco.equals("")) {
                    txtcidade.setText(cidadeEndereco);
                }
                if (! paisEndereco.equals("")) {
                    txtpais.setText( paisEndereco);
                }
                if (! UFEndereco.equals("")) {
                    txtuf.setText( UFEndereco);
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Erro ao carregar as informações de endereço", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

}
