package com.app.gandalf.piquatro;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class LoginReg extends AppCompatActivity {
    private EditText txtfone;
    private EditText txtsenha;
    private EditText txtemail;
    private Button btnok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        txtfone = (EditText) findViewById(R.id.txtcelular);
        txtsenha = (EditText) findViewById(R.id.txtsenha);
        txtemail = (EditText) findViewById(R.id.txtemail);
        btnok = (Button) findViewById(R.id.btnok);


        txtfone.addTextChangedListener(Mask.insert("(##)#####-####", txtfone));

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtemail.getText().length() == 0 ||txtemail.getText().equals("")||
                    txtsenha.getText().length() == 0 || txtsenha.getText().equals("") ||
                    txtfone.getText().length() == 0 || txtfone.getText().equals("")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginReg.this);
                    alerta.setTitle("You shall not pass!");
                    chamaoGandalf();
                    alerta.setMessage("Preencha as inforamções corretamente!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = alerta.create();
                    dialog.show();
                } else {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginReg.this);
                    alerta.setTitle("OK!");
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
        });


    }

    private void chamaoGandalf() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.errogandalf);

        mediaPlayer.start();
    }

}



