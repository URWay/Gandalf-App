package com.app.gandalf.piquatro.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferencesCart {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String PRODUCTS = "Product";

    public SharedPreferencesCart() {
        super();
    }

    public boolean saveItens(Context context, List<Cart_List> list) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        try{
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            editor = settings.edit();

            Gson gson = new Gson();
            String json = gson.toJson(list);

            editor.putString(PRODUCTS, json);

            editor.commit();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void addItem(Context context, Cart_List cart) {
        List<Cart_List> list = getItens(context);
        if (list == null)
            list = new ArrayList<Cart_List>();
            list.add(cart);
    }

    public ArrayList<Cart_List> getItens(Context context) {
        SharedPreferences settings;
        List<Cart_List> list;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(PRODUCTS)) {
            String json = settings.getString(PRODUCTS, null);
            Gson gson = new Gson();
            Cart_List[] Itens = gson.fromJson(json, Cart_List[].class);

            list = Arrays.asList(Itens);
            list = new ArrayList<Cart_List>(list);
        } else {
            return null;
        }

        return (ArrayList<Cart_List>) list;
    }


}
