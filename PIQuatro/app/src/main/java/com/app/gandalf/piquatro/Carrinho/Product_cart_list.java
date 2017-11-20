package com.app.gandalf.piquatro.Carrinho;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.gandalf.piquatro.CartAdapter;
import com.app.gandalf.piquatro.R;
import com.app.gandalf.piquatro.models.Cart_List;
import com.app.gandalf.piquatro.models.SharedPreferencesCart;

import java.util.List;

public class Product_cart_list extends Fragment {

    public static final String ARG_ITEM_ID = "product_list";

    Activity activity;
    ListView productListView;
    List<Cart_List> products;
    CartAdapter productListAdapter;
    SharedPreferencesCart sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreferencesCart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.produto_list_row, container, false);
        findViewsById(view);

        productListAdapter = new CartAdapter(activity, products);
        productListView.setAdapter(productListAdapter);
        return view;
    }

    private void findViewsById(View view) {
        //productListView = (ListView) view.findViewById(R.id.listCart);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

}
