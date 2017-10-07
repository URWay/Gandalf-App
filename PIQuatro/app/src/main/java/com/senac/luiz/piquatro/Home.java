package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;


public class Home extends AppCompatActivity {
private EditText etxtcarrinho;
private EditText etxtpedidos;
private EditText etxtconta;
private TextView txtfiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        // INTENTS

        // Home/Carrinho
        etxtcarrinho = (EditText) findViewById(R.id.etxtcarrinho);
        etxtcarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home.this, Carrinho.class);
                startActivity(intent);

            }
        });

        //Home/Pedidos
        etxtpedidos = (EditText) findViewById(R.id.etxtpedidos);
        etxtpedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*
                Intent intent = new Intent(Home.this, Pedidos.class);
                startActivity(intent);
            */

         etxtconta = (EditText) findViewById(R.id.etxtconta);
         etxtconta.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                   /*
                Intent intent = new Intent(Home.this, Conta.class);
                startActivity(intent);
            */
             }
         });


            }
        });
/*
      // FIM INTENTS
        txtfiltro = (TextView) findViewById(R.id.txtfiltro);
        txtfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(LoginReg.this);
                alerta.setTitle("Erro!");
                alerta.setMessage("");

                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                      
                    }
                });
                AlertDialog dialog = alerta.create();
                dialog.show();
            }
        });
*/


    }

}
