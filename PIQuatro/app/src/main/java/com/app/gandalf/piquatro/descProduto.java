package com.app.gandalf.piquatro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class descProduto extends AppCompatActivity {
    private TextView txtnomeprod;
    private ImageView imgproduto;
    private TextView txtdescricao;
    private TextView txtprecodesc;
    private TextView txtpreco;
    private int id, qtd;
    private String nomeProduto, descProduto, imageProduto;
    private double precoProduto, promocaoProduto;
    private List<String> collection = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        txtnomeprod = (TextView) findViewById(R.id.txt1);
        imgproduto = (ImageView) findViewById(R.id.imgproduto);
        txtdescricao = (TextView) findViewById(R.id.txtdescricao);
        txtprecodesc = (TextView) findViewById(R.id.txtprecodesc);
        txtpreco = (TextView) findViewById(R.id.txtpreco);

        txtdescricao.setMovementMethod(new ScrollingMovementMethod());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        Button btnconfirma = (Button) findViewById(R.id.btnvolta);

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
                        int qtdEstoque = Integer.parseInt(bundle.getString("qtdMinEstoque"));

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

                        // Quantidade mínina no estoque
                        int qtdMin = qtdEstoque;

                        for(int i = 1; i <= qtdMin; i++){
                            collection.add("Quantidade: " + String.valueOf(i));
                        }

                        // Setando valores para adicionar ao carrinho
                        id = idProduto;
                        nomeProduto = nome;
                        descProduto = desc;
                        imageProduto = image;
                        precoProduto = precoprod;
                        promocaoProduto = descprecoprod;

                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, collection);
                        Spinner spinner = (Spinner) findViewById(R.id.spnQtd);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerArrayAdapter);
                        String qtdSem = spinner.getSelectedItem().toString().replace("Quantidade: ", "");
                        qtd = Integer.parseInt(qtdSem);

                        //BTN VOLTAR
                        setTitle(nomeProduto);
                        // Get a support ActionBar corresponding to this toolbar
                        ActionBar ab = getSupportActionBar();
                        // Enable the Up button
                        ab.setDisplayHomeAsUpEnabled(true);

                    }

                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }

        // Adicionar o produto no carrinho
        btnconfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOption(id, nomeProduto, descProduto, imageProduto, precoProduto, promocaoProduto, qtd);
            }
        });




    }

    public void addOption(int id, String nome, String desc, String image, double preco, double promo, int qtd){

        Spinner spinner = (Spinner) findViewById(R.id.spnQtd);
        String qtdSem = spinner.getSelectedItem().toString().replace("Quantidade: ", "");
        qtd = Integer.parseInt(qtdSem);

        Cart_List list = new Cart_List(id, nome, desc, image, preco, promo, qtd);
        List<Cart_List> cart = new ArrayList<>();
        cart.add(list);

        SharedPreferencesCart sh = new SharedPreferencesCart();
        boolean retorno = sh.saveItens(this, cart);

        if(retorno){
            Toast.makeText(getApplicationContext(), "Produto adicionado no carrinho!", Toast.LENGTH_SHORT).show();
            // Retornar quantidade de produtos no carrinho
        } else {
            Toast.makeText(getApplicationContext(), "Problema ao adicionadar produto no carrinho!", Toast.LENGTH_SHORT).show();
        }

    }
}