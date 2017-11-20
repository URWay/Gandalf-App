package com.app.gandalf.piquatro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeListaProduto extends Fragment {


    public FragmentHomeListaProduto() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_produtos, container, false);

        Bundle bundle = new Bundle();
        if(bundle != null){

            String id = bundle.getString("idProduto");
            String nomeproduto = bundle.getString("nomeProduto");
            String descproduto = bundle.getString("descProduto");
            String img = bundle.getString("image");
            String preco = bundle.getString("precProd");
            String descpromocao = bundle.getString("descPromocao");


        }
        return v;
    }

}
