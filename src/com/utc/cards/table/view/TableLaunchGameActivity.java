package com.utc.cards.table.view;

import com.utc.cards.R;
import com.utc.cards.R.layout;
import com.utc.cards.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TableLaunchGameActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_launch_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.launch_game, menu);
	return true;
    }

}
