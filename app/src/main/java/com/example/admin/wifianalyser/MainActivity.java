package com.example.admin.wifianalyser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button enableButton,disableButton,checkStatus;

    WifiManager wifiManager;
    TextView switchStatus;
    Switch mySwitch;
     @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         checkStatus = (Button)findViewById(R.id.status);


         switchStatus = (TextView) findViewById(R.id.switchStatus);
         mySwitch = (Switch) findViewById(R.id.switch1) ;


         wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


         if(wifiManager.isWifiEnabled())
             mySwitch.setChecked(true);

         else
             mySwitch.setChecked(false);
         //attach a listener to check for changes in state
         mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

             @Override
             public void onCheckedChanged(CompoundButton buttonView,
                                          boolean isChecked) {

                 if(isChecked){
                     wifiManager.setWifiEnabled(true);
                     switchStatus.setText("wifi is currently ON");

                 }else{
                     wifiManager.setWifiEnabled(false);
                     switchStatus.setText("wifi is currently OFF");
                 }

             }
         });

         //check the current state before we display the screen
         if(mySwitch.isChecked()){
             switchStatus.setText("Wifi is currently ON");
         }
         else {
             switchStatus.setText("Wifi is currently OFF");
         }



         checkStatus.setOnClickListener(new android.view.View.OnClickListener(){
             public void onClick(View v){

                 Intent myIntent = new Intent(MainActivity.this,
                         Main2Activity.class);
                 startActivity(myIntent);
             }
         });


    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
