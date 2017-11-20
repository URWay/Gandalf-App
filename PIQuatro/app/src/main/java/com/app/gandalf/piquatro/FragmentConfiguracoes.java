package com.app.gandalf.piquatro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luiz1 on 19/11/2017.
 */

public class FragmentConfiguracoes extends Fragment{

    private static final String TAG = "Configurações";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_configuracoes,container,false);


        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Minha conta");
        opcoes.add("Meus endereços");
        opcoes.add("Sobre");

        ListView listView = (ListView) view.findViewById(R.id.nav_config);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1, opcoes

        );

        listView.setAdapter(listViewAdapter);

        return view;
    }


}
