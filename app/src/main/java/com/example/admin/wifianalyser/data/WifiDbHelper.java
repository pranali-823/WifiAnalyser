package com.example.admin.wifianalyser.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 04-04-2017.
 */

public class WifiDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shelter.db";

    public static final int DATABASE_VERSION = 1;

    public WifiDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE ="CREATE TABLE "+ WifiContract.WifiEntry.TABLE_NAME+"("+
                WifiContract.WifiEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                WifiContract.WifiEntry.Column_Wifi_Name+" TEXT ,"+
                WifiContract.WifiEntry.Column_Curr_Time+" REAL NOT NULL ,"+
                WifiContract.WifiEntry.Column_Signal+" INTEGER );";

        //String Add = "INSERT INTO "+PetEntry.TABLE_NAME+"("+PetEntry.COLUMN_PET_NAME+","+PetEntry.COLUMN_PET_BREED+","+
        //      PetEntry.COLUMN_PET_GENDER+","+PetEntry.COLUMN_PET_WEIGHT+") VALUES ('RYAN','OPT',1,98);";

        //String DeleteCom = "DELE TABLE "+WifiContract.WifiEntry.TABLE_NAME;
        //db.execSQL(DeleteCom);
        db.execSQL(SQL_CREATE_PETS_TABLE);
        //db.execSQL(Add);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }
}

