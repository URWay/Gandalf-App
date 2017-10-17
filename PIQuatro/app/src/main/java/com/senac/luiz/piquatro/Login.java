package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtlogin.getText().length() ==0|| txtlogin.equals("")||
                    txtsenha.getText().length() ==0 || txtsenha.equals("")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setTitle("Erro!");
                    alerta.setMessage("Preencha os campos corretamente!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();

                }
            }
        });



        // Intent Login/Registrar
        txtreg = (TextView) findViewById(R.id.txtreg);
        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, LoginReg.class);
                startActivity(intent);

            }
        });
/*
        txtfiltro = (EditText) findViewById(R.id.txtfiltro);
        txtfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/

    }
}
