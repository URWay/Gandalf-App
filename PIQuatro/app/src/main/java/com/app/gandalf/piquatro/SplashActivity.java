package com.app.gandalf.piquatro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);

                    final String FIRSTRUN = "PrimeiraVezRodando";
                    SharedPreferences settings = getSharedPreferences(FIRSTRUN, 0);

                    if(settings.getBoolean("PrimeiraVezRodando",true)){
                        Intent intent = new Intent(getApplicationContext(),FirstTime.class);
                        startActivity(intent);

                        settings.edit().putBoolean("PrimeiraVezRodando",false).commit();
                    }else{
                      Intent intent = new Intent(getApplicationContext(),NewIndex.class);
                        startActivity(intent);

                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
            myThread.start();
    }
}
