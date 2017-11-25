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
import android.widget.TextView;

import com.app.gandalf.piquatro.Checkout.Checkout;
import com.app.gandalf.piquatro.Functions;
import com.app.gandalf.piquatro.NewIndex;
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
    private View view;
    private static final String TAG = "Carrinho";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho,container,false);

        SharedPreferences prefs = getActivity().getSharedPreferences("PRODUCT_APP", getContext().MODE_PRIVATE);
        String product = prefs.getString("Product", null);
        if(product == null || product.equals("") || product.equals("[]")) {
            view = inflater.inflate(R.layout.fragment_carrinho_empty, container, false);

            Button btnContinuar = (Button) view.findViewById(R.id.btnContinuar);

            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), NewIndex.class));
                }
            });

            return view;
        }

        listaCart = (ListView) view.findViewById(R.id.listaCart);

        // Alimenta a lista do carrinho
        SharedPreferencesCart sh = new SharedPreferencesCart();
        list = sh.getItens(getContext());

        TextView txtTotalCarrrinho = (TextView) view.findViewById(R.id.txtTotalCarrrinho);

        // Total do carrinho
        txtTotalCarrrinho.setText("R$: " + String.valueOf(sh.getTotal(getContext())));

        MyCustomAdapterCarrinho adaptador = new MyCustomAdapterCarrinho(list, getContext());

        listaCart.setAdapter(adaptador);

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
