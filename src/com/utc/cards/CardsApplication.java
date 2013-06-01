package com.utc.cards;

import static com.utc.cards.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.utils.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * CARDS require an active Wifi connection Player mode also require a
 * GoogleAccount to display user name
 * 
 * @author Arnaud
 * 
 */
public class CardsApplication extends Application
{

    private Logger log = LoggerFactory.getLogger(CardsApplication.class);

    @Override
    public void onCreate()
    {
	super.onCreate();

	String localIpAddress = Utils.getLocalWifiIpAddress(this);

	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	log.info("JADE_CARDS_PREFS_FILE :");
	log.info(HOST_PORT + " = " + DEFAULT_JADE_PORT);
	log.info(LOCAL_IP + " = " + localIpAddress);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString(HOST_PORT, DEFAULT_JADE_PORT);
	editor.putString(LOCAL_IP, localIpAddress);
	editor.commit();

    }

   
}
