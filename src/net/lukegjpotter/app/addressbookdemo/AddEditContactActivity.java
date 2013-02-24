package net.lukegjpotter.app.addressbookdemo;

/**
 *  AddEditContactActivity.java
 * 
 *  @author Luke Potter
 *  @date: 24/Feb/2013
 * 
 *  This class allows the user to add a new contact, or edit an existing contact.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditContactActivity extends Activity {

	// Variables
	private long rowID; // ID of the contact being edited.
	private EditText nameEditText;
	private EditText phoneEditText;
	private EditText emailEditText;
	private EditText streetEditText;
	private EditText cityEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_contact);
		
		initialiseViews();
		
		Bundle extras = getIntent().getExtras();
		
		checkIfTheresExtras(extras);
		
		// Set an event listener for the save contact button.
		Button saveContactButton = (Button) findViewById(R.id.saveContactButton);
		saveContactButton.setOnClickListener(saveContactButtonListener);
	}


	/**
	 * Initialise the views from the XML file.
	 */
	private void initialiseViews() {
		
		nameEditText   = (EditText) findViewById(R.id.nameEditText);
		phoneEditText  = (EditText) findViewById(R.id.phoneEditText);
		emailEditText  = (EditText) findViewById(R.id.emailEditText);
		streetEditText = (EditText) findViewById(R.id.streetEditText);
		cityEditText   = (EditText) findViewById(R.id.cityEditText);
	}

	/**
	 * A method that checks the Bundle for extras, and sets the views if there's Extras.
	 * @param extras 
	 */
	private void checkIfTheresExtras(Bundle extras) {
		
		if (extras != null) {
			
			rowID = extras.getLong("row_id");
			nameEditText.setText(extras.getString("name"));
			phoneEditText.setText(extras.getString("phone"));
			emailEditText.setText(extras.getString("email"));
			streetEditText.setText(extras.getString("street"));
			cityEditText.setText(extras.getString("city"));
		}
	}
	
	/**
	 * Responds to event generated when user clicks the Done/Save Button.
	 */
	OnClickListener saveContactButtonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			// Check for an empty name.
			if (nameEditText.getText().length() != 0) {
				
				AsyncTask<Object, Object, Object> saveContactTask = new AsyncTask<Object, Object, Object>() {

					@Override
					protected Object doInBackground(Object... params) {
						
						saveContact(); // Save the contact to the database.
						
						return null;
					}

					@Override
					protected void onPostExecute(Object result) {
						
						finish(); // Return to the previous activity.
					}
				};
				
				saveContactTask.execute((Object[]) null);
			} else {
				
				// Create a new AlertDialog Builder
				AlertDialog.Builder builder = new AlertDialog.Builder(AddEditContactActivity.this);
				
				// Set the title and message, and provide a button to dismiss.
				builder.setTitle(R.string.errorTitle);
				builder.setMessage(R.string.errorMessage);
				builder.setPositiveButton(R.string.errorButton, null);
				
				builder.show();
			}
		} // End method onClick.
	}; // End onClickListener definition.

	/**
	 * Saves the contact's information to the database.
	 */
	private void saveContact() {
		
		DatabaseConnector dc = new DatabaseConnector(this);
		
		if (getIntent().getExtras() == null) { // New contact.
			
			// Insert information into the database.
			dc.insertContact(nameEditText.getText().toString(),
					phoneEditText.getText().toString(),
					emailEditText.getText().toString(),
					streetEditText.getText().toString(),
					cityEditText.getText().toString());
		} else { // Existing contact to update.
			
			// Update contact in the database.
			dc.updateContact(rowID,
					nameEditText.getText().toString(),
					phoneEditText.getText().toString(),
					emailEditText.getText().toString(),
					streetEditText.getText().toString(),
					cityEditText.getText().toString());
		}
	}
}
