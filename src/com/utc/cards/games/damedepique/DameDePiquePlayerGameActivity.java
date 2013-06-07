package com.utc.cards.games.damedepique;

import android.os.Bundle;

import com.utc.cards.R;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.AbstractPlayerGameActivity;

public class DameDePiquePlayerGameActivity extends AbstractPlayerGameActivity
{

    @Override
    public void drawGameCards()
    {
	// Petit test

	IGame g = new DameDePique();

	IPlayer p1 = new HumanPlayer("Benoit");
	g.addPlayer(p1);

	IPlayer p2 = new HumanPlayer("Florian");
	g.addPlayer(p2);

	IPlayer p3 = new HumanPlayer("Bobby");
	g.addPlayer(p3);

	IPlayer p4 = new HumanPlayer("Pokemon");
	g.addPlayer(p4);

	// COTE PLAYER ET HOTE pour obtenir l'objet Rules, on passera par
	// l'agent "RulesAgent" de l'HOTE, pas directement via la référence
	//
	// le principe serait que seul cet agent utilisera getRules ou un truc
	// similaire pour avoir/initialiser les règles, ensuite toutes les
	// demandes (qui joue au prochain tour, comment se passe la fin d'un
	// tour, est-ce que la partie est terminée ?
	// passeront par l'agent RulesAgent

	IRules rules = g.getRules();
	Deck hand = rules.getInitialCardDistribution(g.getDeck(),
		g.getPlayers()).get(0);
	p1.setHand(hand);

	System.out.println("p1.getHand()" + p1.getHand());

	drawCards(p1.getHand(), 13);
    }

    @Override
    public void onCreateHook(Bundle savedInstanceState)
    {
	setContentView(R.layout.activity_main);

	drawGameCards();

	// Set the panel used to send cards to the host
	drawSendingPanel();

	// Set the bar which allows to modify the distance between two cards
	drawSeekBar();
    }
}
