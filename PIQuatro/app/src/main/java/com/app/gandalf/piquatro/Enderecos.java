package com.app.gandalf.piquatro;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.gandalf.piquatro.models.Endereco;

import java.util.ArrayList;
import java.util.List;

public class Enderecos extends AppCompatActivity {
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adaptador;

        lista = (ListView) findViewById(R.id.lista);

        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Endereço 1");
        opcoes.add("Endereço 2");
        opcoes.add("Endereço 3");


        adaptador = new ArrayAdapter<String>(Enderecos.this, android.R.layout.simple_list_item_1, opcoes);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: ;
                        break;
                    case 1: ;
                        break;
                    case 2:;
                        break;
                    case 3: ;
                        break;
                    default:finish();
                }
            }
        });
    }

    private void minhaconta() {
        Intent it = new Intent(Enderecos.this,Home.class);
        startActivity(it);
    }

    private void meusenderecos() {
        Intent it = new Intent(Enderecos.this, Home.class);
        startActivity(it);
    }

    private void sobre() {
        Intent it = new Intent(Enderecos.this, Home.class);
        startActivity(it);
    }

}

