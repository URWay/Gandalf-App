package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luiz1 on 19/11/2017.
 */

public class FragmentConfiguracoes extends Fragment {
    private ListView lista;

    private static final String TAG = "Configurações";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_configuracoes, container, false);

        ArrayAdapter<String> adaptador;

        lista = (ListView) view.findViewById(R.id.lista);

        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Minha conta");
        opcoes.add("Meus endereços");
        opcoes.add("Sobre");

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, opcoes);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        minhaconta();
                        break;
                    case 1:
                        meusenderecos();
                        break;
                    case 2:
                        sobre();
                        break;
                    case 3:;
                        break;
                }
            }
        });

        return view;
    }



    private void minhaconta() {
        /*Bundle bundle = new Bundle();
        bundle.putString("ACAO", "M");


        Fragment fragment = null;
        Class fragmentClass = null;

        fragmentClass = FragmentCadastroCliente.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.corpo, fragment).commit();*/

        Intent it = new Intent(getActivity(), CadastroCliente.class);

        startActivity(it);
        //intent.putExtra("ACAO", "M");
    }

    private void meusenderecos() {


       Intent it = new Intent(getActivity(), Enderecos.class);

        startActivity(it);

    }

    private void sobre() {
        Intent it = new Intent(getActivity(), FragmentSobre.class);
        startActivity(it);
    }


    }




