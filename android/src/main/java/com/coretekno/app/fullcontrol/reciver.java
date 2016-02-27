package com.coretekno.app.fullcontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class reciver extends BroadcastReceiver {
    public reciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context,full_service.class));
        Toast.makeText(context,"OOOVVVVV",Toast.LENGTH_LONG).show();
    }
}
