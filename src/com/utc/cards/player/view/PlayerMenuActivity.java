package com.utc.cards.player.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;

import com.utc.cards.model.game.IGame;

public abstract class PlayerMenuActivity<T extends IGame> extends Activity implements IPlayerMenuActivity<T> 
{

	private static Logger _log = LoggerFactory.getLogger(PlayerMenuActivity.class);
	// private MyReceiver myReceiver;
//
//	private CardsContainer _cardsContainer;
//
//	private PlayerAgentManager agentManager = PlayerAgentManager.instance();
//	private Point _screenDimention = new Point();
//	private final static int[] CLUB_CARDS_RESOURCE = { R.raw.cards_ac,
//			R.raw.cards_2c, R.raw.cards_3c, R.raw.cards_4c, R.raw.cards_5c,
//			R.raw.cards_6c, R.raw.cards_7c, R.raw.cards_8c, R.raw.cards_9c,
//			R.raw.cards_10c, R.raw.cards_jc, R.raw.cards_qc, R.raw.cards_kc };
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.activity_main);
//		agentManager.startAgents(this, new AgentActivityListener() {
//
//			@Override
//			public void onAllAgentsReady() {
//				log.info("onAllAgentsReady");
//				final Button button = (Button) findViewById(R.id.runGameButton);
//				button.setEnabled(true);
//				// TODO
//				// get available games list and display it
//				// later : enable join button
//			}
//		});
//		//
//		// myReceiver = new MyReceiver();
//		// IntentFilter showChatFilter = new IntentFilter();
//		// showChatFilter.addAction("jade.demo.chat.SHOW_CHAT");
//		// registerReceiver(myReceiver, showChatFilter);
//
//		Deck deck = new Deck();
//		for (int i = 0; i < CLUB_CARDS_RESOURCE.length; i++) {
//			deck.add(new TraditionnalCard("" + CLUB_CARDS_RESOURCE[i],
//					Color.TREFLE, i, CLUB_CARDS_RESOURCE[i]));
//		}
//		// Game g = new DameDePique();
//		// Player p = new Player("Benoit", g);
//		//
//		// p.giveCard(g.get_associatedDesk().getCard(0));
//		// p.giveCard(g.get_associatedDesk().getCard(1));
//		// p.giveCard(g.get_associatedDesk().getCard(2));
//
//		// Get the size of the display screen
//		getScreenSize();
//		List<CardView> cardViews = new ArrayList<CardView>();
//		final View view = findViewById(R.id.cardsLayout);
//		for (Card card : deck) {
//			cardViews.add(new CardView(card, card.getResourceId(), view
//					.getContext()));
//		}
//
//		// Draw some cards
//		drawCards(cardViews);
//
//		// Set the panel used to send cards to the host
//		setSendingPanel();
//
//		// Set the bar which allows to modify the distance between two cards
//		setSeekBar();
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.mainmenu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.action_clue:
//			_cardsContainer.centerCard("Club_4");
//			break;
//
//		default:
//			break;
//		}
//
//		return true;
//	}
//
//	private void getScreenSize() {
//		Display display = getWindowManager().getDefaultDisplay();
//		display.getSize(_screenDimention);
//	}
//
//	private void drawCards(List<CardView> cards) {
//		final View view = findViewById(R.id.cardsLayout);
//
//		if (view != null) {
//			if (_screenDimention.y > 0) {
//				// Set the cards dimension according to the size of the display
//				// screen
//				int w = (int) (_screenDimention.x * 21 / 100);
//				CardView.CARD_WIDTH = w;
//
//				// Set margins
//				int m = (int) (_screenDimention.y * 3 / 100);
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//						LinearLayout.LayoutParams.MATCH_PARENT,
//						LinearLayout.LayoutParams.WRAP_CONTENT);
//				lp.setMargins(0, m, 0, m);
//				view.setLayoutParams(lp);
//
//				// The container of the cards
//				_cardsContainer = new CardsContainer((RelativeLayout) view,
//						_screenDimention, cards);
//			}
//		}
//	}
//
//	private void setSendingPanel() {
//		final View view = findViewById(R.id.sendingLayout);
//
//		if (view != null) {
//			if (_screenDimention.y > 0) {
//				int h = (int) (_screenDimention.y * 10 / 100);
//				view.setMinimumHeight(h);
//			}
//
//			view.setBackgroundColor(android.graphics.Color.GRAY);
//			view.setOnDragListener(new SendDragListener(_cardsContainer));
//		}
//	}
//
//	private void setSeekBar() {
//		final View view = findViewById(R.id.seekBar);
//
//		if (view != null) {
//			SeekBar bar = (SeekBar) view;
//
//			bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//
//				@Override
//				public void onProgressChanged(SeekBar arg0, int progress,
//						boolean arg2) {
//					// When the user changes the progress value, we change the
//					// distance between cards
//					CardsContainer.CARD_DISTANCE = progress;
//					_cardsContainer.refresh();
//				}
//
//				@Override
//				public void onStartTrackingTouch(SeekBar arg0) {
//				}
//
//				@Override
//				public void onStopTrackingTouch(SeekBar arg0) {
//				}
//
//			});
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// if (requestCode == PARTICIPANTS_REQUEST) {
//		// if (resultCode == RESULT_OK) {
//		// // TODO: A partecipant was picked. Send a private message.
//		// }
//		// }
//		// }
//
//		//
//		// private class MyReceiver extends BroadcastReceiver {
//		//
//		// @Override
//		// public void onReceive(Context context, Intent intent) {
//		// String action = intent.getAction();
//		// log.info("Received intent " + action);
//		// if (action.equalsIgnoreCase("jade.demo.chat.KILL")) {
//		// finish();
//		// }
//		// // if (action.equalsIgnoreCase("jade.demo.chat.SHOW_CHAT")) {
//		// // Intent showChat = new Intent(MainActivity.this,
//		// // DameDePiquePlayerActivity.class);
//		// // showChat.putExtra("nickname", nickname);
//		// // MainActivity.this
//		// // .startActivityForResult(showChat, CHAT_REQUEST);
//		// // }
//		// }
//	}
}
