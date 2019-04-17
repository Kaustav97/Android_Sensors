package com.example.graphproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ListView tv1;
    private Button clrbtn,viewbtn;

    SQLiteDatabase db;
    
    private SensorEventListener listener;

    public void ToastMsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv1 = (ListView) findViewById(R.id.senlst);
        clrbtn = (Button) findViewById(R.id.clrbtn);
        viewbtn = (Button) findViewById(R.id.viewbtn);

        db= openOrCreateDatabase("sensordb",MODE_PRIVATE,null);
        db.execSQL("create table if not exists history(sensor varchar,val varchar ) ");

        final SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        final List<Sensor> devsens = sensorManager.getSensorList(Sensor.TYPE_ALL);

        final List<String> sNames= new ArrayList<String>();

        for(Sensor s:devsens){
            sNames.add(s.getName());
        }

        listener=this;

        tv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sNames) );
        tv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent lgt = new Intent(getApplicationContext(),SingleValues.class);
                lgt.putExtra("title",sNames.get(i));
                lgt.putExtra("idx",i);
                sensorManager.unregisterListener(listener);
                startActivity(lgt);
            }
        });

        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(listener);
                db.execSQL("delete from history");
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor rs = db.rawQuery("select * from history",null);
                String res = "";
                while(rs.moveToNext()){
                    res+= rs.getString(0)+" - "+ rs.getString(1)+"\n";
                }
                ToastMsg(res);
            }
        });



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

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
