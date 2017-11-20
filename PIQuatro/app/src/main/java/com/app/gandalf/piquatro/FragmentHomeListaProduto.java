package com.app.gandalf.piquatro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHomeListaProduto extends Fragment {

    private static final String TAG = "Produtos";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_produtos,container,false);

        return view;
    }

}
