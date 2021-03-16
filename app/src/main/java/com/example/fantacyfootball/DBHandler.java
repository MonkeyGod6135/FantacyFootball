package com.example.fantacyfootball;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // initialize constants for DB version and name
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "chc.db";

    // initialize constants for student table
    private static final String TABLE_PLAYER = "player";
    private static final String COLUMN_PLAYER_ID = "_id";
    private static final String COLUMN_PLAYER_NAME = "name";
    private static final String COLUMN_PLAYER_POSITION = "position";
    private static final String COLUMN_PLAYER_TEAM = "team";

    /**
     * Initializes a DBHandler.  Creates version of the database.
     * @param context reference to an activity that initializes the
     *                DBHandler
     * @param cursorFactory null
     */
    public DBHandler (Context context, SQLiteDatabase.CursorFactory cursorFactory) {

        // call superclass constructor
        super(context, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    /**
     * Creates database tables.
     * @param sqLiteDatabase reference to chc database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // define create statement for student table
        String query = "CREATE TABLE " + TABLE_PLAYER + "(" +
                COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAYER_NAME + " TEXT, " +
                COLUMN_PLAYER_POSITION + " TEXT, " +
                COLUMN_PLAYER_TEAM + " TEXT" +
                ");";

        // execute create statement
        sqLiteDatabase.execSQL(query);
    }

    /**
     * Creates a new version of the database.
     * @param sqLiteDatabase reference to shopper database
     * @param oldVersion old version of database
     * @param newVersion new version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        // define drop statement
        String query = "DROP TABLE IF EXISTS " + TABLE_PLAYER;

        // execute drop statement that drops student table
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

    /**
     * This method gets called when the add button in the Action Bar of the
     * MainActivity gets clicked.  It inserts a new row in the
     * student table.
     * @param name student name
     * @param position student year
     * @param team student major
     */
    public void addplayer(String name, String position, String team) {

        // get reference to chc database
        SQLiteDatabase db = getWritableDatabase();

        // initialize a ContentValues object
        ContentValues values = new ContentValues();

        // put data into ContentValues object
        values.put(COLUMN_PLAYER_NAME, name);
        values.put(COLUMN_PLAYER_POSITION, position);
        values.put(COLUMN_PLAYER_TEAM, team);

        // insert data in ContentValues object into student table
        db.insert(TABLE_PLAYER, null, values);

        // close database reference
        db.close();
    }

    /**
     * This method gets called when a menu-item in the overflow menu in the
     * MainActivity is selected.
     * @param team
     * @return
     */
    public int getCount (String team) {

        // get reference to chc database
        SQLiteDatabase db = getWritableDatabase();

        // define select statement
        String query = "SELECT * FROM " + TABLE_PLAYER +
                " WHERE " + COLUMN_PLAYER_TEAM + " = " + "'" + team + "'";

        // execute select statement and return count of rows in Cursor
        return db.rawQuery(query, null).getCount();
    }
}

