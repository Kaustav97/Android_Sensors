package com.example.graphproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class SingleValues extends AppCompatActivity implements SensorEventListener {

    LineGraphSeries<DataPoint> series1,series2,series3,series4;
    DataPoint hist[];
    SQLiteDatabase db;
    int idx;

    public void ToastMsg(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
    double x;

    private SensorEventListener listener;
    private GraphView plot;
    private TextView valtxt;
    private Button savebtn;

    String sen_name;
    double latest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_values);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        plot = findViewById(R.id.single_plot);
        valtxt = findViewById(R.id.valtxt);
//        savebtn = findViewById(R.id.savebtn);
        db= openOrCreateDatabase("sensordb",MODE_PRIVATE,null);


        final SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        listener=this;
        final List<Sensor> devsens = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensorManager.unregisterListener(listener);

        idx = getIntent().getExtras().getInt("idx");
        sen_name = getIntent().getExtras().getCharSequence("title").toString();
        toolbar.setTitle(getIntent().getExtras().getCharSequence("title"));
        setSupportActionBar(toolbar);

        series1 = new LineGraphSeries<DataPoint>();
        series2 = new LineGraphSeries<DataPoint>();
        series3 = new LineGraphSeries<DataPoint>();

        hist = new  DataPoint[50];
        x=0;

        sensorManager.registerListener(listener,devsens.get(idx), SensorManager.SENSOR_DELAY_UI);

//        savebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                db.execSQL( String.format("insert into history values('%s','%s')",sen_name,(""+latest)) );
//                ToastMsg(String.format("insert into history values('%s','%d')",sen_name,latest));
//            }
//        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent ) {

	   latest = sensorEvent.values[0];
	   db.execSQL( String.format("insert into history values('%s','%s')",sen_name,(""+latest)) );
	   plot.removeAllSeries();

        if(idx==0 )Accelerometer(sensorEvent);
        if(idx==1)Gyroscope(sensorEvent);
        if(idx==6)AmbientLight(sensorEvent);
        if(idx==4)GravSens(sensorEvent);
        if(idx==9 || idx==10 || idx==11)FlatUp(sensorEvent);
        if(idx==15) RearProx(sensorEvent);

        String tmp="";
        for (double v: sensorEvent.values) tmp+=""+v+"\n";
        valtxt.setText(tmp + "\n IDX = "+x);

    }

    public void AmbientLight(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
        plot.addSeries(series1);

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(0);
        plot.getViewport().setMaxY(1000);
    }

    public void Accelerometer(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        series2.appendData( new DataPoint(x,evt.values[1]),true,40);
        series3.appendData( new DataPoint(x,evt.values[2]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
        series2.setColor(Color.GREEN);
        series3.setColor(Color.BLUE);

        plot.addSeries(series1);
        plot.addSeries(series2);
        plot.addSeries(series3);

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(-157);
        plot.getViewport().setMaxY(157);
    }

    public void Gyroscope(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        series2.appendData( new DataPoint(x,evt.values[1]),true,40);
        series3.appendData( new DataPoint(x,evt.values[2]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
//            series1.setDrawDataPoints(true);

        series2.setColor(Color.GREEN);
//            series2.setDrawDataPoints(true);

        series3.setColor(Color.BLUE);
//            series3.setDrawDataPoints(true);

        plot.addSeries(series1);
        plot.addSeries(series2);
        plot.addSeries(series3);

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(-50);
        plot.getViewport().setMaxY(50);

    }

    public void GravSens(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        series2.appendData( new DataPoint(x,evt.values[1]),true,40);
        series3.appendData( new DataPoint(x,evt.values[2]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
//            series1.setDrawDataPoints(true);

        series2.setColor(Color.GREEN);
//            series2.setDrawDataPoints(true);

        series3.setColor(Color.BLUE);
//            series3.setDrawDataPoints(true);

//        try{
            plot.addSeries(series1);
            plot.addSeries(series2);
//            plot.addSeries(series3);
//        }
//        catch (Exception e){
//            ToastMsg("PLOTTING ERROR");
//        }

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(-50);
        plot.getViewport().setMaxY(50);
    }

    public void FlatUp(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
//            series1.setDrawDataPoints(true);
        plot.addSeries(series1);

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(-1);
        plot.getViewport().setMaxY(1);
    }

    public void RearProx(SensorEvent evt){
        series1.appendData( new DataPoint(x,evt.values[0]),true,40);
        x+=0.2;

        series1.setColor(Color.RED);
//            series1.setDrawDataPoints(true);
        plot.addSeries(series1);

        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(-10);
        plot.getViewport().setMaxY(10);
    }


//    public void LinAcceleration(SensorEvent evt){
//
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
