package com.utc.cards.player.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.common.view.CardsContainer;
import com.utc.cards.model.game.InfoType;
import com.utc.cards.utils.Utils;

public abstract class AbstractPlayerGameActivity extends Activity implements IPlayerGameActivity
{

    private static Logger log = LoggerFactory.getLogger(AbstractPlayerGameActivity.class);
    // private MyReceiver myReceiver;

    private CardsContainer _cardsContainer;
    private Point _screenDimention = new Point();

    protected MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getLayout());

        onCreateSpecificView(savedInstanceState);
    }

    public abstract void onCreateSpecificView(Bundle savedInstanceState);

    public abstract void drawGameCards();

    public abstract void getScreenSize();

    public abstract void drawSendingPanel();

    public abstract void drawSeekBar();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getMenu(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_clue:
            _cardsContainer.centerCard("Club_4");
            break;

        default:
            break;
        }

        return true;
    }

    // private void getScreenSize()
    // {
    // Display display = getWindowManager().getDefaultDisplay();
    // display.getSize(_screenDimention);
    // }

    // protected void drawCards(List<Card> cards, int nbMaxCardsMain)
    // {
    // final View view = findViewById(R.id.cardsLayout);
    // final View viewSendingLayout = findViewById(R.id.sendingLayout);
    //
    // if (view != null)
    // {
    // List<CardView> cardViews = new ArrayList<CardView>();
    //
    // if (_screenDimention.y > 0)
    // {
    // // Set the cards dimension according to the size of the display
    // // screen
    // int w = (int) ((_screenDimention.x * 1.6 * nbMaxCardsMain) / 100);
    // CardView.CARD_WIDTH = w;
    //
    // for (Card card : cards)
    // {
    // cardViews.add(new CardView(card, card.getResourceId(), view.getContext()));
    // }
    //
    // // Set margins
    //
    // RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    //
    // lp.addRule(RelativeLayout.BELOW, viewSendingLayout.getId());
    //
    // lp.setMargins(0, 20, 0, 0);
    //
    // view.setLayoutParams(lp);
    //
    // // The container of the cards
    //
    // CardsContainer.CARD_DISTANCE = ((_screenDimention.x - 2 * CardsContainer.STARTING_X - CardView.CARD_WIDTH) / (nbMaxCardsMain - 1));
    //
    // _cardsContainer = new CardsContainer((RelativeLayout) view, _screenDimention, cardViews);
    // }
    // }
    // }

    // protected void drawSendingPanel()
    // {
    // final View view = findViewById(R.id.sendingLayout);
    //
    // if (view != null)
    // {
    // if (_screenDimention.y > 0)
    // {
    // int h = (int) (_screenDimention.y * 15 / 100);
    // view.setMinimumHeight(h);
    // }
    //
    // view.setBackgroundColor(android.graphics.Color.GRAY);
    // view.setOnDragListener(new SendDragListener(_cardsContainer));
    // }
    // }

    // protected void drawSeekBar()
    // {
    // final View view = findViewById(R.id.seekBar);
    //
    // if (view != null)
    // {
    // if (_screenDimention.y > 0)
    // {
    // int h = (int) (_screenDimention.y * 15 / 100);
    // view.setMinimumHeight(h);
    // }
    //
    // SeekBar bar = (SeekBar) view;
    //
    // bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
    // {
    //
    // @Override
    // public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
    // {
    // // When the user changes the progress value, we change the
    // // distance between cards
    //
    // // Length of the player's hand
    // int lengthHand = ((_cardsContainer.getCards().size() - 1) * progress + CardView.CARD_WIDTH);
    //
    // if (lengthHand <= (_screenDimention.x - 2 * CardsContainer.STARTING_X))
    // {
    // CardsContainer.CARD_DISTANCE = progress;
    // }
    //
    // _cardsContainer.refresh();
    // }
    //
    // @Override
    // public void onStartTrackingTouch(SeekBar arg0)
    // {
    // }
    //
    // @Override
    // public void onStopTrackingTouch(SeekBar arg0)
    // {
    // }
    //
    // });
    // }
    // }

    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, Intent data)
    // {
    // // if (requestCode == PARTICIPANTS_REQUEST) {
    // // if (resultCode == RESULT_OK) {
    // // // TODO: A partecipant was picked. Send a private message.
    // // }
    // // }
    // // }
    //
    //
    // }

    protected abstract int getMenu();

    public abstract int getLayout();

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        log.debug("Destroy activity!");
    }

    private class MyReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            log.info("Received intent " + action);
            if (action.equalsIgnoreCase(Constants.POP_INFO))
            {
                Utils.showAlertDialog(getThis(), intent.getStringExtra(InfoType.INFO.name()), false);
            }
        }

    }

    public abstract Activity getThis();

    private void registerReceivers()
    {
        myReceiver = new MyReceiver();
        IntentFilter showChatFilter = new IntentFilter();
        showChatFilter.addAction(Constants.POP_INFO);
        registerReceiver(myReceiver, showChatFilter);
    }
}
