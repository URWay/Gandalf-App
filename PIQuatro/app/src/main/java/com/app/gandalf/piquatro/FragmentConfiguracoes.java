package com.app.gandalf.piquatro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luiz1 on 19/11/2017.
 */

public class FragmentConfiguracoes extends Fragment{

    private static final String TAG = "Configurações";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_configuracoes,container,false);


        return view;
    }


}
