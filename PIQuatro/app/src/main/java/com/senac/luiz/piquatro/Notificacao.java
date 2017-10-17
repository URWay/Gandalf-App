package com.senac.luiz.piquatro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Notificacao extends AppCompatActivity {
private TextView txtnotific;
  private TextView txtnotific2;
    private TextView txtnotific3;
    private Button btnenvia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

       txtnotific = (TextView) findViewById(R.id.txtnotific);

                Intent intent = new Intent(Notificacao.this, Home.class);
                int id = (int) (Math.random()*1000);
                PendingIntent pi = PendingIntent.getActivity(getBaseContext(), id,intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new Notification.Builder(getBaseContext())
                        .setContentTitle("Gandalf Geek Store")
                        .setContentText(txtnotific.getText()).setSmallIcon(R.drawable.logogandalf)
                        .setContentIntent(pi).build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(id, notification);


    }
}

