package com.utc.cards.games.damedepique;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.game.IRules;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.AbstractPlayerGameActivity;

public class DameDePiquePlayerGameActivity extends
	AbstractPlayerGameActivity
{

    @Override
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
	Deck hand = rules.getInitialCardDistribution(g.getDeck(),
		g.getPlayers()).get(0);
	p.setHand(hand);

	drawCards(p.getHand());
    }
}
