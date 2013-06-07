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
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.utc.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.CardsContainer;
import com.utc.cards.common.view.listener.SendDragListener;
import com.utc.cards.model.card.Card;
import com.utc.cards.player.jade.PlayerAgentManager;

public abstract class AbstractPlayerGameActivity extends Activity implements
	IPlayerGameActivity
{

    private static Logger _log = LoggerFactory
	    .getLogger(AbstractPlayerGameActivity.class);
    // private MyReceiver myReceiver;

    private CardsContainer _cardsContainer;

    private PlayerAgentManager _agentManager = PlayerAgentManager.instance();
    private Point _screenDimention = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	// Get the size of the display screen
	getScreenSize();

	onCreateHook(savedInstanceState);

    }

    public abstract void onCreateHook(Bundle savedInstanceState);

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

    protected void drawCards(List<Card> cards, int nbMaxCardsMain)
    {
	System.out.println("TEST 1");
	final View view = findViewById(R.id.cardsLayout);
	final View viewSendingLayout = findViewById(R.id.sendingLayout);

	if (view != null)
	{
	    System.out.println("TEST 2");
	    List<CardView> cardViews = new ArrayList<CardView>();

	    if (_screenDimention.y > 0)
	    {
		// Set the cards dimension according to the size of the display
		// screen
		int w = (int) ((_screenDimention.x * 1.6 * nbMaxCardsMain) / 100);
		CardView.CARD_WIDTH = w;

		for (Card card : cards)
		{
		    cardViews.add(new CardView(card, card.getResourceId(), view
			    .getContext()));
		}

		// Set margins

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.MATCH_PARENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);

		lp.addRule(RelativeLayout.BELOW, viewSendingLayout.getId());

		lp.setMargins(0, 20, 0, 0);

		view.setLayoutParams(lp);

		// The container of the cards

		CardsContainer.CARD_DISTANCE = ((_screenDimention.x - 2
			* CardsContainer.STARTING_X - CardView.CARD_WIDTH) / (nbMaxCardsMain - 1));

		_cardsContainer = new CardsContainer((RelativeLayout) view,
			_screenDimention, cardViews);

		System.out.println("_cardsssssssssssssssss : "
			+ _cardsContainer);
	    }
	}
    }

    protected void drawSendingPanel()
    {
	final View view = findViewById(R.id.sendingLayout);

	if (view != null)
	{
	    if (_screenDimention.y > 0)
	    {
		int h = (int) (_screenDimention.y * 15 / 100);
		view.setMinimumHeight(h);
	    }

	    view.setBackgroundColor(android.graphics.Color.GRAY);
	    view.setOnDragListener(new SendDragListener(_cardsContainer));
	}
    }

    protected void drawSeekBar()
    {
	final View view = findViewById(R.id.seekBar);

	if (view != null)
	{
	    if (_screenDimention.y > 0)
	    {
		int h = (int) (_screenDimention.y * 15 / 100);
		view.setMinimumHeight(h);
	    }

	    SeekBar bar = (SeekBar) view;

	    bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar arg0, int progress,
			boolean arg2)
		{
		    // When the user changes the progress value, we change the
		    // distance between cards

		    // Length of the player's hand
		    int lengthHand = ((_cardsContainer.getCards().size() - 1)
			    * progress + CardView.CARD_WIDTH);

		    if (lengthHand <= (_screenDimention.x - 2 * CardsContainer.STARTING_X))
		    {
			CardsContainer.CARD_DISTANCE = progress;
		    }

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
