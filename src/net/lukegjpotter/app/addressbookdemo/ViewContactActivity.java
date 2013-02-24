package net.lukegjpotter.app.addressbookdemo;

/**
 *  ViewContactActivity.java
 * 
 *  @author Luke Potter
 *  @date: 24/Feb/2013
 * 
 *  This class displays the data belonging to one contact.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewContactActivity extends Activity {

	// Variables
	private long rowID;              // Selected contact's name.
	private TextView nameTextView;   // Displays Contact's name.
	private TextView phoneTextView;  // Displays Contact's phone number.
	private TextView emailTextView;  // Displays Contact's email address.
	private TextView streetTextView; // Displays Contact's street address.
	private TextView cityTextView;   // Displays Contact's city/state/zip code.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contact);
		
		// Get the TextViews
		InitialiseViews();
		
		// Get the selected contact's rotID.
		Bundle extras = getIntent().getExtras();
		rowID = extras.getLong("row_id");
	}

	/**
	 * Function to initialise the TextViews from the XML layout.
	 */
	private void InitialiseViews() {
		
		nameTextView   = (TextView) findViewById(R.id.nameTextView);
		phoneTextView  = (TextView) findViewById(R.id.phoneTextView);
		emailTextView  = (TextView) findViewById(R.id.emailTextView);
		streetTextView = (TextView) findViewById(R.id.streetTextView);
		cityTextView   = (TextView) findViewById(R.id.cityTextView);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		
		// Create new LoadContactsTask and execute it.
		new LoadContactsTask().execute(rowID);
	}
	
	/**
	 * An anonymous class that performs database access outside the GUI thread.
	 */
	private class LoadContactsTask extends AsyncTask<Long, Object, Cursor> {

		DatabaseConnector dc = new DatabaseConnector(ViewContactActivity.this);
		
		// Perform the database access
		@Override
		protected Cursor doInBackground(Long... params) {
			
			dc.open();
			
			// Get a cursor containing all data on one given entry.
			return dc.getOneContact(params[0]);
		}
		
		// Use the Cursor returned from the doInBackground method
		@Override
		protected void onPostExecute(Cursor result) {
			
			super.onPostExecute(result);
			
			result.moveToFirst(); // Move the cursor to the first item.
			
			// Fill the TextViews with the column indices form the retrieved data.
			nameTextView.setText(result.getString(result.getColumnIndex("name")));
			phoneTextView.setText(result.getString(result.getColumnIndex("phone")));
			emailTextView.setText(result.getString(result.getColumnIndex("email")));
			streetTextView.setText(result.getString(result.getColumnIndex("street")));
			cityTextView.setText(result.getString(result.getColumnIndex("city")));
			
			result.close(); // Close the cursor
			dc.close(); // Close the database connection
		}
	}
	
	/**
	 * Creates the Options menu from a menu resource XML file.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_contact, menu);
		
		return true;
	}

	
	/**
	 * Handle the choice from the Options menu.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Switch to the MenuItem that was selected.
		switch (item.getItemId()) {
			
			case R.id.editItem:
				
				// Create an Intent to launch the AddEditContact Activity
				Intent addEditContactIntent = new Intent(this, AddEditContactActivity.class);
				
				// Pass the selected contact's data as extras with the intent.
				addEditContactIntent.putExtra("row_id", rowID);
				addEditContactIntent.putExtra("name", nameTextView.getText());
				addEditContactIntent.putExtra("phone", phoneTextView.getText());
				addEditContactIntent.putExtra("email", emailTextView.getText());
				addEditContactIntent.putExtra("street", streetTextView.getText());
				addEditContactIntent.putExtra("city", cityTextView.getText());
				
				//Start the activity
				startActivity(addEditContactIntent);
				return true;
			
			case R.id.deleteItem:
				
				// Delete the contact.
				deleteContact();
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A function that deletes the contact being viewed.
	 */
	private void deleteContact() {
		
		// Create a new AlertDialog Builder
		AlertDialog.Builder builder = new AlertDialog.Builder(ViewContactActivity.this);
		builder.setTitle(R.string.confirmTitle);
		builder.setMessage(R.string.confirmMessage);
		
		// Provide an OK button; that simply dismisses the dialog.
		builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int button) {
				
				final DatabaseConnector dc = new DatabaseConnector(ViewContactActivity.this);
				
				// Create an AsyncTask that that deletes the contact from the database.
				AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>() {

					@Override
					protected Object doInBackground(Long... params) {
						
						dc.deleteContact(params[0]);
						
						return null;
					}
					
					@Override
					protected void onPostExecute(Object result) {
						
						finish(); // Return to the AddressBook Activity.
					}
				};
				
				// Execute the delete AsyncTask to delete the contact at rowID.
				deleteTask.execute(new Long[] {rowID});
			} // End onClickListener
		}); // End call to setPositiveButton.
		
		builder.setNegativeButton(R.string.button_cancel, null);
		
		// Display the Dialog
		builder.show();
	}

}
