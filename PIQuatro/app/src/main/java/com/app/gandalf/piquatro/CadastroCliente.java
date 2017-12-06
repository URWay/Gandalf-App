package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.ClienteModel;
import com.app.gandalf.piquatro.models.LoginModel;
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

public class CadastroCliente extends AppCompatActivity {

    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private EditText txtcpf;
    private EditText txttelefone;
    private EditText txtcelular;
    private EditText txtcomercial;
    private EditText txtresidencial;
    private EditText txtnasc;
    private CheckBox checknews;
    private Button btnok;
    private TextView textView16;
    private TextWatcher cpfMask;
    private Functions f = new Functions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        txtnome = (EditText) findViewById(R.id.txtnomeapelido);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtcpf = (EditText) findViewById(R.id.txtcpf);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtcelular = (EditText) findViewById(R.id.txtcelular);
        txtcomercial = (EditText) findViewById(R.id.txtcomercial);
        txtresidencial = (EditText) findViewById(R.id.txtres);
        txtnasc = (EditText) findViewById(R.id.txtnasc);
        checknews = (CheckBox) findViewById(R.id.checknews);
        btnok = (Button) findViewById(R.id.btnok);

        Intent mBundle = getIntent();
        if(mBundle != null) {
            String acao = mBundle.getStringExtra("ACAO");

            if (acao.equals("M")) {
                // Carrega as informações de cadastro
                NetworkCallCarregaDados myCall = new NetworkCallCarregaDados();
                myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/cliente/" + f.getId(this));
                btnok.setText("Atualizar cadastro");
            }
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastro de Cliente");


        // Mascára
        txtcpf.addTextChangedListener(Mask.insert("###.###.###-##", txtcpf));
        txtcelular.addTextChangedListener(Mask.insert("(##)#####-####", txtcelular));
        txtresidencial.addTextChangedListener(Mask.insert("(##)####-####", txtresidencial));
        txtnasc.addTextChangedListener(Mask.insert("####/##/##", txtnasc));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validações
                String nome = txtnome.getText().toString().trim();
                String email = txtemail.getText().toString();
                String senha = txtsenha.getText().toString();
                String cpf = txtcpf.getText().toString();
                String celular = txtcelular.getText().toString();
                String comercial = txtcomercial.getText().toString();
                String residencial = txtresidencial.getText().toString();
                String nasc = txtnasc.getText().toString();

                int knews = 0;
                if(checknews.isEnabled()) {
                    knews = 1;
                } else {
                    knews = 0;
                }

                if (nome.equals("") || email.equals("") || senha.equals("") || cpf.equals("") || celular.equals("")) {
                    Toast.makeText(CadastroCliente.this, "Os campos com * são obrigatórios!", Toast.LENGTH_SHORT).show();
                } else {

                    if(f.isCPF(cpf) == true && f.isValidEmail(email) == true){
                        Intent mBundle = getIntent();
                        String acao = "";
                        if(mBundle != null) {
                            acao = mBundle.getStringExtra("ACAO");
                        }

                        ClienteModel cliente = new ClienteModel(f.getId(CadastroCliente.this), nome, email, senha, cpf, celular, comercial, residencial, nasc, knews );

                        if (acao.equals("A")){
                            // Inserir dados de cadastro
                            Cadastro(cliente, "inserir");
                        }
                        else if(acao.equals("M")){
                            // Atualização do cadastro
                            Cadastro(cliente, "atualizar");
                        }else {
                            Toast.makeText(CadastroCliente.this, "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if(f.isValidEmail(email) == false){
                            Toast.makeText(CadastroCliente.this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                        }

                        if(f.isCPF(cpf) == false){
                            Toast.makeText(CadastroCliente.this, "CPF inválido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        };

        btnok.setOnClickListener(listener);
    }

    public void Cadastro(ClienteModel cliente, String acao){

        Gson g = new Gson();
        String json = g.toJson(cliente);

        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/cliente/" + acao;
        String method = null;

        if(acao.equals("atualizar")){
            method = "PUT";
        } else if (acao.equals("inserir")){

                method = "POST";
        }

        NetworkCall myCall = new NetworkCall();

        Toast toast = Toast.makeText(CadastroCliente.this,"Cadastrando...",Toast.LENGTH_LONG);
        toast.show();

        myCall.execute(url, json, method);
    }

    public void LoginCadastro(String email, String senha){
        LoginModel login = new LoginModel(email, senha, 0);
        Gson g = new Gson();

        String json = g.toJson(login);
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/login";

        NetworkCallLogin myCall = new NetworkCallLogin();

        myCall.execute(url, json);
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

                    StringBuilder resultado = new StringBuilder();
                    String linha = bufferedReader.readLine();

                    while (linha != null) {
                        resultado.append(linha);
                        linha = bufferedReader.readLine();
                    }

                    bufferedReader.close();
                    Log.d ("tag",sb.toString());

                    // Login
                    if(params[2].equals("POST")){
                        JSONObject json = new JSONObject(sb.toString());
                        String email = json.getString("emailCliente");
                        String senha = json.getString("senhaCliente");
                        LoginCadastro(email, senha);
                    }

                    return new String (params[2]);
                } else {
                    return new String ("false");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result.equals("PUT")){
                Toast.makeText(CadastroCliente.this, "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
            } else if (result.equals("false")) {
                f.showDialog("Falha na atualização", "Não foi possível atualizar o cadastro, por favor, verifique as informação de cadastro se estão corretas!", CadastroCliente.this);
            } else {
                f.showDialog("Erro","Erro ao obter o resultado", CadastroCliente.this);
            }
        }
    }

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

                String nome = json.getString("nomeCompletoCliente");
                String email = json.getString("emailCliente");
                String senha = json.getString("senhaCliente");
                String cpf = json.getString("cpfcliente");
                String celular = json.getString("celularCliente");
                String telefone = json.getString("telComercialCliente");
                String residencial = json.getString("telResidencialCliente");
                String nasc = json.getString("dtNascCliente");
                int news = json.getInt("recebeNewsLetter");

                if (!nome.equals("")) {
                    txtnome.setText(nome);
                }
                if (!email.equals("")) {
                    txtemail.setText(email);
                }
                if (!senha.equals("")) {
                    txtsenha.setText(senha);
                }
                if (!cpf.equals("")) {
                    txtcpf.setText(cpf);
                }
                if (!celular.equals("")) {
                    txtcelular.setText(celular);
                }
                if (!telefone.equals("")) {
                    txttelefone.setText(telefone);
                }
                if (!residencial.equals("")) {
                    txtresidencial.setText(residencial);
                }
                if (!nasc.equals("")) {
                    txtnasc.setText(nasc);
                }
                if (news == 1) {
                    checknews.setChecked(true);
                } else {
                    checknews.setChecked(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // Realiza o Login
    public class NetworkCallLogin extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return f.Login(CadastroCliente.this, params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if(!result.equals("200")){

                    Toast.makeText(CadastroCliente.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadastroCliente.this, NewIndex.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
                f.showDialog("Erro","Erro ao obter o resultado", CadastroCliente.this);
            }
        }
    }

}
