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
	private final String contactsTable = "contacts"; // Specify a table name
	private SQLiteDatabase database;                 // Database Object.
	private DatabaseOpenHelper databaseOpenHelper;   // Database Helper.
	
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

	/**
	 * Return a Cursor with all the contact information in the database.
	 * 
	 * @return databaseCursor
	 */
	public Cursor getAllContacts() {

		return database.query(contactsTable, new String[] {"_id", "name"}, null, null, null, null, "name");
	}

	/**
	 * Close the database connection.
	 */
	public void close() {
		
		if (database != null) {
			
			database.close();
		}
	}

	/**
	 * Get a Cursor containing all the information belonging to one contact.
	 * The contact is specified by the given ID.
	 * 
	 * @param rowId
	 * @return contactCursor
	 */
	public Cursor getOneContact(Long rowId) {
		
		return database.query(contactsTable, null, "_id=" + rowId, null, null, null, null);
	}

	/**
	 * Delete a contact from the database.
	 * The contact is specified by the given ID.
	 * 
	 * @param rowId
	 */
	public void deleteContact(Long rowId) {
		
		open();
		database.delete(contactsTable, "_id=" + rowId, null);
		close();
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
		database.insert(contactsTable, null, newContact);
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
		database.update(contactsTable, editContact, "_id=" + rowID, null);
		close();
	}

	/**
	 * Private nested class DatabaseOpenHelper.
	 * 
	 * @author lukepotter
	 * @date 25/Feb/2013
	 *
	 */
	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		/**
		 * Public constructor for DatabaseOpenHelper.
		 * 
		 * @param context
		 * @param name
		 * @param factory
		 * @param version
		 */
		public DatabaseOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			
			super(context, name, factory, version);
		}

		/**
		 * Creates the contacts table when the database is created.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			// Query to create a new table named contacts.
			String createQuery = "CREATE TABLE " + contactsTable
					+ "(_id integer primary key autoincrement, "
					+ "name TEXT, "
					+ "email TEXT, "
					+ "phone TEXT, "
					+ "street TEXT, "
					+ "city TEXT);";
			
			// Execute the create table query.
			db.execSQL(createQuery);
		}

		/**
		 * A stub method for the upgrade of the database.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			// Do nothing.
		}
		
	}
}
