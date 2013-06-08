package com.utc.cards.model.game;

import java.util.List;

import com.utc.cards.common.jade.IGameBehaviour;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.player.view.IPlayerGameActivity;
import com.utc.cards.table.view.ITableGameActivity;

public interface IGame
{

    public ITableGameActivity createTableGameActivity();

    public IPlayerGameActivity createPlayerGameActivity();

    public IGameBehaviour createGameBehaviour();

    public String getName();

    public int getMaxPlayerCount();

    public int getMinPlayerCount();

    public Deck getDeck();

    public IRules getRules();

    public List<IPlayer> getPlayers();

    public void addPlayer(IPlayer player);

    public void removePlayer(IPlayer player);

    // public void setHand(Player player, Deck hand);
    //
    // public Deck getHand(Player player);
    //
    // public void replacePlayer(Player playerToRemove, Player playerToAdd);
    //
    // public void launchGame();

    public List<Integer> getLegalPlayerCounts();

    public GameStatus getStatus();

    public void setStatus(GameStatus status);

    public int getLogoResource();

    public void setLogoResource(int res);

}
