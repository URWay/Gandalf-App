package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class descProduto extends AppCompatActivity {
    private EditText edtQtdDesc;
    private int id, qtd, qtdEstoque;
    private String nomeProduto, descProduto, imageProduto;
    private double precoProduto, promocaoProduto;
    private List<String> collection = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão


        edtQtdDesc = (EditText) findViewById(R.id.edtQtdDesc);
        Button btnconfirma = (Button) findViewById(R.id.btnvolta);

        TextView txtdescMinus = (TextView) findViewById(R.id.txtdescMinus);
        TextView txtdescPlus = (TextView) findViewById(R.id.txtdescPlus);

        Intent intent = getIntent();

        if(intent != null){
            try {

                if(!intent.getStringExtra("idProduto").equals("0")){
                    final int idProduto = Integer.parseInt(intent.getStringExtra("idProduto"));

                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        String nome = bundle.getString("nomeProduto");
                        String desc = bundle.getString("descProduto");
                        String image = bundle.getString("image");
                        qtdEstoque = Integer.parseInt(bundle.getString("qtdMinEstoque"));

                        Double precoprod = Double.parseDouble(bundle.getString("precProd"));
                        Double descprecoprod = Double.parseDouble(bundle.getString("descPromocao"));

                        // Sentando os valores passado via Intent
                        TextView txtnomeprod = (TextView) findViewById(R.id.txt1);
                        TextView txtprecoprod = (TextView) findViewById(R.id.txtpreco);
                        TextView txtdescpreco = (TextView) findViewById(R.id.txtprecodesc);
                        TextView txtdescricao = (TextView) findViewById(R.id.txtdescricao);
                        ImageView imgproduto = (ImageView) findViewById(R.id.imgproduto);

                        txtnomeprod.setText(nome);
                        txtdescricao.setText(desc);
                        txtprecoprod.setText(new DecimalFormat("R$ #,##0.00").format(precoprod));
                        txtprecoprod.setPaintFlags(txtprecoprod.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        txtdescpreco.setText(new DecimalFormat("R$ #,##0.00").format(descprecoprod));

                        final byte[] image64 = Base64.decode(image, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image64, 0, image64.length);
                        imgproduto.setImageBitmap(bitmap);

                        // Setando valores para adicionar ao carrinho
                        id = idProduto;
                        nomeProduto = nome;
                        descProduto = desc;
                        imageProduto = image;
                        precoProduto = precoprod;
                        promocaoProduto = descprecoprod;
                        getSupportActionBar().setTitle(nome);     //Titulo para ser exibido na sua Action Bar em frente à seta
                        // Verifica promoção
                        if(precoProduto <= promocaoProduto){
                            findViewById(R.id.textView32).setVisibility(View.INVISIBLE);
                            findViewById(R.id.txtpreco).setVisibility(View.INVISIBLE);
                        }
                    }

                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }

        txtdescMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtdMinus = Integer.parseInt(edtQtdDesc.getText().toString());
                if(qtdMinus != 1)
                    edtQtdDesc.setText(String.valueOf(qtdMinus - 1));
            }
        });

        txtdescPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qtdPlus = Integer.parseInt(edtQtdDesc.getText().toString());
                // Quantidade mínina no estoque
                if (qtdEstoque >= qtdPlus)
                    edtQtdDesc.setText(String.valueOf(qtdPlus + 1));
                else
                    Toast.makeText(getApplicationContext(), "Quantidade insuficiente no estoque!", Toast.LENGTH_SHORT).show();

            }
        });

        // Adicionar o produto no carrinho
        btnconfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOption(id, nomeProduto, descProduto, imageProduto, precoProduto, promocaoProduto);
            }
        });
    }

    public void addOption(int id, String nome, String desc, String image, double preco, double promo){
        qtd = Integer.parseInt(String.valueOf(edtQtdDesc.getText().toString()));
        Cart_List list = new Cart_List(id, nome, desc, image, preco, promo, qtd);
        List<Cart_List> cart = new ArrayList<>();
        cart.add(list);

        SharedPreferencesCart sh = new SharedPreferencesCart();
        boolean retorno = sh.saveItens(this, cart);

        if(retorno){
            Toast.makeText(getApplicationContext(), "Produto adicionado no carrinho!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(descProduto.this, NewIndex.class));
        } else {
            Toast.makeText(getApplicationContext(), "Problema ao adicionadar produto no carrinho!", Toast.LENGTH_SHORT).show();
        }
    }

}