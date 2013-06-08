package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.utc.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.CardsContainer;
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
    public void onCreateHook(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub

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

    @Override
    public void onCreateHook()
    {
        drawGameCards();

        // Set the panel used to send cards to the host
        drawSendingPanel();

        // Set the bar which allows to modify the distance between two cards
        drawSeekBar();
    }

    public void drawGameCards()
    {
        // Petit test

        IGame g = new DameDePique();

        IPlayer p = new HumanPlayer("Benoit");
        g.addPlayer(p);

        // COTE PLAYER ET HOTE pour obtenir l'objet Rules, on passera par
        // l'agent "RulesAgent" de l'HOTE, pas directement via la référence
        //
        // le principe serait que seul cet agent utilisera getRules ou un truc
        // similaire pour avoir/initialiser les règles, ensuite toutes les
        // demandes (qui joue au prochain tour, comment se passe la fin d'un
        // tour, est-ce que la partie est terminée ?
        // passeront par l'agent RulesAgent
        IRules rules = g.getRules();
        Deck hand = rules.getInitialCardDistribution(g.getDeck(), g.getPlayers()).get(0);
        p.setHand(hand);

        drawCards(p.getHand());
    }

    protected void drawCards(List<Card> cards)
    {
        final View view = findViewById(R.id.cardsLayout);

        if (view != null)
        {

            List<CardView> cardViews = new ArrayList<CardView>();

            for (Card card : cards)
            {
                cardViews.add(new CardView(card, card.getResourceId(), view.getContext()));
            }

            if (_screenDimention.y > 0)
            {
                // Set the cards dimension according to the size of the display
                // screen
                int w = (int) (_screenDimention.x * 21 / 100);
                CardView.CARD_WIDTH = w;

                // Set margins
                int m = (int) (_screenDimention.y * 3 / 100);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                lp.setMargins(0, m, 0, m);
                view.setLayoutParams(lp);

                // The container of the cards
                _cardsContainer = new CardsContainer((RelativeLayout) view, _screenDimention, cardViews);
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
                int h = (int) (_screenDimention.y * 10 / 100);
                view.setMinimumHeight(h);
            }

            view.setBackgroundColor(android.graphics.Color.GRAY);
            view.setOnDragListener(new SendDragListener(_cardsContainer));
        }
    }

    private void drawSeekBar()
    {
        final View view = findViewById(R.id.seekBar);

        if (view != null)
        {
            SeekBar bar = (SeekBar) view;

            bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
            {

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
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

}
