package com.app.gandalf.piquatro;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;


public class DadosCadastrais extends AppCompatActivity {
private TextView txtemail;
private ImageView imgcfg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_cadastrais);

        txtemail = (TextView) findViewById(R.id.txtemail);



        imgcfg = (ImageView) findViewById(R.id.imgcfg);
        imgcfg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DadosCadastrais.this, Configuracoes.class);
                startActivity(intent);
            }
        });



    }
}
