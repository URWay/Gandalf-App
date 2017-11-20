package com.app.gandalf.piquatro.Carrinho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.app.gandalf.piquatro.Functions;
import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.util.List;

public class Carrinho extends AppCompatActivity {
    private ListView listaCart;
    private List<Cart_List> list;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";
    private Functions f = new Functions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_carrinho);

        listaCart = (ListView) findViewById(R.id.listaCart);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(PRODUCTS, null);

        if(json != null){
            // Alimenta a lista do carrinho
            SharedPreferencesCart sh = new SharedPreferencesCart();
            list = sh.getItens(this);

            MyCustomAdapterCarrinho adaptador = new MyCustomAdapterCarrinho(list, this);
            listaCart.setAdapter(adaptador);
        } else {
            // Colocar mensagem de carrinho vazio, chamar outro fragmento com mensagem vazia
        }

        Button btncheckout = (Button) findViewById(R.id.btncheckout);

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String json = prefs.getString(PRODUCTS, null);

                if (json != null) {

                } else {
                    f.showDialog("Carrinho vazio", "Nenhum produto no carrinho para finalizar a compra", Carrinho.this);
                }
            }
        });

    }
}

