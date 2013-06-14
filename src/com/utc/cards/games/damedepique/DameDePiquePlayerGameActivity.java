package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.utc.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.CardsContainer;
import com.utc.cards.common.view.listener.CardTouchListener;
import com.utc.cards.common.view.listener.SendDragListener;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.AbstractPlayerGameActivity;

public class DameDePiquePlayerGameActivity extends AbstractPlayerGameActivity
{
    private CardsContainer _cardsContainer;
    private Point _screenDimention = new Point();

    @Override
    public void onCreateSpecificView(Bundle savedInstanceState)
    {
        // Get the size of the display screen
        getScreenSize();

        drawGameCards();

        // Set the panel used to send cards to the host
        drawSendingPanel();

        // Set the bar which allows to modify the distance between two cards
        drawSeekBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_clue:
            // _cardsContainer.centerCard("Club_4");
            break;
        // case R.id.game_menu_player:
        // // Intent showSettings = new Intent(this, SettingsActivity.class);
        // // startActivity(showSettings);
        // return true;

        default:
            break;
        }

        return true;
    }

    @Override
    public Activity getThis()
    {
        return this;
    }

    @Override
    public int getLayout()
    {
        // return R.layout.activity_dame_de_pique_player_game;
        return R.layout.activity_main;
    }

    @Override
    protected int getMenu()
    {
        return R.menu.menu_dame_de_pique_game_player;
    }

    public void getScreenSize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(_screenDimention);
    }

    public void drawGameCards()
    {
        // Petit test

        IGame g = new DameDePique();

        IPlayer p1 = new HumanPlayer("Benoit");
        g.addPlayer(p1);

        IPlayer p2 = new HumanPlayer("Florian");
        g.addPlayer(p2);

        IPlayer p3 = new HumanPlayer("Arnaud");
        g.addPlayer(p3);

        IPlayer p4 = new HumanPlayer("Random");
        g.addPlayer(p4);

        // COTE PLAYER ET HOTE pour obtenir l'objet Rules, on passera par
        // l'agent "RulesAgent" de l'HOTE, pas directement via la r��f��rence
        //
        // le principe serait que seul cet agent utilisera getRules ou un truc
        // similaire pour avoir/initialiser les r��gles, ensuite toutes les
        // demandes (qui joue au prochain tour, comment se passe la fin d'un
        // tour, est-ce que la partie est termin��e ?
        // passeront par l'agent RulesAgent
        IRules rules = g.getRules();
        Deck hand = rules.getInitialCardDistribution(g.getDeck(), g.getPlayers()).get(0);
        p1.setHand(hand);

        drawCards(p1.getHand());
    }

    protected void drawCards(List<Card> cards)
    {
        final View view = findViewById(R.id.cardsLayout);
        final View viewSendingLayout = findViewById(R.id.sendingLayout);

        if (view != null)
        {
            List<CardView> cardViews = new ArrayList<CardView>();

            int nbMaxCardsMain = 52 / 4;

            if (_screenDimention.y > 0)
            {

                // Set the cards dimension according to the size of the display
                // screen
                int w = (int) ((_screenDimention.x * 1.6 * nbMaxCardsMain) / 100);
                CardView.CARD_WIDTH = w;

                for (Card card : cards)
                {
                    CardView cv = new CardView(card, view.getContext());
                    cv.setOnTouchListener(new CardTouchListener());
                    cardViews.add(cv);
                }

                // Set margins

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                lp.addRule(RelativeLayout.BELOW, viewSendingLayout.getId());

                lp.setMargins(0, 20, 0, 0);

                view.setLayoutParams(lp);

                // The container of the cards

                CardsContainer.CARD_DISTANCE = ((_screenDimention.x - 2 * CardsContainer.STARTING_X - CardView.CARD_WIDTH) / (nbMaxCardsMain - 1));

                _cardsContainer = new CardsContainer((RelativeLayout) view, _screenDimention, cardViews);
            }
        }
    }

    public void drawSendingPanel()
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

    public void drawSeekBar()
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

            bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
            {

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    // When the user changes the progress value, we change the
                    // distance between cards

                    // Length of the player's hand
                    int lengthHand = ((_cardsContainer.getCards().size() - 1) * progress + CardView.CARD_WIDTH);

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

}
