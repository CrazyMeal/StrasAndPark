package com.crazymeal.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.crazymeal.model.Parking;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ParkingDatabase extends SQLiteOpenHelper{
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Parkings.db";
    
    static final public String TABLE_NAME = "parkings";
    
    static final public String PARKING_UUID = "_id";
    static final public String PARKING_ID = "id";
	static final public String PARKING_NAME = "name";
	static final public String PARKING_AVAIBLE = "avaible";
	static final public String PARKING_FULL = "full";
	static final public String PARKING_STATUS = "status";
	static final public String PARKING_LONGITUDE = "longitude";
	static final public String PARKING_LATITUDE = "latitude";

	public ParkingDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createScoreTable = "CREATE TABLE " + TABLE_NAME + " (" +
				PARKING_UUID + " INTEGER PRIMARY KEY,"
				+ PARKING_ID + " INTEGER,"
				+ PARKING_NAME + " TEXT," 
				+ PARKING_AVAIBLE + " INTEGER," 
				+ PARKING_FULL + " INTEGER," 
				+ PARKING_STATUS + " INTEGER,"
				+ PARKING_LONGITUDE + " REAL,"
				+ PARKING_LATITUDE + " REAL"
				+ " )";
			db.execSQL(createScoreTable);
			Log.d("DATABASE_SQL","Database table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME);
		onCreate(db);
	}
	
	public void addParkings(HashMap<Integer, Parking> parkings){
		SQLiteDatabase db = this.getWritableDatabase();
		for(Parking p : parkings.values()){
			db.insert(TABLE_NAME, null, p.getAsContentValues());
			Log.d("DATABASE_SQL", "Inserted parking");
		}
		db.close();
	}

	public ArrayList<Parking> getAllAsList() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery ("SELECT * FROM " + 	TABLE_NAME, null);
		if (c == null) {
		   db.close();
		   return null;
		}
		c.moveToFirst();
		ArrayList<Parking> all = new ArrayList<Parking> ();
		while (! c.isAfterLast()) {
		    all.add(new Parking(c));
		    c.moveToNext();
		}
		System.out.println(all);
		return all;

	}
}