package com.utc.cards.common.ontology;

public interface CardsVocabulary
{
    public static final String ONTOLOGY_NAME = "Cards-ontology";

    // VOCABULARY
    // SUBCRIPTION
    public static final String JOINED = "joined";
    public static final String JOINED_WHO = "who";

    public static final String LEFT = "left";
    public static final String LEFT_WHO = "who";

    public static final String SPOKEN = "spoken";
    public static final String SPOKEN_WHAT = "what";

    // CARDS
    public static final String CARD = "card"; // concept
    public static final String CARD_NAME = "name";
    public static final String CARD_ID = "resourceId";
    public static final String CARD_ORIENTATION = "orientation";
    public static final String CARD_VISIBLE = "visible";

    public static final String DECK = "deck"; // concept
    public static final String DECK_NAME = "name";
    public static final String DECK_CARDS = "cards";

    public static final String SET_CARDS = "set_cards"; // predicate
    public static final String ADD_CARDS = "add_cards"; // predicate
    public static final String REMOVE_CARDS = "rem_cards"; // predicate
    public static final String PLAY_CARDS = "play_cards"; // predicate

    public static final String INFO = "info"; // concept
    public static final String INFO_TEXT = "text"; // concept

    public static final String GAME = "game"; // concept
    public static final String GAME_NAME = "name";

}