package com.utc.cards.model.decks;

import static com.utc.cards.model.Color.*;
import static com.utc.cards.model.TraditionnalCard.TraditionnalCardNames.*;

import com.utc.cards.R;
import com.utc.cards.model.TraditionnalCard;

public final class Traditionnal52Deck extends Deck
{

    private static final long serialVersionUID = 7362813219195435616L;

    // FIXME: only Clubs resource is available, Diamonds, Hearts and Spades are
    // missing
    public Traditionnal52Deck()
    {
	addClubCards();
	addDiamondCards();
	addHeartCards();
	addSpadeCards();
    }

    private void addSpadeCards()
    {
	add(new TraditionnalCard(_AS, SPADES, R.raw.cards_as));
	add(new TraditionnalCard(_2, SPADES, R.raw.cards_2s));
	add(new TraditionnalCard(_3, SPADES, R.raw.cards_3s));
	add(new TraditionnalCard(_4, SPADES, R.raw.cards_4s));
	add(new TraditionnalCard(_5, SPADES, R.raw.cards_5s));
	add(new TraditionnalCard(_6, SPADES, R.raw.cards_6s));
	add(new TraditionnalCard(_7, SPADES, R.raw.cards_7s));
	add(new TraditionnalCard(_8, SPADES, R.raw.cards_8s));
	add(new TraditionnalCard(_9, SPADES, R.raw.cards_9s));
	add(new TraditionnalCard(_10, SPADES, R.raw.cards_10s));
	add(new TraditionnalCard(_J, SPADES, R.raw.cards_js));
	add(new TraditionnalCard(_Q, SPADES, R.raw.cards_qs));
	add(new TraditionnalCard(_K, SPADES, R.raw.cards_ks));
    }

    private void addHeartCards()
    {
	add(new TraditionnalCard(_AS, HEARTS, R.raw.cards_ah));
	add(new TraditionnalCard(_2, HEARTS, R.raw.cards_2h));
	add(new TraditionnalCard(_3, HEARTS, R.raw.cards_3h));
	add(new TraditionnalCard(_4, HEARTS, R.raw.cards_4h));
	add(new TraditionnalCard(_5, HEARTS, R.raw.cards_5h));
	add(new TraditionnalCard(_6, HEARTS, R.raw.cards_6h));
	add(new TraditionnalCard(_7, HEARTS, R.raw.cards_7h));
	add(new TraditionnalCard(_8, HEARTS, R.raw.cards_8h));
	add(new TraditionnalCard(_9, HEARTS, R.raw.cards_9h));
	add(new TraditionnalCard(_10, HEARTS, R.raw.cards_10h));
	add(new TraditionnalCard(_J, HEARTS, R.raw.cards_jh));
	add(new TraditionnalCard(_Q, HEARTS, R.raw.cards_qh));
	add(new TraditionnalCard(_K, HEARTS, R.raw.cards_kh));
    }

    private void addDiamondCards()
    {
	add(new TraditionnalCard(_AS, DIAMONDS, R.raw.cards_ad));
	add(new TraditionnalCard(_2, DIAMONDS, R.raw.cards_2d));
	add(new TraditionnalCard(_3, DIAMONDS, R.raw.cards_3d));
	add(new TraditionnalCard(_4, DIAMONDS, R.raw.cards_4d));
	add(new TraditionnalCard(_5, DIAMONDS, R.raw.cards_5d));
	add(new TraditionnalCard(_6, DIAMONDS, R.raw.cards_6d));
	add(new TraditionnalCard(_7, DIAMONDS, R.raw.cards_7d));
	add(new TraditionnalCard(_8, DIAMONDS, R.raw.cards_8d));
	add(new TraditionnalCard(_9, DIAMONDS, R.raw.cards_9d));
	add(new TraditionnalCard(_10, DIAMONDS, R.raw.cards_10d));
	add(new TraditionnalCard(_J, DIAMONDS, R.raw.cards_jd));
	add(new TraditionnalCard(_Q, DIAMONDS, R.raw.cards_qd));
	add(new TraditionnalCard(_K, DIAMONDS, R.raw.cards_kd));
    }

    private void addClubCards()
    {
	add(new TraditionnalCard(_AS, CLUBS, R.raw.cards_ac));
	add(new TraditionnalCard(_2, CLUBS, R.raw.cards_2c));
	add(new TraditionnalCard(_3, CLUBS, R.raw.cards_3c));
	add(new TraditionnalCard(_4, CLUBS, R.raw.cards_4c));
	add(new TraditionnalCard(_5, CLUBS, R.raw.cards_5c));
	add(new TraditionnalCard(_6, CLUBS, R.raw.cards_6c));
	add(new TraditionnalCard(_7, CLUBS, R.raw.cards_7c));
	add(new TraditionnalCard(_8, CLUBS, R.raw.cards_8c));
	add(new TraditionnalCard(_9, CLUBS, R.raw.cards_9c));
	add(new TraditionnalCard(_10, CLUBS, R.raw.cards_10c));
	add(new TraditionnalCard(_J, CLUBS, R.raw.cards_jc));
	add(new TraditionnalCard(_Q, CLUBS, R.raw.cards_qc));
	add(new TraditionnalCard(_K, CLUBS, R.raw.cards_kc));
    }
}
