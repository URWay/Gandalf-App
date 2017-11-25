package com.app.gandalf.piquatro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class descPedido extends AppCompatActivity {
private TextView idpedido;
private ListView listaitens;
private TextView txtnome;
private TextView txtendereco;
private TextView txtnum;
private TextView txtcep;
private TextView txtcidade;
private TextView txtuf;
private TextView txtcomplemento;
private TextView txtpagamento;
private TextView txttotal;
private Button btnvolta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_pedido);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Pedido" +idpedido.getText());     //Titulo para ser exibido na sua Action Bar em frente à seta

        idpedido = (TextView) findViewById(R.id.idpedido);
        listaitens = (ListView) findViewById(R.id.listaitens);
        txtnome = (TextView) findViewById(R.id.txtnome);
        txtendereco = (TextView) findViewById(R.id.txtendereco);
        txtnum = (TextView) findViewById(R.id.txtnum);
        txtcep = (TextView) findViewById(R.id.txtcep);
        txtcidade = (TextView) findViewById(R.id.txtcidade);
        txtuf = (TextView) findViewById(R.id.txtuf);
        txtcomplemento = (TextView) findViewById(R.id.txtcomplemento);
        txtpagamento = (TextView) findViewById(R.id.txtpagamento);
        txttotal = (TextView) findViewById(R.id.txttotal);
        btnvolta = (Button) findViewById(R.id.btnvolta);


    }
}
