package com.app.gandalf.piquatro.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONArray;

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
            String retorno = "";

            // Valor já armazenado
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
            retorno = prefs.getString(PRODUCTS, null);

            if(retorno == null || retorno == ""){
                String json = gson.toJson(list);
                editor.putString(PRODUCTS, json);
                editor.commit();
            } else {
                JSONArray array = new JSONArray(retorno);
                Cart_List cartNew = null;

                int id, qtd;
                double preco, promocao;
                String nome, desc, image;

                if(!list.contains(array)){

                    // Adicionando produtos que já estavam no carrinho
                    for (int i = 0; i < array.length(); i++) {

                        // ID dos objetos g
                        id = array.getJSONObject(i).getInt("id");

                        if (id != list.get(0).getId()) {
                            qtd = array.getJSONObject(i).getInt("qtd");

                            preco = array.getJSONObject(i).getDouble("preco");
                            promocao = array.getJSONObject(i).getDouble("promocao");

                            nome = array.getJSONObject(i).getString("nome");
                            desc = array.getJSONObject(i).getString("desc");
                            image = array.getJSONObject(i).getString("image");

                            cartNew = new Cart_List(id, nome, desc, image, preco, promocao, qtd);
                            list.add(cartNew);
                        } else {
                            int qtdOld = list.get(0).getQtd();
                            int qtdJson = array.getJSONObject(i).getInt("qtd");
                            int QTD_NEW = qtdOld + qtdJson;
                            list.get(0).setQtd(QTD_NEW);
                        }

                    }
                }

                String json = gson.toJson(list);
                editor.putString(PRODUCTS, json);
                editor.commit();
            }


        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
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

    // Passando três parâmetros, remove do carrinho sem comparar os existentes
    public void saveItens(Context context, Cart_List cart, List<Cart_List> list){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(PRODUCTS, json);
        editor.commit();
    }

    // Função de remover do carrinho
    public void removeIten(Context context, Cart_List cart) {
        ArrayList<Cart_List> list = getItens(context);
        if (list != null) {
            list.remove(cart);
            saveItens(context, cart, list);
        }
    }

    public void removeSharedItens(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PRODUCTS, null);
        editor.apply();
    }

    public double getTotal(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        String retorno = prefs.getString(PRODUCTS, null);

        if(retorno == null || retorno == ""){
            return 0;
        }

        List<Cart_List> cart;
        cart = getItens(context);

        if(retorno != null) {
            double total = 0;
            boolean promocao;
            double preco, promocaoProduto;
            int qtd;

            for (int i = 0; i < cart.size(); i++) {
                preco = cart.get(i).getPreco();
                promocaoProduto = cart.get(i).getPromocao();
                promocao = preco <= promocaoProduto;
                qtd = cart.get(i).getQtd();

                if (promocao) {
                    total +=  qtd * preco;
                } else {
                    total += qtd * promocaoProduto;
                }
            }

            return total;
        }

        return 0;
    }

}