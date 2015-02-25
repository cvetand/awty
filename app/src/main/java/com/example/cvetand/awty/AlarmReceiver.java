package com.example.cvetand.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by cvetand on 2/24/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getExtras().getString("message");
        String phoneNumber = intent.getExtras().getString("phoneNumber");
        Log.i("AlarmReceiver", "Toast set off");
        Toast.makeText(context, phoneNumber +": "+message, Toast.LENGTH_LONG).show();
    }
}
