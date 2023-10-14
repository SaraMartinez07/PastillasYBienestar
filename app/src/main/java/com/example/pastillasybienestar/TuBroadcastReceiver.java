package com.example.pastillasybienestar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TuBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // La lógica que deseas ejecutar cuando se activa la alarma
        Toast.makeText(context, "¡La alarma está sonando!", Toast.LENGTH_SHORT).show();
    }
}
