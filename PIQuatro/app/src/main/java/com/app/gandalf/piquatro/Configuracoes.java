package com.app.gandalf.piquatro;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class Configuracoes extends AppCompatActivity {
private ListView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        ArrayAdapter<String> adaptador;

        lista = (ListView) findViewById(R.id.lista);

        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Minha conta");
        opcoes.add("Meus endereços");
        opcoes.add("Sobre");


        adaptador = new ArrayAdapter<String>(Configuracoes.this, android.R.layout.simple_list_item_1, opcoes);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: minhaconta();
                        break;
                    case 1: meusenderecos();
                        break;
                    case 2: sobre();
                        break;

                    case 4 :;
                        break;
                    case 3: finish();
                        break;
                }
            }
        });
    }

    private void minhaconta() {
        Intent it = new Intent(Configuracoes.this,Home.class);
        startActivity(it);
    }

    private void meusenderecos() {
        Intent it = new Intent(Configuracoes.this, Home.class);
        startActivity(it);
    }

    private void sobre() {
        Intent it = new Intent(Configuracoes.this, Home.class);
        startActivity(it);
    }

}
