package net.lukegjpotter.app.addressbookdemo;

/**
 * 
 * DataaseConnector.java
 * 
 * @author Luke Potter
 * @date 22/Feb/2013
 * 
 * This class provides and easy connection and creation of UserContacts database.
 *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector {

	private static final String DATABASE_NAME = "UserContacts";
	private SQLiteDatabase database;               // Database Object.
	private DatabaseOpenHelper databaseOpenHelper; // Database Helper.
	
	/**
	 * Constructor for DatabaseConnector.
	 * 
	 * @param addressBookActivity 
	 * 
	 */
	public DatabaseConnector(Context context) {
		
		// Create a new DatabaseOpenHelper
		databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
	}

	/**
	 * Open the connection to the database.
	 */
	public void open() {
		
		
	}

	public Cursor getAllContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public Cursor getOneContact(Long rowId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteContact(Long rowId) {
		// TODO Auto-generated method stub
		
	}

	public void insertContact(String name, String phone, String email,
			String street, String city) {
		// TODO Auto-generated method stub
		
	}

	public void updateContact(long rowID, String name, String phone,
			String email, String street, String city) {
		// TODO Auto-generated method stub
		
	}

	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		public DatabaseOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
