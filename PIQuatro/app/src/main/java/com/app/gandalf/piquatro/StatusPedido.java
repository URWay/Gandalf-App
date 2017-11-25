package com.app.gandalf.piquatro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class StatusPedido extends AppCompatActivity {
private ImageView imgstatus1;
private ImageView imgstatus2;
private ImageView imgstatus3;
private TextView txtidpedido;
private TextView txtstatus1;
private TextView txtstatus2;
private TextView txtstatus3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Informações do Pedido");     //Titulo para ser exibido na sua Action Bar em frente à seta


        imgstatus1 = (ImageView) findViewById(R.id.imgstatus1);
        imgstatus2 = (ImageView) findViewById(R.id.imgstatus2);
        imgstatus3 = (ImageView) findViewById(R.id.imgstatus3);

        txtidpedido = (TextView) findViewById(R.id.txtidpedido);

        txtstatus1 = (TextView) findViewById(R.id.txtstatus1);
        txtstatus2 = (TextView) findViewById(R.id.txtstatus2);
        txtstatus3 = (TextView) findViewById(R.id.txtstatus3);
     }
}
