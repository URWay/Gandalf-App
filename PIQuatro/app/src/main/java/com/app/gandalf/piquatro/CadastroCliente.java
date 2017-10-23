package com.app.gandalf.piquatro;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class CadastroCliente extends AppCompatActivity {
    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private EditText txtcpf;
    private EditText txttelefone;
    private EditText txtcelular;
    private EditText txtcomercial;
    private EditText txtresidencial;
    private EditText txtnasc;
    private CheckBox checknews;
    private Button btnok;

    private TextWatcher cpfMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        txtnome = (EditText) findViewById(R.id.txtnomeapelido);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtcpf = (EditText) findViewById(R.id.txtcpf);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtcelular = (EditText) findViewById(R.id.txtcelular);
        txtcomercial = (EditText) findViewById(R.id.txtcomercial);
        txtresidencial = (EditText) findViewById(R.id.txtres);
        txtnasc = (EditText) findViewById(R.id.txtnasc);
        checknews = (CheckBox) findViewById(R.id.checknews);
        btnok = (Button) findViewById(R.id.btnok);

        class MaskWatcher implements TextWatcher {
            private boolean isRunning = false;
            private boolean isDeleting = false;
            private final String mask;

            public MaskWatcher(String mask) {
                this.mask = mask;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                isDeleting = count > after;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isRunning || isDeleting) {
                    return;
                }
                isRunning = true;

                int editableLength = editable.length();
                if (editableLength < mask.length()) {
                    if (mask.charAt(editableLength) != '#') {
                        editable.append(mask.charAt(editableLength));
                    } else if (mask.charAt(editableLength-1) != '#') {
                        editable.insert(editableLength-1, mask, editableLength-1, editableLength);
                    }
                }

                isRunning = false;
            }
        }



        txtcpf.addTextChangedListener(new MaskWatcher("###.###.###-##"));
        txttelefone.addTextChangedListener(new MaskWatcher("####-####"));
        txtcomercial.addTextChangedListener(new MaskWatcher("####-####"));
        txtresidencial.addTextChangedListener(new MaskWatcher("####-####"));
        txtcelular.addTextChangedListener(new MaskWatcher("(##) # ####-####"));

        txtcpf.addTextChangedListener(Mask.insert("###.###.###-##", txtcpf));
        txtcelular.addTextChangedListener(Mask.insert("(##)#####-####", txtcelular));
        txtresidencial.addTextChangedListener(Mask.insert("(##)####-####", txtresidencial));
        txtnasc.addTextChangedListener(Mask.insert("(##)####-####", txtnasc));


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtnome.equals("") || txtemail.equals("") || txtsenha.equals("") || txtcpf.equals("") || txttelefone.equals("")) {

                    if (txtnome.getText().length() == 0 || txtemail.getText().length() == 0 || txtsenha.getText().length() == 0 || txtcpf.getText().length() == 0 || txttelefone.getText().length() == 0) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroCliente.this);
                        alerta.setTitle("Erro!");
                        alerta.setMessage("Os campos com * são obrigatórios!");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                txtnome.setText(null);
                                txtemail.setText(null);
                                txtsenha.setText(null);
                                txtcpf.setText(null);
                                txttelefone.setText(null);


                            }
                        });
                        AlertDialog dialog = alerta.create();
                        dialog.show();


                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroCliente.this);
                        alerta.setTitle("OK!");
                        alerta.setMessage("Cliente cadastrado com sucesso!");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (checknews.isChecked()) {
                                    // chamar método JAVA para disparar email
                                }

                            }
                        });
                        AlertDialog dialog = alerta.create();
                        dialog.show();

                    }
                }   }

        });


    }
}


