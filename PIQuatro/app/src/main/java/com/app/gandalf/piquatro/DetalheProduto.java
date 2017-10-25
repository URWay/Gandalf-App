package com.app.gandalf.piquatro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;

public class DetalheProduto extends AppCompatActivity {
    private TextView txtnomeprod;
    private TextView txtpreco;
    private TextView txtprecodesc;
    private TextView txtdescricao;
    private Button btnconfirma;
    private ImageView imgproduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        txtnomeprod = (TextView) findViewById(R.id.txtnomeprod);
        txtpreco = (TextView) findViewById(R.id.txtpreco);
        txtprecodesc = (TextView) findViewById(R.id.txtprecodesc);
        txtdescricao = (TextView) findViewById(R.id.txtdescricao);


    }
}
