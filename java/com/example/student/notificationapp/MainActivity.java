package com.example.student.notificationapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private ListView tv1;
    private ArrayAdapter<String> adp;

    private Sensor lightsns,gravsns;
    private Button lightbtn,gravbtn;
    private boolean flg =false;

    private SensorEventListener listener;

    public void ToastMsg(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv1 = (ListView) findViewById(R.id.senlst);
        final SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> devsens = sensorManager.getSensorList(Sensor.TYPE_ALL);
        tv1.setAdapter(new ArrayAdapter<Sensor>(this,android.R.layout.simple_list_item_1,devsens));

        lightbtn = (Button)findViewById(R.id.lightbtn);
        gravbtn = (Button)findViewById(R.id.gravbtn);

        lightsns = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gravsns = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        // OTHER SENSORS

        sensorManager.registerListener(this,lightsns,SensorManager.SENSOR_DELAY_NORMAL);
        listener=this;

        lightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flg){sensorManager.unregisterListener(listener);flg=!flg; }
                else { sensorManager.registerListener(listener,lightsns,SensorManager.SENSOR_DELAY_NORMAL);flg=!flg;}

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(flg){sensorManager.unregisterListener(listener);flg=!flg; }
//                        else { sensorManager.registerListener(listener,lightsns,SensorManager.SENSOR_DELAY_NORMAL);flg=!flg;}
//                    }
//                },0);
            }
        });

    }


    public void setLight(android.view.View v1 ){
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String vals = "";
        for( float x: sensorEvent.values) vals+="\n"+x;
        ToastMsg(vals);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
