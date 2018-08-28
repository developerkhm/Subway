package com.skt.tmaphot.activity.area;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

//This class used to open database and reading database table values
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_area.db";
    private static final int VERSION_NAME = 1;
    private final static String TAG = "DatabaseHelper";
    private SQLiteDatabase myDataBase;
    private String myPath;
    private String mDatapath;
    private Context myContext;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NAME);
    }

    public DatabaseOpenHelper(Context context, String filePath) {
        super(context, DATABASE_NAME, null, VERSION_NAME);
        this.myContext = context;
        mDatapath = filePath;
        myPath = String.valueOf(filePath + "/" + DATABASE_NAME);
        Log.d(TAG,"filepath : " +  myPath);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
        //TODO
    }

    public void prepareDataBase() throws IOException {
        //here we are checking database already on specified path or not
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.d(TAG, "Database exists.");
//            try {
//                copyDataBase();
//            } catch (IOException e) {
//                Log.e(TAG, e.getMessage());
//            }
        } else {
            Log.d(TAG, "Database not exists.");
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    //method checks for DB exist or NOT
    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            File dbFile = new File(myPath);
            checkDB = dbFile.exists();
        } catch (SQLiteException ignored) {
        }
        return checkDB;
    }

    private void copyDataBase() throws IOException {

        try {
            File file = new File(mDatapath);
            if( !file.exists() ) {
                file.mkdirs();
            }

            OutputStream myOutput = new FileOutputStream(myPath);
            InputStream myInput = myContext.getAssets().open("database/" + DATABASE_NAME);
            byte[] buffer = new byte[1024 * 1024 * 5];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            Log.d(TAG, "copyDataBase complete.");
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch(Exception e){
            e.printStackTrace();
            Log.d(TAG, "copyDataBase error : " + e.getMessage());
        }
    }


    //Opening DB and Getting Category DATA from DB
    public ArrayList<XLAreaItem> getXLarea() {
        myDataBase = SQLiteDatabase.openDatabase(myPath,
                null, SQLiteDatabase.OPEN_READONLY);
        String query = "select do_code, do_txt  from area GROUP BY do_code";
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<XLAreaItem> categoryArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            XLAreaItem category = new XLAreaItem();
            category.setId(cursor.getInt(cursor.getColumnIndex("do_code")));
            category.setArea(cursor.getString(cursor.getColumnIndex("do_txt")));
            categoryArrayList.add(category);
        }
        myDataBase.close();
        cursor.close();
        return categoryArrayList;
    }

    public ArrayList<LAreaItem> getLarea(int id) {
        myDataBase = SQLiteDatabase.openDatabase(myPath,
                null, SQLiteDatabase.OPEN_READONLY);
        String query = "select sigun_code, sigun_txt, lati, longti from area Where do_code = \'"+ id + "\' GROUP BY sigun_code";
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<LAreaItem> categoryArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            LAreaItem category = new LAreaItem();
            category.setPreid(id);
            category.setId(cursor.getInt(cursor.getColumnIndex("sigun_code")));
            category.setArea(cursor.getString(cursor.getColumnIndex("sigun_txt")));
            category.setLati(cursor.getString(cursor.getColumnIndex("lati")));
            category.setLongti(cursor.getString(cursor.getColumnIndex("longti")));
            categoryArrayList.add(category);
        }
        myDataBase.close();
        cursor.close();
        return categoryArrayList;
    }

    public ArrayList<MAreaItem> getMarea(int preid, int id) {
        myDataBase = SQLiteDatabase.openDatabase(myPath,
                null, SQLiteDatabase.OPEN_READONLY);
        String query = "select dong_code, dong_txt, lati, longti from area Where do_code = \'"+ preid + "\' and sigun_code = \'" + id + "\' GROUP BY dong_code";
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<MAreaItem> categoryArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            MAreaItem category = new MAreaItem();
            category.setId(cursor.getInt(cursor.getColumnIndex("dong_code")));
            category.setArea(cursor.getString(cursor.getColumnIndex("dong_txt")));
            category.setLati(cursor.getString(cursor.getColumnIndex("lati")));
            category.setLongti(cursor.getString(cursor.getColumnIndex("longti")));
            categoryArrayList.add(category);
        }
        myDataBase.close();
        cursor.close();
        return categoryArrayList;
    }
}