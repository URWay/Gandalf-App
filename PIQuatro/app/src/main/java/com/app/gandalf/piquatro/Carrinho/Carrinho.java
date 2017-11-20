package com.app.gandalf.piquatro.Carrinho;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.util.List;

public class Carrinho extends AppCompatActivity {
    private ListView listaCart;
    private List<Cart_List> list;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";

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
        }

    }
}

