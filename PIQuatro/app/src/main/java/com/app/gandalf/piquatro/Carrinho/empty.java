package com.app.gandalf.piquatro.Carrinho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.gandalf.piquatro.NewIndex;
import com.app.gandalf.piquatro.R;

public class empty extends Fragment {

    private static final String TAG = "Carrinho";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho_empty,container,false);

        Button btnContinuar = (Button) view.findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewIndex.class));
            }
        });

        return view;
    }

}
