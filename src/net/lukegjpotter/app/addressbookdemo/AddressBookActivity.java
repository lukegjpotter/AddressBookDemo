package net.lukegjpotter.app.addressbookdemo;

/**
 *  AddressBookActivity.java
 * 
 *  @author Luke Potter
 *  Date: 21/Feb/2013
 * 
 *  This class displays a list view of contacts.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class AddressBookActivity extends ListActivity {

	public static final String ROW_ID = "row_id"; // Extra key for Intent; passed between activities.
	private ListView contactListView;             // The ListActivity's ListView; can interact with it programmatically
	private CursorAdapter contactAdapter;         // Adapter for ListView; populates the AddressBook's ListActivity
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		contactListView = getListView(); // Get the in built ListView
		contactListView.setOnItemClickListener(viewContactListner);
	}

	
	OnItemClickListener viewContactListner = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
			// Create the Intent to launch the ViewContact Activity
			Intent viewContect = new Intent(AddressBookActivity.this, ViewContactActivity.class);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present. <- Cool Story Brah!
		getMenuInflater().inflate(R.menu.activity_address_book, menu);
		
		return true;
	}

}
