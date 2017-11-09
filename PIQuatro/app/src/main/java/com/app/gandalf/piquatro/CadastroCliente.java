package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

    private TextWatcher cpfMask;

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

        // Verificar quando for inclusão / alteração / Exclusão
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("M").equals("Modificar")){
                // Carrega as informações de cadastro
                NetworkCallCarregaDados myCall = new NetworkCallCarregaDados();
                SharedPreferences prefs = getSharedPreferences("DadosSuperApp", MODE_PRIVATE);
                int id = prefs.getInt("id", 0);
                myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/cliente/" + id);
            }
        }

        class MaskWatcher implements TextWatcher {
            private boolean isRunning = false;
            private boolean isDeleting = false;
            private final String mask;

            public MaskWatcher(String mask) {
                this.mask = mask;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                isDeleting = count > after;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isRunning || isDeleting) {
                    return;
                }
                isRunning = true;

                int editableLength = editable.length();
                if (editableLength < mask.length()) {
                    if (mask.charAt(editableLength) != '#') {
                        editable.append(mask.charAt(editableLength));
                    } else if (mask.charAt(editableLength-1) != '#') {
                        editable.insert(editableLength-1, mask, editableLength-1, editableLength);
                    }
                }

                isRunning = false;
            }
        }

        // Mascára
        txtcpf.addTextChangedListener(Mask.insert("###.###.###-##", txtcpf));
        txtcelular.addTextChangedListener(Mask.insert("(##)#####-####", txtcelular));
        txtresidencial.addTextChangedListener(Mask.insert("(##)####-####", txtresidencial));
        txtnasc.addTextChangedListener(Mask.insert("(##)####-####", txtnasc));

        txtcpf.addTextChangedListener(new MaskWatcher("###.###.###-##"));
        txttelefone.addTextChangedListener(new MaskWatcher("####-####"));
        txtcomercial.addTextChangedListener(new MaskWatcher("####-####"));
        txtresidencial.addTextChangedListener(new MaskWatcher("####-####"));
        txtcelular.addTextChangedListener(new MaskWatcher("(##) # ####-####"));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validações
                Functions f = new Functions();

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
                    Toast toast = Toast.makeText(getApplicationContext(), "Os campos com * são obrigatórios!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    if(f.isCPF(cpf) == true && f.isValidEmail(email) == true){
                        findViewById(R.id.loadingLogin).setVisibility(View.VISIBLE);
                        RelativeLayout relative = (RelativeLayout) findViewById(R.id.activity_cadastro_cliente);
                        relative.setBackgroundResource(0);

                        ClienteModel cliente = new ClienteModel(0, nome, email, senha, cpf, celular, comercial, residencial, nasc, knews );
                        Intent intent = getIntent();
                        if(intent != null){
                            // Atualização do cadastro
                            if(intent.getStringExtra("M").equals("Modificar")){
                                Cadastro(cliente, "atualizar");
                            }else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Erro na aplicação corrija...", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else {
                            // Inserir dados de cadastro
                            if (intent.getStringExtra("A").equals("Inclusao")){
                                Cadastro(cliente, "inserir");
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Erro na aplicação corrija...", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    } else {
                        if(f.isValidEmail(email) == false){
                            Toast toast = Toast.makeText(getApplicationContext(), "E-mail inválido", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        if(f.isCPF(cpf) == false){
                            Toast toast = Toast.makeText(getApplicationContext(), "CPF inválido", Toast.LENGTH_SHORT);
                            toast.show();
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

        NetworkCall myCall = new NetworkCall();
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

            try {

            } catch (Exception e) {
                e.printStackTrace();
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
                String cpf = json.getString("CPFCliente");
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
}


