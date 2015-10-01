package com.example.pablovfds.agendadecontato.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pablovfds on 25/09/15.
 */
public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context){
        super(context, "AGENDA_DE_CONTATOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateContato());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
