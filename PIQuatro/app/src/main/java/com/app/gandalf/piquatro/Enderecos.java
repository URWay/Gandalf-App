package com.app.gandalf.piquatro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private MyCustomAdapter thadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Meus Endereços");     //Titulo para ser exibido na sua Action Bar em frente à seta


        lista = (ListView) findViewById(R.id.lista);
        SharedPreferences prefs = getSharedPreferences("SessionLogin", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);

        NetworkCall myCall = new NetworkCall();
        Toast toast = Toast.makeText(Enderecos.this,"Consultando...",Toast.LENGTH_LONG);
        toast.show();
        myCall.execute("http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/all/" + id );

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
                positions.add(0);
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
        if (!logradouro.equals("")) {
            endereco = logradouro;
        } else {
            endereco = nomeEndereco;
        }

        String option = endereco.trim() + " " + numero + "\n" +
                        CEPEndereco.trim() + " - " + estado.trim() + " - " + cidade.trim();
        opcoes.add(option);

        // Adiciona para pegar a posição posteriormente
        positions.add(idEndereco);

        thadapter = new MyCustomAdapter(opcoes, positions, this, Enderecos.this);
        ListView lView = (ListView)findViewById(R.id.lista);
        lView.setAdapter(thadapter);
    }

    // Retorno
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String retorno = data.getStringExtra("Retorno");

                if (retorno.equals("A")) {
                    Toast.makeText(getApplicationContext(), "Cadastro adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                } else if(retorno.equals("M")){
                    Toast.makeText(getApplicationContext(), "Cadastro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                finish();
                startActivity(getIntent());
            } else if(resultCode == RESULT_CANCELED){
                Functions f = new Functions();
                try{
                    String retorno = data.getStringExtra("Retorno");
                    if(retorno.equals("A.F")){
                        f.showDialog("Erro", "Problema ao cadastrar o endereço!", Enderecos.this);
                    } else if(retorno.equals("M.F")){
                        f.showDialog("Erro", "Problema ao atualizar o endereço!", Enderecos.this);
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list;
        private List<Integer> positions;
        private Context context;
        private int id;
        private Activity activity;
        private int pos;

        public MyCustomAdapter(ArrayList<String> list, List<Integer> positions, Context context, Activity activity) {
            this.list = list;
            this.positions = positions;
            this.context = context;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0; //list.get(pos).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, null);
            }

            TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
            ImageView deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);
            listItemText.setText(list.get(position));

            int valor = positions.get(position);

            if(valor == 0 && valor <= 0){
                view.findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whick) {
                            switch (whick) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    int idEndereco = positions.get(position);
                                    pos = position;

                                    String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/endereco/delete";

                                    Functions fun = new Functions();
                                    String param = "{\"idEndereco\":"+idEndereco+"}";

                                    NetworkCallDelete myCall = new NetworkCallDelete();;
                                    myCall.execute(url, param, "POST");

                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;

                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Corfirma a exclusão do endereço ?").setTitle("Deletar endereço").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
                }
            });

            return view;
        }

        // Exclusão do endereço
        public class NetworkCallDelete extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                Functions f = new Functions();

                int retorno = f.sendPost(params[0], params[1], params[2]);

                return String.valueOf(retorno);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(result.equals("200")){
                    list.remove(pos);
                    Toast.makeText(activity.getApplicationContext(), "Endereço deletado com sucesso!", Toast.LENGTH_SHORT).show();
                    thadapter.notifyDataSetChanged();

                    String valor = list.get(pos);

                    if(valor.equals("Adicionar endereço")){
                        findViewById(R.id.delete_btn).setVisibility(View.INVISIBLE);
                    }

                }else {
                    Toast.makeText(activity.getApplicationContext(), "Erro ao deletar o endereço.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}

