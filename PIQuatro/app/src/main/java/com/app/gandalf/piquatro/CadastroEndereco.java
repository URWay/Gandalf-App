package com.app.gandalf.piquatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;



public class CadastroEndereco extends AppCompatActivity {

private EditText txtnomeapelido;
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
//private Spinner spinnercidade;
//private Spinner spinnerpais;
//private Spinner spinneruf;
private Button btnok;
private EditText txtcidade;
private EditText txtuf;
private EditText txtpais;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);


        txtnomeapelido = (EditText) findViewById(R.id.txtnomeapelido);

        txtendereco = (EditText) findViewById(R.id.txtendereco);
        txtnum = (EditText) findViewById(R.id.txtnum);
        txtcep = (EditText) findViewById(R.id.txtcep);
        txtcomplemento = (EditText) findViewById(R.id.txtcomplemento);

        btnok = (Button) findViewById(R.id.btnok);
        txtcidade = (EditText) findViewById(R.id.txtcidade);
        txtuf = (EditText) findViewById(R.id.txtuf);
        txtpais = (EditText) findViewById(R.id.txtpais);


        //txtnum.addTextChangedListener(Mask.insert("######", txtnum));
        //txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));




    }

}
