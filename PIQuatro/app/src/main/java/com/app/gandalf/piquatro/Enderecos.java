package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Enderecos extends AppCompatActivity {
    private ListView lista;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> opcoes = new ArrayList<String>();
    private List<Integer> positions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);

        lista = (ListView) findViewById(R.id.lista);
        SharedPreferences prefs = getSharedPreferences("SessionLogin", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);

        if(id == 0){
            Intent iRetorno = new Intent(Enderecos.this, Configuracoes.class);
            iRetorno.putExtra("Retorno", 0);
            startActivity(iRetorno);
            finish();
        } else {
            NetworkCall myCall = new NetworkCall();
            myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/all/" + id );
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posicao = positions.get(position);
                if(posicao == 0){
                    Intent intent = new Intent(Enderecos.this, CadastroEndereco.class);
                    intent.putExtra("ACAO", "A");
                    intent.putExtra("idEndereco", "0");
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(Enderecos.this, CadastroEndereco.class);
                    intent.putExtra("ACAO", "M");
                    intent.putExtra("idEndereco", String.valueOf(posicao));
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    // Comunicação do retorno da Lista de Endereços
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

            if(result.isEmpty() || result.equals("")){
                opcoes.add("Adicionar endereço");
                adaptador = new ArrayAdapter<>(Enderecos.this, android.R.layout.simple_list_item_1, opcoes);
                lista.setAdapter(adaptador);
            } else {
                try {
                    JSONArray json = new JSONArray(result);

                    int idEndereco, numero;
                    String nomeEndereco, CEPEndereco, estado, cidade, logradouro;

                    // Lista de Endereços
                    for (int i = 0; i < json.length(); i++) {
                        idEndereco = json.getJSONObject(i).getInt("idEndereco");
                        numero = json.getJSONObject(i).getInt("numeroEndereco");
                        nomeEndereco = json.getJSONObject(i).getString("nomeEndereco");
                        logradouro = json.getJSONObject(i).getString("logradouroEndereco");
                        estado = json.getJSONObject(i).getString("ufendereco");
                        cidade = json.getJSONObject(i).getString("cidadeEndereco");
                        CEPEndereco = json.getJSONObject(i).getString("cependereco");
                        addOption(idEndereco, numero, nomeEndereco, logradouro, estado, cidade, CEPEndereco);
                    }

                    opcoes.add("Adicionar endereço");
                    positions.add(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // Adiciona Opções na lista
    public void addOption(int idEndereco, int numero, String nomeEndereco, String logradouro, String estado, String cidade, String CEPEndereco){
        String endereco = "";
        if (!nomeEndereco.equals("")) {
            endereco = nomeEndereco;
        } else {
            endereco = logradouro;
        }

        String option = endereco.trim() + " " + numero + "\n" +
                        CEPEndereco.trim() + " - " + estado.trim() + " - " + cidade.trim();
        opcoes.add(option);

        //adaptador = new ArrayAdapter<>(Enderecos.this, android.R.layout.simple_selectable_list_item, opcoes);
        //lista.setAdapter(adaptador);

        // Adiciona para pegar a posição posteriormente
        positions.add(idEndereco);

        MyCustomAdapter adaptador = new MyCustomAdapter(opcoes, positions, this, Enderecos.this);
        ListView lView = (ListView)findViewById(R.id.lista);
        lView.setAdapter(adaptador);
    }

    // Retorno
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String retorno = data.getStringExtra("Retorno");

                if (retorno.equals("A")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cadastro adicionado com sucesso!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(retorno.equals("M")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                finish();
                startActivity(getIntent());
            } else if(resultCode == RESULT_CANCELED){
                Functions f = new Functions();
                try{
                    String retorno = data.getStringExtra("Retorno");
                    if(retorno.equals("A.F")){
                        f.showDialog("Erro", "Problema ao cadastrar o endereço!", Enderecos.this);
                    } else if(retorno.equals("A.F")){
                        f.showDialog("Erro", "Problema ao atualizar o endereço!", Enderecos.this);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}

