package com.example.sander.sander_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by sander on 6-5-17.
 *
 * Creates database and defines CRUD methods
 */

public class DBManager {
    private DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    // constructor
    public DBManager(Context c) {
        context = c;
    }

    // open database, use before first CRUD
    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void create(Todo todo) {
        // open database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // insert values into database
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TEXT, todo.getText());
        contentValues.put(DBHelper.CHECKED, todo.getChecked());
        db.insert(DBHelper.TABLE, null, contentValues);
    }

    public ArrayList<Todo> read() {
        // open database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // create arraylist we can fill with todos
        ArrayList<Todo> todos = new ArrayList<>();

        // define which columns we want to read
        String[] columns = new String[] {
                DBHelper._ID,
                DBHelper.TEXT,
                DBHelper.CHECKED };

        // create cursor object filled with previously defined columns
        Cursor cursor = db.query(DBHelper.TABLE, columns, null, null, null, null, null);

        // move over rows in cursor,
        if (cursor.moveToFirst()) {
            do {

                // get needed data from current row
                String text = cursor.getString(cursor.getColumnIndex("TEXT"));
                int checked = cursor.getInt(cursor.getColumnIndex("CHECKED"));
                int id = cursor.getInt(cursor.getColumnIndex("_ID"));

                // create todo object from received data
                Todo todo = new Todo(text, checked, id);
                todos.add(todo);
            }
            // until end of cursor object has been reached
            while (cursor.moveToNext());
        }
        cursor.close();
        return todos;
    }

    public int update(Todo todo) {
        // open database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // update values and return exit code
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TEXT, todo.getText());
        contentValues.put(DBHelper.CHECKED, todo.getChecked());
        return db.update(DBHelper.TABLE, contentValues, DBHelper._ID + " = ?",
                new String[] {String.valueOf(todo.get_id())});
    }

    public void delete(Todo todo) {
        db.delete(DBHelper.TABLE, DBHelper._ID + " = ?",
                new String[] {String.valueOf(todo.get_id())});
    }
}
