package com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.errorlab.widget.CheckableFloatingActionButton;

public class MainActivity extends AppCompatActivity
{
	static final String STATE_FAB = "fabState";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
    }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Let's save the state of the fab
		// CheckableFloatingActionButton fab = (CheckableFloatingActionButton) findViewById(R.id.fab);
		// savedInstanceState.putParcelable(STATE_FAB, fab);
    
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}
}

