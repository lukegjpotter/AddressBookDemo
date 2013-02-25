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
	public void open() throws SQLException {
		
		// Creates or Opens a database for reading/writing
		database = databaseOpenHelper.getWritableDatabase();
	}

	public Cursor getAllContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Close the database connection.
	 */
	public void close() {
		
		if (database != null) {
			
			database.close();
		}
	}

	public Cursor getOneContact(Long rowId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteContact(Long rowId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Inserts a new contact in the database.
	 * 
	 * @param name
	 * @param phone
	 * @param email
	 * @param street
	 * @param city
	 */
	public void insertContact(String name, String phone, String email,
			String street, String city) {
		
		ContentValues newContact = new ContentValues();
		newContact.put("name", name);
		newContact.put("phone", phone);
		newContact.put("email", email);
		newContact.put("street", street);
		newContact.put("city", city);
		
		open();
		database.insert("contacts", null, newContact);
		close();
	}

	/**
	 * Updates a contact in the database.
	 * 
	 * @param rowID
	 * @param name
	 * @param phone
	 * @param email
	 * @param street
	 * @param city
	 */
	public void updateContact(long rowID, String name, String phone,
			String email, String street, String city) {
		
		ContentValues editContact = new ContentValues();
		editContact.put("name", name);
		editContact.put("phone", phone);
		editContact.put("email", email);
		editContact.put("street", street);
		editContact.put("city", city);
		
		open();
		database.update("contacts", editContact, "_id=" + rowID, null);
		close();
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
