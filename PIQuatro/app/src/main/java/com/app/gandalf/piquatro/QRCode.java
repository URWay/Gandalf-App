package com.app.gandalf.piquatro;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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
                alert1.show();
            }
        });
        scannerView.startCamera();
    }

}
