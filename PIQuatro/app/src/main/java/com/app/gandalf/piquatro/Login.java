package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.LoginModel;
import com.google.gson.Gson;


public class Login extends AppCompatActivity {
    private EditText txtlogin;
    private EditText txtsenha;
    private Button btnok;
    private TextView txtreg;
    private Functions f = new Functions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtlogin = (EditText) findViewById(R.id.txtlogin);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        btnok = (Button) findViewById(R.id.btnok);
        txtreg = (TextView) findViewById(R.id.txtreg);
        findViewById(R.id.loadingL).setVisibility(View.GONE);

        //ActionBar ab = getSupportActionBar();
        //ab.setDisplayHomeAsUpEnabled(true);

        // Login e senha em branco
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (txtlogin.getText().toString().isEmpty() || txtsenha.getText().toString().isEmpty()) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Preencha os campos corretamentes", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    findViewById(R.id.loadingL).setVisibility(View.VISIBLE);
                    // Comunicação com WS e validação de Login
                    String email = txtlogin.getText().toString().trim();
                    String senha = txtsenha.getText().toString().trim();

                    // Login
                    LoginCliente(email, senha);
                }
            }
        };

        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CadastroCliente.class);
                intent.putExtra("ACAO", "A");
                startActivity(intent);
            }
        });

        btnok.setOnClickListener(listener);


    }



    public void LoginCliente(String email, String senha){
        LoginModel login = new LoginModel(email, senha, 0);
        Gson g = new Gson();

        String json = g.toJson(login);
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/login";

        NetworkCall myCall = new NetworkCall();
        myCall.execute(url, json);
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return f.Login(Login.this, params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if(!result.equals("200")){
                    findViewById(R.id.loadingL).setVisibility(View.GONE);
                    f.showDialog("Falha no login!","Usuário ou senha inválidos", Login.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                findViewById(R.id.loadingL).setVisibility(View.GONE);
                f.showDialog("Erro","Erro ao obter o resultado", Login.this);
            }
        }
    }
}