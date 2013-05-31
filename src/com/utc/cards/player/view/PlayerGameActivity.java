package com.utc.cards.player.view;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.CardsContainer;
import com.utc.cards.common.view.listener.SendDragListener;
import com.utc.cards.games.damedepique.DameDePique;
import com.utc.cards.model.Card;
import com.utc.cards.model.game.AbstractGame;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.player.jade.PlayerAgentManager;

public abstract class PlayerGameActivity<T extends IGame> extends Activity
	implements IPlayerGameActivity<T>
{

    private static Logger _log = LoggerFactory
	    .getLogger(PlayerGameActivity.class);
    // private MyReceiver myReceiver;

    private CardsContainer _cardsContainer;

    private PlayerAgentManager _agentManager = PlayerAgentManager.instance();
    private Point _screenDimention = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_main);

	_agentManager.startAgents(this, new AgentActivityListener() {

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
	//
	// myReceiver = new MyReceiver();
	// IntentFilter showChatFilter = new IntentFilter();
	// showChatFilter.addAction("jade.demo.chat.SHOW_CHAT");
	// registerReceiver(myReceiver, showChatFilter);

	// Get the size of the display screen
	getScreenSize();

	drawGameCards();

	// Set the panel used to send cards to the host
	setSendingPanel();

	// Set the bar which allows to modify the distance between two cards
	setSeekBar();
    }

    public abstract void drawGameCards();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.mainmenu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId()) {
	case R.id.action_clue:
	    _cardsContainer.centerCard("Club_4");
	    break;

	default:
	    break;
	}

	return true;
    }

    private void getScreenSize()
    {
	Display display = getWindowManager().getDefaultDisplay();
	display.getSize(_screenDimention);
    }

    protected void drawCards(List<Card> cards)
    {
	final View view = findViewById(R.id.cardsLayout);

	if (view != null)
	{

	    List<CardView> cardViews = new ArrayList<CardView>();

	    for (Card card : cards)
	    {
		cardViews.add(new CardView(card, card.getResourceId(), view
			.getContext()));
	    }

	    if (_screenDimention.y > 0)
	    {
		// Set the cards dimension according to the size of the display
		// screen
		int w = (int) (_screenDimention.x * 21 / 100);
		CardView.CARD_WIDTH = w;

		// Set margins
		int m = (int) (_screenDimention.y * 3 / 100);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);

		lp.setMargins(0, m, 0, m);
		view.setLayoutParams(lp);

		// The container of the cards
		_cardsContainer = new CardsContainer((RelativeLayout) view,
			_screenDimention, cardViews);
	    }
	}
    }

    private void setSendingPanel()
    {
	final View view = findViewById(R.id.sendingLayout);

	if (view != null)
	{
	    if (_screenDimention.y > 0)
	    {
		int h = (int) (_screenDimention.y * 10 / 100);
		view.setMinimumHeight(h);
	    }

	    view.setBackgroundColor(android.graphics.Color.GRAY);
	    view.setOnDragListener(new SendDragListener(_cardsContainer));
	}
    }

    private void setSeekBar()
    {
	final View view = findViewById(R.id.seekBar);

	if (view != null)
	{
	    SeekBar bar = (SeekBar) view;

	    bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar arg0, int progress,
			boolean arg2)
		{
		    // When the user changes the progress value, we change the
		    // distance between cards
		    CardsContainer.CARD_DISTANCE = progress;
		    _cardsContainer.refresh();
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0)
		{
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0)
		{
		}

	    });
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	// if (requestCode == PARTICIPANTS_REQUEST) {
	// if (resultCode == RESULT_OK) {
	// // TODO: A partecipant was picked. Send a private message.
	// }
	// }
	// }

	//
	// private class MyReceiver extends BroadcastReceiver {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// String action = intent.getAction();
	// log.info("Received intent " + action);
	// if (action.equalsIgnoreCase("jade.demo.chat.KILL")) {
	// finish();
	// }
	// // if (action.equalsIgnoreCase("jade.demo.chat.SHOW_CHAT")) {
	// // Intent showChat = new Intent(MainActivity.this,
	// // DameDePiquePlayerActivity.class);
	// // showChat.putExtra("nickname", nickname);
	// // MainActivity.this
	// // .startActivityForResult(showChat, CHAT_REQUEST);
	// // }
	// }
    }
}
