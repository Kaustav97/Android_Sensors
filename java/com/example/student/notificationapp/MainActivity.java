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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private ListView tv1;
    private ArrayAdapter<String> adp;

    private Sensor lightsns,gravsns;
    private Button clrbtn;

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
        clrbtn = (Button) findViewById(R.id.clrbtn);

        final SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        final List<Sensor> devsens = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sNames= new ArrayList<String>();

        for(Sensor s:devsens){
            sNames.add(s.getName());
        }

        listener=this;

        tv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sNames) );
        tv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sensorManager.unregisterListener(listener);
                sensorManager.registerListener(listener,devsens.get(i),SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(listener);
            }
        });
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
