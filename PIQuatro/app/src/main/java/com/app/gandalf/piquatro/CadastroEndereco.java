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
private Spinner spinnercidade;
private Spinner spinnerpais;
private Spinner spinneruf;
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
        spinnercidade = (Spinner) findViewById(R.id.spinnercidade);
        spinnerpais = (Spinner) findViewById(R.id.spinnerpais);
        spinneruf = (Spinner) findViewById(R.id.spinneruf);
        btnok = (Button) findViewById(R.id.btnok);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtnomeapelido.equals("") || txtendereco.equals("") || txtnum.equals("") ||
                    txtcep.equals("") || spinnercidade != null && spinnercidade.getSelectedItem() != null ||
                    spinnerpais != null && spinnerpais.getSelectedItem() != null ||
                    spinneruf != null && spinneruf.getSelectedItem() != null){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroEndereco.this);
                    alerta.setTitle("Erro!");
                    alerta.setMessage("Os campos com * são obrigatórios!");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            txtnomeapelido.setText("");
                            txtendereco.setText("");
                            txtnum.setText("");
                            txtcep.setText("");

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();

                }
            }

        });

        txtcidade = (EditText) findViewById(R.id.txtcidade);
        txtuf = (EditText) findViewById(R.id.txtuf);
        txtpais = (EditText) findViewById(R.id.txtpais);


        txtnum.addTextChangedListener(Mask.insert("######", txtnum));
        txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));




        if(txtnomeapelido.equals("") || txtendereco.equals("") || txtnum.equals("") || txtcep.equals("") || txtcidade.equals("Selecione") || txtuf.equals("Selecione") || txtpais.equals("Selecione") ){
            AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroEndereco.this);
            alerta.setTitle("Erro!");
            alerta.setMessage("Preencha os campos corretamente!");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    txtnomeapelido.setText(null);
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
