package com.utc.cards.model.game;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.AbstractPlayerGameActivity;

public interface IGame
{

    public Class getTableGameActivity();

    public AbstractPlayerGameActivity createPlayerGameActivity();

    public String getName();

    public int getMaxPlayerCount();

    public int getMinPlayerCount();

    public Deck getDeck();

    public IRules getRules();

    public List<IPlayer> getPlayers();

    public List<String> getPlayerNames();

    public void addPlayer(IPlayer player);

    public void removePlayer(IPlayer player);

    // public void setHand(Player player, Deck hand);
    //
    // public Deck getHand(Player player);
    //
    // public void replacePlayer(Player playerToRemove, Player playerToAdd);
    //
    // public void launchGame();

    public int[] getLegalPlayerCounts();

    public GameStatus getStatus();

    public void setStatus(GameStatus status);

    public int getLogoResource();

    public void setLogoResource(int res);

    public void registerListener(PropertyChangeListener pcl);
}
