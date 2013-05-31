package com.utc.cards.model.game;

import java.util.ArrayList;
import java.util.List;

import com.utc.cards.model.decks.Deck;
import com.utc.cards.model.player.IPlayer;

public interface IGame
{
    public String getName();

    public int getMaxPlayerCount();

    public int getMinPlayerCount();

    public void init();

    public Deck getDeck();

    public void initializeGame();

    public boolean isInitialized();

    public List<IPlayer> getPlayers();

    public void addPlayer(IPlayer player);

    public void removePlayer(IPlayer player);

    public ArrayList<IRule> getRules();

    public IPlayer determineFirstPlayer();

    public IPlayer getFirstPlayer();

    public List<Deck> getInitialCardDistribution();

    // public void setHand(Player player, Deck hand);
    //
    // public Deck getHand(Player player);
    //
    // public void replacePlayer(Player playerToRemove, Player playerToAdd);

    public void launchGame();

    public int[] getLegalPlayerCounts();
}
