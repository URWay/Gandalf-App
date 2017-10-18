package com.senac.luiz.piquatro;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.senac.luiz.piquatro.models.LoginModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.support.v7.appcompat.R.styleable.AlertDialog;

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

        // Login e senha em branco
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Functions f = new Functions();

                if (txtlogin.getText().toString().isEmpty() || txtsenha.getText().toString().isEmpty()) {
                    f.showDialog("Erro", "Preencha os campos corretamente!", Login.this);
                } else {

                    // Comunicação com WS e validação de Login
                    String email = txtlogin.getText().toString().trim();
                    String senha = txtsenha.getText().toString().trim();

                    LoginModel login = new LoginModel(email, senha);
                    Gson g = new Gson();

                    Type usuarioType = new TypeToken<LoginModel>() {}.getType();
                    String json = g.toJson(login, usuarioType);

                    // Temporário
                    String url = "http://192.168.0.115:8080/wb/login";

                    NetworkCall myCall = new NetworkCall();
                    //myCall.execute("http://gandalf-ws.azurewebsites.net/PI4/wb/login");
                    myCall.execute(url, json);
                    //f.showDialog("teste","",Login.this);
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

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                StringBuilder result = new StringBuilder();
                result.append("&");

                result.append(URLEncoder.encode(params[1], "UTF-8"));

                String postDataParams = result.toString();

                writer.write(postDataParams);

                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(conn.getInputStream()));

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
                    return sb.toString();

                } else {
                    return new String("false : "+responseCode);
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
                JSONObject json = new JSONObject(result);

                f.showDialog("Teste", result, Login.this);

            } catch (Exception e) {
                e.printStackTrace();
                f.showDialog("Erro","Erro ao obter o resultado", Login.this);
            }
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}
