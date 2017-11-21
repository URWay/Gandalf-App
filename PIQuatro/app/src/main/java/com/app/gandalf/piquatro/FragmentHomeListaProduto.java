package com.app.gandalf.piquatro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHomeListaProduto extends Fragment {
    private ViewGroup mensagens;
    private static final String TAG = "Produtos";

    @Override
<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_produtos, container, false);


        return v;
    }
=======
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_produtos,container,false);
>>>>>>> b00bca4d98117e87e7359620c5a2f21e7ce458ed



}
