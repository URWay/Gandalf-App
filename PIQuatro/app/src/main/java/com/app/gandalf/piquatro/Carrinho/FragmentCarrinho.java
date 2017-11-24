package com.app.gandalf.piquatro.Carrinho;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.gandalf.piquatro.Checkout.Checkout;
import com.app.gandalf.piquatro.Functions;
import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.util.List;

public class FragmentCarrinho extends Fragment {

    private ListView listaCart;
    private List<Cart_List> list;
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";
    private Functions f = new Functions();

    private static final String TAG = "Carrinho";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho,container,false);

        listaCart = (ListView) view.findViewById(R.id.listaCart);
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String json = prefs.getString(PRODUCTS, null);

        if(json != null){
            // Alimenta a lista do carrinho
            SharedPreferencesCart sh = new SharedPreferencesCart();
            list = sh.getItens(getContext());

            MyCustomAdapterCarrinho adaptador = new MyCustomAdapterCarrinho(list, getContext());
            listaCart.setAdapter(adaptador);
        } else {
            // Colocar mensagem de carrinho vazio, chamar outro fragmento com mensagem vazia
        }

        Button btncheckout = (Button) view.findViewById(R.id.btncheckout);

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
                String json = prefs.getString(PRODUCTS, null);

                if (json != null) {
                    Intent intent = new Intent(getActivity(), Checkout.class);
                    startActivity(intent);
                } else {
                    f.showDialog("Carrinho vazio", "Nenhum produto no carrinho para finalizar a compra", getActivity());
                }
            }
        });


        return view;
    }

}
