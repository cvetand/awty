package com.example.cvetand.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set button listener
        final Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startStop = startButton.getText().toString();
                //check to see if fields have been filled
                Log.i("buttonListener", "" + validFields());
                if(startStop.equals("Start") && validFields()){
                    startButton.setText("Stop");
                    startAlarm();
                } else if (startStop.equals("Stop")){
                    startButton.setText("Start");
                    stopAlarm();
                }

            }
        });
    }

    public boolean validFields(){
        String phoneNumber = ((EditText) findViewById(R.id.etPhoneNumber)).getText().toString();
        String message = ((EditText) findViewById(R.id.etMessageToSend)).getText().toString();
        String stInterval = ((EditText) findViewById(R.id.etInterval)).getText().toString();
        int interval;
        try {
            interval = Integer.parseInt(stInterval) * 60 * 1000;
        }catch(NumberFormatException e){
            interval = 0;
        }
        return message.length() > 0 && interval >= 60000 && phoneNumber.length() >= 10;
    }

    public void startAlarm(){

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        String stInterval = ((EditText) findViewById(R.id.etInterval)).getText().toString();
        int interval = Integer.parseInt(stInterval)*60*1000;
        String phoneNumber = ((EditText) findViewById(R.id.etPhoneNumber)).getText().toString();
        String message = ((EditText) findViewById(R.id.etMessageToSend)).getText().toString();

        Intent intentToNag = new Intent(MainActivity.this, AlarmReceiver.class);
        intentToNag.putExtra("message", message);
        intentToNag.putExtra("phoneNumber", phoneNumber);

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intentToNag, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, pendingIntent);
        Log.i("startAlarm", "Alarm set for - " + phoneNumber +": " + message+" every "+interval);
    }

    public void stopAlarm(){
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.i("stopAlarm", "alarm stopped");
        am.cancel(pendingIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
