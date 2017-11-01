package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.gandalf.piquatro.models.LoginModel;


public class Login extends AppCompatActivity {
    private EditText txtlogin;
    private EditText txtsenha;
    private Button btnok;
    private TextView txtreg;
    private EditText txtfiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtlogin = (EditText) findViewById(R.id.txtlogin);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        btnok = (Button) findViewById(R.id.btnok);
        txtreg = (TextView) findViewById(R.id.txtreg);

        // Login e senha em branco
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Functions f = new Functions();

                if (txtlogin.getText().toString().isEmpty() || txtsenha.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Preencha os campos corretamentes", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    // Comunicação com WS e validação de Login
                    String email = txtlogin.getText().toString().trim();
                    String senha = txtsenha.getText().toString().trim();

                    LoginModel login = new LoginModel(email, senha);
                    Gson g = new Gson();

                    String json = g.toJson(login);

                    // Temporário
                    String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/login";

                    NetworkCall myCall = new NetworkCall();
                    myCall.execute(url, json);
                }
            }
        };

        btnok.setOnClickListener(listener);
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

                JSONObject json = new JSONObject(params[1]);

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

                    // Armazena a sessão
                    String login = json.getString("emailCliente");
                    String password = json.getString("senhaCliente");

                    SharedPreferences prefs = getSharedPreferences("SessionLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("emailCliente", login);
                    editor.putString("senhaCliente", password);
                    editor.apply();

                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);

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
                    f.showDialog("Erro","Usuário ou senha inválidos", Login.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                f.showDialog("Erro","Erro ao obter o resultado", Login.this);
            }
        }
    }
}