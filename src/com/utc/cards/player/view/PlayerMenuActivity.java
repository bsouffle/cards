package com.utc.cards.player.view;

import jade.android.AndroidHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.utc.cards.R;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.player.jade.PlayerAgentManager;

public class PlayerMenuActivity extends Activity
{

    private static Logger _log = LoggerFactory
	    .getLogger(PlayerMenuActivity.class);

    // private MyReceiver myReceiver;
    //
    // private CardsContainer _cardsContainer;
    //
    // private PlayerAgentManager agentManager = PlayerAgentManager.instance();
    // private Point _screenDimention = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_player_menu);
	final EditText editText = (EditText) findViewById(R.id.ipAddressEditText);
//	if (AndroidHelper.isEmulator())
//	{	    
//	    // Emulator: this is needed to work with emulated devices
//	    editText.setText(AndroidHelper.LOOPBACK);
//	} else
//	{
	 WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	      
	       WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
	       int myIp = myWifiInfo.getIpAddress();
	       int intMyIp3 = myIp/0x1000000;
	       int intMyIp3mod = myIp%0x1000000;
	      
	       int intMyIp2 = intMyIp3mod/0x10000;
	       int intMyIp2mod = intMyIp3mod%0x10000;
	      
	       int intMyIp1 = intMyIp2mod/0x100;
	       int intMyIp0 = intMyIp2mod%0x100;
	      
	       editText.setText(String.valueOf(intMyIp0)
	         + "." + String.valueOf(intMyIp1)
	         + "." + String.valueOf(intMyIp2)
	         + "." + String.valueOf(intMyIp3)
	         );
	   // editText.setText(AndroidHelper.getLocalIPAddress());
//	}
	//
	// myReceiver = new MyReceiver();
	// IntentFilter showChatFilter = new IntentFilter();
	// showChatFilter.addAction("jade.demo.chat.SHOW_CHAT");
	// registerReceiver(myReceiver, showChatFilter);

    }

    public void connect(View view)
    {
	// validate ip address
	
	_log.info("PlayerMenuActivity.connect : try starting jade platform");
	PlayerAgentManager.instance().startAgents(this,
		new AgentActivityListener() {

		    @Override
		    public void onAllAgentsReady()
		    {
			_log.info("onAllAgentsReady");
			final Button button = (Button) findViewById(R.id.runGameButton);
			button.setEnabled(true);
			// TODO
			// get available games list and display it
			// later : enable join button
		    }
		});
    }

    public void join(View view)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.player_menu, menu);
	return true;
    }

    //
    // private void getScreenSize() {
    // Display display = getWindowManager().getDefaultDisplay();
    // display.getSize(_screenDimention);
    // }
    //
    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // // if (requestCode == PARTICIPANTS_REQUEST) {
    // // if (resultCode == RESULT_OK) {
    // // // TODO: A partecipant was picked. Send a private message.
    // // }
    // // }
    // // }
    //
    // //
    // // private class MyReceiver extends BroadcastReceiver {
    // //
    // // @Override
    // // public void onReceive(Context context, Intent intent) {
    // // String action = intent.getAction();
    // // log.info("Received intent " + action);
    // // if (action.equalsIgnoreCase("jade.demo.chat.KILL")) {
    // // finish();
    // // }
    // // // if (action.equalsIgnoreCase("jade.demo.chat.SHOW_CHAT")) {
    // // // Intent showChat = new Intent(MainActivity.this,
    // // // DameDePiquePlayerActivity.class);
    // // // showChat.putExtra("nickname", nickname);
    // // // MainActivity.this
    // // // .startActivityForResult(showChat, CHAT_REQUEST);
    // // // }
    // // }
    // }
}
