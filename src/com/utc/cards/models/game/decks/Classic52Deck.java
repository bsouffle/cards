package com.utc.cards.models.game.decks;

import com.example.cards.R;
import com.utc.cards.models.game.Card;

public final class Classic52Deck extends Deck
{
	private final static int[] CLUB_CARDS_RESOURCE = {R.raw.cards_ac, R.raw.cards_2c, R.raw.cards_3c, R.raw.cards_4c, R.raw.cards_5c, R.raw.cards_6c, R.raw.cards_7c, R.raw.cards_8c, 
		R.raw.cards_9c, R.raw.cards_10c, R.raw.cards_jc, R.raw.cards_qc, R.raw.cards_kc};

	public Classic52Deck()
	{
		for(int r : CLUB_CARDS_RESOURCE) // clubs
		{
			_cards.add(new Card("Club_" + (_nbOfCards+1), r, _nbOfCards++));
		}
		
		// Ajouter piques, coeurs, et carreaux
	}
}
