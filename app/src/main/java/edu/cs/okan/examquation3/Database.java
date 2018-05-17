package edu.cs.okan.examquation3;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "sqllite_database";

	private static final String TABLE_NAME = "Users";
	private static String ID = "Id";
	private static String USER_NAME = "username";
	private static String NAME = "name";
	private static String SURNAME = "surname";
	private static String PASSWORD = "password";

	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ USER_NAME + " TEXT,"
				+ NAME + " TEXT,"
				+ SURNAME + " TEXT,"
				+ PASSWORD + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}


	
	public void UserEkle(String user_name, String name,String surname,String password) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_NAME, user_name);
		values.put(NAME, name);
		values.put(SURNAME, surname);
		values.put(PASSWORD, password);

		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	
	public HashMap<String, String> getUser(String username,String pass){
		
		HashMap<String,String> kitap = new HashMap<String,String>();
		String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE username='"+username +"' and password='"+pass+"'";
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	kitap.put(USER_NAME, cursor.getString(1));
        	kitap.put(NAME, cursor.getString(2));
        	kitap.put(SURNAME, cursor.getString(3));
        	kitap.put(PASSWORD, cursor.getString(4));
        }
        cursor.close();
        db.close();
		return kitap;
	}
	
	public  ArrayList<HashMap<String, String>> users(){

		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);
	    ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();
	    
	    if (cursor.moveToFirst()) {
	        do {
	            HashMap<String, String> map = new HashMap<String, String>();
	            for(int i=0; i<cursor.getColumnCount();i++)
	            {
	                map.put(cursor.getColumnName(i), cursor.getString(i));
	            }
	
	            kitaplist.add(map);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    return kitaplist;
	}

	
	public int getRowCount() {

		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}
	

	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
