package com.utc.cards.games.damedepique;

import android.os.Bundle;

import com.utc.cards.model.game.AbstractGame;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.PlayerGameActivity;

public class DameDePiquePlayerGameActivity extends
	PlayerGameActivity<DameDePique>
{

    @Override
    public void drawGameCards()
    {
	// Petit test

	AbstractGame g = new DameDePique();
	g.loadCards();

	IPlayer p = new HumanPlayer("Benoit");
	g.addPlayer(p);

	p.setHand(g.getInitialCardDistribution().get(0));

	drawCards(p.getHand());
    }
}
