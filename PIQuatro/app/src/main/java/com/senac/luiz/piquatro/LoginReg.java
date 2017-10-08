package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginReg extends AppCompatActivity {
private EditText txtlogin;
private EditText txtsenha;
private EditText txtemail;
private Button btnok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

            txtlogin = (EditText)findViewById(R.id.txtlogin);
            txtsenha = (EditText)findViewById(R.id.txtsenha);
            txtemail = (EditText)findViewById(R.id.txtemail);
            btnok = (Button)findViewById(R.id.btnok);


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtlogin.equals("") || txtsenha.equals("") || txtemail.equals("") ){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginReg.this);
                    alerta.setTitle("Erro!");
                    alerta.setMessage("Preencha os campos corretamente!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            txtlogin.setText(null);
                            txtsenha.setText(null);
                            txtemail.setText(null);

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();

                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginReg.this);

                    alerta.setMessage("Cliente cadastrado com sucesso! (:");
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
    }
}
