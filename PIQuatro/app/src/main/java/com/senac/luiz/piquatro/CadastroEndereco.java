package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class CadastroEndereco extends AppCompatActivity {
private EditText txtnome;
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
private Spinner spinnercidade;
private Spinner spinneruf;
private Spinner spinnerpais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtnome = (EditText) findViewById(R.id.txtnome);
        txtendereco = (EditText) findViewById(R.id.txtendereco);
        txtnum = (EditText) findViewById(R.id.txtnum);
        txtcep = (EditText) findViewById(R.id.txtcep);
        txtcomplemento = (EditText) findViewById(R.id.txtcomplemento);
        spinnercidade = (Spinner) findViewById(R.id.spinnercidade);
        spinneruf = (Spinner) findViewById(R.id.spinneruf);
        spinnerpais = (Spinner) findViewById(R.id.spinnerpais);


        txtnum.addTextChangedListener(Mask.insert("######", txtnum));
        txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));







        if(txtnome.equals("") || txtendereco.equals("") || txtnum.equals("") || txtcep.equals("") || spinnercidade.equals("Selecione") || spinneruf.equals("Selecione") || spinnerpais.equals("Selecione") ){
            AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroEndereco.this);
            alerta.setTitle("Erro!");
            alerta.setMessage("Preencha os campos corretamente!");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    txtnome.setText(null);
                    txtendereco.setText(null);
                    txtnum.setText(null);
                    txtcep.setText(null);

                }
            });
            AlertDialog dialog = alerta.create();
            dialog.show();
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroEndereco.this);

            alerta.setMessage("Cliente cadastrado com sucesso!");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {


                }
            });
            AlertDialog dialog = alerta.create();
            dialog.show();


        }
    }
}
