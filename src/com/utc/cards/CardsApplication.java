package com.utc.cards;

import static com.utc.cards.Constants.DEFAULT_JADE_PORT;
import static com.utc.cards.Constants.HOST_IP;
import static com.utc.cards.Constants.HOST_PORT;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.utc.cards.utils.Utils;

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
	log.info(HOST_IP + " = " + localIpAddress);
	SharedPreferences.Editor editor = settings.edit();
	editor.putString(HOST_PORT, DEFAULT_JADE_PORT);
	editor.putString(LOCAL_IP, localIpAddress);
	editor.putString(HOST_IP, settings.getString(HOST_IP, localIpAddress));
	editor.commit();

    }

}
