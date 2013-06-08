package com.utc.cards.model;

import jade.core.AID;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.Fold;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.IPlayer;

public class HostModel
{
    private static HostModel instance;

    /**
     * le jeu actuellement chargé
     */
    private IGame _game;
    /**
     * l'état actuel des mains des joueurs
     */
    private Map<IPlayer, Map<String, Deck>> _playerHands = new HashMap<IPlayer, Map<String, Deck>>();
    /**
     * l'état actuel des cartes de l'hôte
     */
    private Map<String, Deck> _hostDecks = new HashMap<String, Deck>();
    /**
     * les plis précédents
     */
    private Stack<Fold> _oldFolds = new Stack<Fold>();
    /**
     * le pli courant
     */
    private Fold _currentFold;

    private Map<String, AID> _playersMap = new HashMap<String, AID>();

    public HostModel()
    {
	super();
	instance = this;
    }

    public static HostModel Instance()
    {
	return instance;
    }

    public IGame getGame()
    {
	return _game;
    }

    public void setGame(IGame game)
    {
	this._game = game;
    }

    public Map<IPlayer, Map<String, Deck>> getPlayerHands()
    {
	return _playerHands;
    }

    public void setPlayerHands(Map<IPlayer, Map<String, Deck>> playerHands)
    {
	this._playerHands = playerHands;
    }

    public Map<String, Deck> getHostDecks()
    {
	return _hostDecks;
    }

    public void setHostDecks(Map<String, Deck> hostDecks)
    {
	this._hostDecks = hostDecks;
    }

    public Stack<Fold> getOldFolds()
    {
	return _oldFolds;
    }

    public void setOldFolds(Stack<Fold> oldFolds)
    {
	this._oldFolds = oldFolds;
    }

    public Fold getCurrentFold()
    {
	return _currentFold;
    }

    public void setCurrentFold(Fold currentFold)
    {
	this._currentFold = currentFold;
    }

    public Map<String, AID> getPlayersMap()
    {
	return _playersMap;
    }

}
