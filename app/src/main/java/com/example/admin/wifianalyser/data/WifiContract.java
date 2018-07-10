package com.example.admin.wifianalyser.data;

/**
 * Created by Admin on 04-04-2017.
 */

import android.provider.BaseColumns;

public final class  WifiContract{
    private WifiContract(){}
public final class WifiEntry implements BaseColumns
{
    public final static String TABLE_NAME= "WifiRecord";

    public final static String _ID= BaseColumns._ID;
    public final static String Column_Wifi_Name= "name";
    public final static String Column_Wifi_Mac="mac";
    public final static String Column_Curr_Time= "CurrTime";
    public final static String Column_Signal= "Signal";



}
}