package com.senac.luiz.piquatro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

abstract class RodarNotificacao extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
//Definir início para as 10 horas
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

//Definir intervalo de 2 horas
        long intervalo = 2*20*20*1000; //2 horas em milissegundos

        Intent tarefaIntent = new Intent(context, Notificacao.class);
        PendingIntent tarefaPendingIntent = PendingIntent.getBroadcast(context,1234, tarefaIntent,0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

//Definir o alarme para acontecer de 2 em 2 horas a partir das 10 horas
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                intervalo, tarefaPendingIntent);
    }

}