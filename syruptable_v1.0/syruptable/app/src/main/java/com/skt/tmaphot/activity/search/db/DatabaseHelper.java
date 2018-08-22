package com.skt.tmaphot.activity.search.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Syruptable";

    private static final String TABLE_NAME = "SearchWord";

    private static final String KEY_ID = "id";
    private static final String KEY_KEYWORD = "keyword";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SYRUPTABLE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_KEYWORD + " TEXT NOT NULL" +
                        ");";
        db.execSQL(CREATE_TABLE_SYRUPTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_DRINK =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_DRINK);
        onCreate(db);
    }

    public void add(SearchWord searchWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KEYWORD, searchWord.getKeyword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public SearchWord getLast() {
        SearchWord searchWord = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+ " order by id desc limit 1", null);

        if (cursor.moveToFirst()) {
            do {
                searchWord = new SearchWord();
                searchWord.setId(Integer.parseInt(cursor.getString(0)));
                searchWord.setKeyword(cursor.getString(1));
            } while (cursor.moveToNext());
        }
//        cursor.close();
        db.close();
        return searchWord;
    }

    public List<SearchWord> getAll() {
        List<SearchWord> searchWordArrayList = new ArrayList<SearchWord>();

        String SELECT_ALL = "SELECT * FROM " + TABLE_NAME + "order by id desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+ " order by id desc", null);


        if (cursor.moveToFirst()) {
            do {
                SearchWord searchWord = new SearchWord();
                searchWord.setId(Integer.parseInt(cursor.getString(0)));
                searchWord.setKeyword(cursor.getString(1));
                searchWordArrayList.add(searchWord);
            } while (cursor.moveToNext());
        }
//        cursor.close();
        return searchWordArrayList;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE id=" + id + ";");
        db.close();
    }

    public int delete(String keyword) {
        SQLiteDatabase db = getWritableDatabase();
        int deleteCount = db.delete(TABLE_NAME, KEY_KEYWORD + "='" + keyword +"'", null);
        db.close();
        return deleteCount;
    }

    public int getTotalCount() {
        String countQuery = "SELECT  * FROM " +TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();

        // return count
        db.close();
        return cursor.getCount();
    }

}

