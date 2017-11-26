package com.app.gandalf.piquatro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gandalf.piquatro.models.LoginModel;
import com.google.gson.Gson;

public class FragmentLogin extends Fragment{
    private static final String TAG = "Login";
    private ViewHolder holder = new ViewHolder();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login,container,false);

        holder.txtlogin = (EditText) view.findViewById(R.id.txtlogin);
        holder.txtsenha = (EditText) view.findViewById(R.id.txtsenha);
        holder.btnok = (Button) view.findViewById(R.id.btnok);
        holder.txtreg = (TextView) view.findViewById(R.id.txtreg);

        // Login e senha em branco
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (holder.txtlogin.getText().toString().isEmpty() || holder.txtsenha.getText().toString().isEmpty()) {

                    Toast toast = Toast.makeText(getActivity(), "Preencha os campos corretamentes", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // Comunicação com WS e validação de Login
                    String email = holder.txtlogin.getText().toString().trim();
                    String senha = holder.txtsenha.getText().toString().trim();

                    // Login
                    LoginCliente(email, senha);
                }
            }
        };

        holder.txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent it = new Intent(getActivity(), CadastroCliente.class);
            it.putExtra("ACAO", "A");
            startActivity(it);
            }
        });

        holder.btnok.setOnClickListener(listener);

        return view;
    }

    public class ViewHolder{
        EditText txtlogin;
        EditText txtsenha;
        Button btnok;
        TextView txtreg;
        Functions f = new Functions();
    }

    public void LoginCliente(String email, String senha){
        LoginModel login = new LoginModel(email, senha, 0);
        Gson g = new Gson();

        String json = g.toJson(login);
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/login";

        NetworkCall myCall = new NetworkCall();
        myCall.execute(url, json);
    }

    public class NetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return holder.f.Login(getActivity(), params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if(!result.equals("200")){
                    holder.f.showDialog("Falha no login!","Usuário ou senha inválidos", getActivity());
                } else {
                    startActivity(new Intent(getActivity(), NewIndex.class));
                    Toast.makeText(getContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                holder.f.showDialog("Erro","Erro ao obter o resultado", getActivity());
            }
        }
    }

}
