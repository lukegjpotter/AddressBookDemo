package net.lukegjpotter.app.addressbookdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddEditContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_edit_contact, menu);
		return true;
	}

}