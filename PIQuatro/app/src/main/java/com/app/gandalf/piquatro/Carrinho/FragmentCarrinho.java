package com.app.gandalf.piquatro.Carrinho;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gandalf.piquatro.R;

public class FragmentCarrinho extends Fragment {


    private static final String TAG = "Carrinho";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho,container,false);

        return view;
    }

}
