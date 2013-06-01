package com.utc.cards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Application;
import android.content.SharedPreferences;

public class CardsApplication extends Application
{
    private Logger log = LoggerFactory.getLogger(CardsApplication.class);

    @Override
    public void onCreate()
    {
	super.onCreate();

	SharedPreferences settings = getSharedPreferences("jadeChatPrefsFile",
		0);

	String defaultHost = settings.getString("defaultHost", "");
	String defaultPort = settings.getString("defaultPort", "");
	if (defaultHost.isEmpty() || defaultPort.isEmpty())
	{
	    log.info("Create default properties");
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("defaultHost", "10.0.2.2");
	    editor.putString("defaultPort", "1099");
	    editor.commit();
	}
    }
}
