package com.senac.luiz.piquatro;

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
import android.text.Editable;
import android.text.TextWatcher;

import static com.senac.luiz.piquatro.R.id.txtlogin;

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

        txtnome = (EditText) findViewById(R.id.txtnome);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtcpf = (EditText) findViewById(R.id.txtcpf);
        txtcelular = (EditText) findViewById(R.id.txtcelular);
        txtcomercial = (EditText) findViewById(R.id.txtcomercial);
        txtresidencial = (EditText) findViewById(R.id.txtres);
        txtnasc = (EditText) findViewById(R.id.txtnasc);
        checknews = (CheckBox) findViewById(R.id.checknews);
        btnok = (Button) findViewById(R.id.btnok);

        txtcpf.addTextChangedListener(Mask.insert("###.###.###-##", txtcpf));
        txtcelular.addTextChangedListener(Mask.insert("(##)#####-####", txtcelular));
        txtresidencial.addTextChangedListener(Mask.insert("(##)####-####", txtresidencial));
        txtnasc.addTextChangedListener(Mask.insert("(##)####-####", txtnasc));


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtnome.getText().length() == 0 || txtemail.getText().length() == 0 || txtsenha.getText().length() == 0 || txtcpf.getText().length() == 0 || txttelefone.getText().length() == 0) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroCliente.this);
                    alerta.setTitle("Erro!");
                    alerta.setMessage("Os campos com * são obrigatórios!");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();

                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroCliente.this);
                    alerta.setTitle("OK!");
                    alerta.setMessage("Cliente cadastrado com sucesso!");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if(checknews.isChecked()){
                                // chamar método JAVA para disparar email
                            }

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();
                }
            }

        });



    }
}
