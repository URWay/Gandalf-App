package com.senac.luiz.piquatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
=======
>>>>>>> 7b8775513acac7fabc96dafb7b10b7a86bae09de
import android.widget.EditText;
import android.widget.Spinner;

public class CadastroEndereco extends AppCompatActivity {
<<<<<<< HEAD
private EditText txtnomeapelido;
=======
private EditText txtnome;
>>>>>>> 7b8775513acac7fabc96dafb7b10b7a86bae09de
private EditText txtendereco;
private EditText txtnum;
private EditText txtcep;
private EditText txtcomplemento;
<<<<<<< HEAD
private Spinner spinnercidade;
private Spinner spinnerpais;
private Spinner spinneruf;
    private Button btnok;
=======
private EditText txtcidade;
private EditText txtuf;
private EditText txtpais;

>>>>>>> 7b8775513acac7fabc96dafb7b10b7a86bae09de

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

<<<<<<< HEAD
        txtnomeapelido = (EditText) findViewById(R.id.txtnomeapelido);
=======
        txtnome = (EditText) findViewById(R.id.txtnome);
>>>>>>> 7b8775513acac7fabc96dafb7b10b7a86bae09de
        txtendereco = (EditText) findViewById(R.id.txtendereco);
        txtnum = (EditText) findViewById(R.id.txtnum);
        txtcep = (EditText) findViewById(R.id.txtcep);
        txtcomplemento = (EditText) findViewById(R.id.txtcomplemento);
<<<<<<< HEAD
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


=======
        txtcidade = (EditText) findViewById(R.id.txtcidade);
        txtuf = (EditText) findViewById(R.id.txtuf);
        txtpais = (EditText) findViewById(R.id.txtpais);


        txtnum.addTextChangedListener(Mask.insert("######", txtnum));
        txtcep.addTextChangedListener(Mask.insert("#####-###", txtcep));




        if(txtnome.equals("") || txtendereco.equals("") || txtnum.equals("") || txtcep.equals("") || txtcidade.equals("Selecione") || txtuf.equals("Selecione") || txtpais.equals("Selecione") ){
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
>>>>>>> 7b8775513acac7fabc96dafb7b10b7a86bae09de
    }
}
