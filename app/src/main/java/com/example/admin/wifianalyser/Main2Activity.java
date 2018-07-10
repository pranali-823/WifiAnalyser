package com.example.admin.wifianalyser;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.wifianalyser.data.WifiContract;
import com.example.admin.wifianalyser.data.WifiDbHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {


    private int mInterval = 5000;
    WifiDbHelper mDbHelper = new WifiDbHelper(this);
    final Handler handler1 = new Handler();
    Timer timer;
    WifiManager wifiManager;
    private Handler handler = new Handler();
    List<String> list;
    int k=1;
    ListView displayView;
    Button b1;
    Button b2;
    ArrayAdapter<String> arrayAdapter;
    TimerTask timertask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        System.out.flush();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        list = new ArrayList<String>();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        b1 = (Button)findViewById(R.id.Stop);
        b2 = (Button)findViewById(R.id.button);
        displayView = (ListView) findViewById(R.id.displayRec);
        arrayAdapter = new ArrayAdapter<String>(
                Main2Activity.this,
                android.R.layout.simple_list_item_1,
                list );
        displayView.setAdapter(arrayAdapter);


        int level=wifiInfo.getRssi();

         //handler.postDelayed(runnable,1000);

        //insertRec();
        //displayCurrDatabaseInfo();





        final ListView displayView = (ListView) findViewById(R.id.displayRec);

        timer = new Timer();
        timertask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    public void  run() {
                        insertRec();
                        System.out.flush();
                        displayCurrDatabaseInfo();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timertask,1000,2000);


        b1.setOnClickListener (new View.OnClickListener() {
            public void onClick(View arg0) {



                // Start NewActivity.class
                Intent myIntent = new Intent(Main2Activity.this,
                        MainActivity.class);
                finish();
            }
        });


        b2.setOnClickListener (new View.OnClickListener() {
            public void onClick(View arg0) {

                if((b2.getText()).equals("Stop")){
                    timertask.cancel();
                    b2.setText("Start");
                }else{
                    timer = new Timer();
                    timertask=new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                public void  run() {
                                    insertRec();
                                    System.out.flush();
                                    displayCurrDatabaseInfo();
                                }
                            });
                        }
                    };
                    timer.scheduleAtFixedRate(timertask,1000,2000);
                    b2.setText("Stop");
                }


            }
        });
        onStop();
    }


//Start


    private void insertRec()
    {


        // Gets the dat a repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(WifiContract.WifiEntry.Column_Wifi_Name,wifiInfo.getSSID());
        values.put(WifiContract.WifiEntry.Column_Curr_Time,new Timestamp(System.currentTimeMillis())+"");
        values.put(WifiContract.WifiEntry.Column_Signal,wifiInfo.getRssi());

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(WifiContract.WifiEntry.TABLE_NAME, null, values);
    }

    private void deleteRec()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String project[]={
                WifiContract.WifiEntry._ID,
                WifiContract.WifiEntry.Column_Wifi_Name,
                WifiContract.WifiEntry.Column_Curr_Time,
                WifiContract.WifiEntry.Column_Signal

        };
        db.delete(WifiContract.WifiEntry.TABLE_NAME, WifiContract.WifiEntry._ID+"<100",project);
    }


    private void displayCurrDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.

        String project[]={
                WifiContract.WifiEntry._ID,
                WifiContract.WifiEntry.Column_Wifi_Name,
                WifiContract.WifiEntry.Column_Curr_Time,
                WifiContract.WifiEntry.Column_Signal

        };
        Cursor cursor = db.query(WifiContract.WifiEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).




            //deleteRec();


            cursor.moveToLast();
            int idColumnIndex = cursor.getColumnIndex(WifiContract.WifiEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(WifiContract.WifiEntry.Column_Wifi_Name);
            int signalColumnIndex = cursor.getColumnIndex(WifiContract.WifiEntry.Column_Signal);
            int timeColumnIndex = cursor.getColumnIndex(WifiContract.WifiEntry.Column_Curr_Time);


                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currSignal = cursor.getString(signalColumnIndex);
                String currTime = cursor.getString(timeColumnIndex);
                list.add(k+": Name:"+currentName+"\nTime:"+currTime+"\nStrength:"+currSignal);
                arrayAdapter.notifyDataSetChanged();
                k++;



        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public void onStop() {

       // deleteRec();
        super.onStop();


    }

    @Override
    public void onDestroy()

    {

        super.onDestroy();
    }


}
