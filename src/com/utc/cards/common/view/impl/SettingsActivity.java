package com.utc.cards.common.view.impl;

import static com.utc.cards.Constants.HOST_IP;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.utc.cards.R;

public class SettingsActivity extends Activity
{

    EditText hostField;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String host = settings.getString(HOST_IP,
		settings.getString(LOCAL_IP, ""));
	setContentView(R.layout.activity_settings);
	hostField = (EditText) findViewById(R.id.edit_host);
	hostField.setText(host);

	Button button = (Button) findViewById(R.id.button_save);
	button.setOnClickListener(buttonSaveListener);
    }

    private OnClickListener buttonSaveListener = new OnClickListener() {
	public void onClick(View v)
	{
	    SharedPreferences settings = getSharedPreferences(
		    JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(HOST_IP, hostField.getText().toString());
	    editor.commit();
	    finish();
	}
    };

}
