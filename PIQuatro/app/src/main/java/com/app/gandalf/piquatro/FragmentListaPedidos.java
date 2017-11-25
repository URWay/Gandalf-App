package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentListaPedidos extends Fragment {
    private ListView pedidoslista;

    private static final String TAG = "Meus Pedidos";
    private int idPed = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_configuracoes, container, false);

        pedidoslista = (ListView) view.findViewById(R.id.pedidos);

        NetworkCall myCall = new NetworkCall();
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/pedido/all";

        int idSession = 1; // SharedPreferences.getId();

        myCall.execute(url + "/"+idSession);

        return view;
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String respostaCompleta = "";
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuilder resultado = new StringBuilder();
                String linha = bufferedReader.readLine();

                while (linha != null) {
                    resultado.append(linha);
                    linha = bufferedReader.readLine();
                }

                respostaCompleta = resultado.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return respostaCompleta;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {
                JSONArray json = new JSONArray(result);

                int to = json.length();



                List<String> pedidos = new ArrayList<String>();
                ArrayAdapter<String> adaptador;
                adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pedidos);
                pedidoslista.setAdapter(adaptador);

                pedidos.add("Pedido1");
                pedidos.add("Pedido2");
                pedidos.add("Pedido3");

                for (int i = 0; i <= to; i++) {
                //SÓ FALTA AQUI - SETAR OS PRODUTOS
                    json.getJSONObject(i).getString("imagem");
                    idPed = json.getJSONObject(i).getInt("idPedido");

                    pedidoslista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i = new Intent(getActivity(), descPedido.class);
                            i.putExtra("id",idPed);
                            startActivity(i);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}





