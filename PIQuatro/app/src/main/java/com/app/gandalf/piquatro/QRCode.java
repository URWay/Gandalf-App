package com.app.gandalf.piquatro;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRCode extends AppCompatActivity {
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        scannerView = new ZXingScannerView(QRCode.this);
        setContentView(scannerView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast toast = Toast.makeText(QRCode.this,
                        "Não é possível utilizar a aplicação sem permissão de acesso à camera. Saindo...",
                        Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QRCode.this);
                builder.setTitle("Resultado");
                builder.setMessage(result.getText());

                final ZXingScannerView.ResultHandler rh = this;

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        scannerView.resumeCameraPreview(rh);
                    }
                });

                AlertDialog alert1 = builder.create();
                int a = Integer.parseInt(result.getText().substring(1));
                alert1.setMessage(String.valueOf(a));
                alert1.show();
                myMontar(a);
            }
        });
        scannerView.startCamera();
    }


    private void myMontar(int a){
        NetworkCall myCall = new NetworkCall();
        String url = "http://gandalf-ws.azurewebsites.net/pi4/wb/produtos/desc";
        myCall.execute(url+"/"+a);
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

                int idProduto, qtd;
                String nomeProduto, descProduto, imagem;
                double precProduto, descontoPromocao;

                idProduto = json.getJSONObject(1).getInt("idProduto");
                nomeProduto = json.getJSONObject(1).getString("nomeProduto");
                descProduto = json.getJSONObject(1).getString("descProduto");
                precProduto = json.getJSONObject(1).getDouble("precProduto");
                descontoPromocao = json.getJSONObject(1).getDouble("descontoPromocao");
                imagem = json.getJSONObject(1).getString("imagem");
                qtd = json.getJSONObject(1).getInt("qtdMinEstoque");

                Intent i = new Intent(QRCode.this, descProduto.class);
                i.putExtra("idProduto", String.valueOf((idProduto)));
                i.putExtra("nomeProduto", nomeProduto);
                i.putExtra("descProduto", descProduto);
                i.putExtra("image", imagem);
                i.putExtra("precProd", String.valueOf(precProduto));
                i.putExtra("descPromocao", String.valueOf(descontoPromocao));
                i.putExtra("qtdMinEstoque", String.valueOf(qtd));

                startActivity(i);




            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
