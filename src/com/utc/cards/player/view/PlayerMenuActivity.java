package com.utc.cards.player.view;

import static com.utc.cards.Constants.GMAIL;
import static com.utc.cards.Constants.HOST_IP;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;
import jade.core.MicroRuntime;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.player.jade.agent.playerAgent.IPlayerAgent;
import com.utc.cards.player.jade.agent.playerHelperAgent.IPlayerHelperAgent;
import com.utc.cards.utils.Utils;

public class PlayerMenuActivity extends Activity
{

    private class AgentStartListener implements AgentActivityListener
    {

	@Override
	public void onAllAgentsReady()
	{
	    // mHandler to update view from another thread
	    mHandler.post(new Runnable() {

		@Override
		public void run()
		{
		    log.info("onAllAgentsReady");
		    final Button button = (Button) PlayerMenuActivity.this
			    .findViewById(R.id.joinButton);
		    button.setEnabled(true);
		    // tous les agents sont prets
		    // on initialise les interfaces pour pouvoir les utiliser
		    loadPlayerAgent();
		    loadPlayerHelperAgent();
		}

	    });

	}
    }

    private static Logger log = LoggerFactory
	    .getLogger(PlayerMenuActivity.class);

    private Handler mHandler = new Handler();
    private IPlayerAgent playerAgent;
    private IPlayerHelperAgent playerHelperAgent;

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
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	setContentView(R.layout.activity_player_menu);
	// if (AndroidHelper.isEmulator())
	// {
	// // Emulator: this is needed to work with emulated devices
	// editText.setText(AndroidHelper.LOOPBACK);
	// } else
	// {

	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String localHost = settings.getString(LOCAL_IP, "");
	// Ce n'est pas la bonne adresse, mais comme il faut être sur le même
	// réseau on peut afficher la même adresse ip pour la corriger plus
	// rapidement
	String localIpAddress = localHost;

	final EditText hostIpAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
	hostIpAddressEditText.setText(localIpAddress);

	// }
	//
	// myReceiver = new MyReceiver();
	// IntentFilter showChatFilter = new IntentFilter();
	// showChatFilter.addAction("jade.demo.chat.SHOW_CHAT");
	// registerReceiver(myReceiver, showChatFilter);
	String gmailAddress = settings.getString(GMAIL, "");
	final TextView googleAccountTextView = (TextView) findViewById(R.id.googleAccountTextView);
	googleAccountTextView.setText("Google Account : " + gmailAddress);
    }

    public void connect(View view)
    {
	final EditText ipAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
	String hostIp = ipAddressEditText.getText().toString();

	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String localIp = settings.getString(LOCAL_IP, "");

	boolean ipsValids = Utils.validateIps(hostIp, localIp);

	if (ipsValids)
	{
	    log.info("PlayerMenuActivity.connect : ipsValids (local: "
		    + localIp + ", host: " + hostIp + ")");
	    log.info("PlayerMenuActivity.connect : try starting jade platform");

	    log.info("JADE_CARDS_PREFS_FILE :");
	    log.info(HOST_IP + " = " + hostIp);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(HOST_IP, hostIp);
	    editor.commit();

	    // auto add some behaviour in agent.startup()
	    PlayerAgentManager.instance().startAgents(this,
		    settings.getString(GMAIL, ""), new AgentStartListener());
	} else
	{
	    log.info("ips invalid");
	}

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

    private void showAlertDialog(String message, final boolean fatal)
    {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage(message).setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id)
		    {
			dialog.cancel();
			if (fatal)
			    finish();
		    }
		});
	AlertDialog alert = builder.create();
	alert.show();
    }

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

    private void loadPlayerAgent()
    {
	try
	{
	    SharedPreferences settings = getSharedPreferences(
		    JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	    playerAgent = MicroRuntime.getAgent(
		    Constants.CARDS_PLAYER_AGENT_NAME + "-"
			    + settings.getString(GMAIL, "")).getO2AInterface(
		    IPlayerAgent.class);
	    log.debug("playerAgent loaded !");
	} catch (StaleProxyException e)
	{
	    showAlertDialog(getString(R.string.msg_interface_exc), true);
	} catch (ControllerException e)
	{
	    showAlertDialog(getString(R.string.msg_controller_exc), true);
	}
    }

    private void loadPlayerHelperAgent()
    {
	try
	{
	    SharedPreferences settings = getSharedPreferences(
		    JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	    playerHelperAgent = MicroRuntime.getAgent(
		    Constants.CARDS_PLAYER_HELPER_AGENT_NAME + "-"
			    + settings.getString(GMAIL, "")).getO2AInterface(
		    IPlayerHelperAgent.class);
	    log.debug("playerHelperAgent loaded !");
	} catch (StaleProxyException e)
	{
	    showAlertDialog(getString(R.string.msg_interface_exc), true);
	} catch (ControllerException e)
	{
	    showAlertDialog(getString(R.string.msg_controller_exc), true);
	}
    }
}
