package com.app.gandalf.piquatro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHomeListaProduto extends Fragment {

    private static final String TAG = "Produtos";

    @Override
<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_produtos, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){

            String id = bundle.getString("idProduto");
            String nomeproduto = bundle.getString("nomeProduto");
            String descproduto = bundle.getString("descProduto");
            String img = bundle.getString("image");
            String preco = bundle.getString("precProd");
            String descpromocao = bundle.getString("descPromocao");

=======
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_produtos,container,false);
>>>>>>> e4c24136990b2ca608c245d380930d971bc923c4

        return view;
    }

}
