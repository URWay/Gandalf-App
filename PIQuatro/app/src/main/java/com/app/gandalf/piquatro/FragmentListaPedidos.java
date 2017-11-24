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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentListaPedidos extends Fragment {
    private ListView pedidoslista;

    private static final String TAG = "Meus Pedidos";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_configuracoes, container, false);

        ArrayAdapter<String> adaptador;

        pedidoslista = (ListView) view.findViewById(R.id.pedidos);

        List<String> pedidos = new ArrayList<String>();

        pedidos.add("Pedido1");
        pedidos.add("Pedido2");
        pedidos.add("Pedido3");


        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pedidos);
        pedidoslista.setAdapter(adaptador);

        pedidoslista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(getActivity(), descPedido.class);
                    startActivity(i);
            }
        });

        return view;
    }
}





