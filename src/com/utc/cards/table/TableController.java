package com.utc.cards.table;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.decks.Deck;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.IAPlayer;
import com.utc.cards.model.player.IPlayer;

public class TableController
{

    private static Logger _log = LoggerFactory
	    .getLogger(TableController.class);

    private IGame _game;

    public void loadGame(IGame game)
    {
	_log.debug("loadGame()");

	setGame(game);
	
	// wait for players / IA to subscribe
	startSubscription();
    }

    // on user on table event
    public void launchGame()
    {
	_log.debug("launchGame()");
	completePlayersWithIA();
	distributeInitialCards();
	notifyTurn(_game.getFirstPlayer());
    }

    private void completePlayersWithIA()
    {
	_log.debug("completePlayersWithIA()");
	int actualPlayerCount = _game.getPlayers().size();

	if (_game.getMinPlayerCount() != 0)
	{
	    int neededIACount = 0;
	    // might need to add IA
	    if (_game.getLegalPlayerCounts().length != 0)
	    {
		int smallestLegalPlayerCount = 0;
		for (int i = 0; i < _game.getLegalPlayerCounts().length; i++)
		{
		    if (_game.getLegalPlayerCounts()[i] > actualPlayerCount)
		    {
			smallestLegalPlayerCount = _game.getLegalPlayerCounts()[i];
			break;
		    }
		}

		neededIACount = smallestLegalPlayerCount - actualPlayerCount;
	    } else
	    {
		neededIACount = _game.getMinPlayerCount() - actualPlayerCount;

	    }
	    for (int i = 0; i < neededIACount; i++)
	    {
		addIAPlayer();
	    }
	}
    }

    // use rules via behaviours
    private void distributeInitialCards()
    {
	_log.debug("distributeInitialCards()");
	List<Deck> hands = _game.getRules().getInitialCardDistribution(_game.getDeck(), _game.getPlayers());

	// TODO check order, use the same index ?

	List<IPlayer> players = _game.getPlayers();
	for (int index = 0; index < players.size(); index++)
	{
	    IPlayer player = players.get(index);
	    notifyDeckChanged(player, hands.get(index));
	}
    }

    private void startSubscription()
    {
	_log.debug("startSubscription()");

	// limit subsription between min and max player count or only to
	// authorized values
	// TODO
    }

    public void addIAPlayer()
    {
	_log.debug("addIAPlayer()");

	IPlayer ia = new IAPlayer("IA");
	_game.addPlayer(ia);
    }

    public void removeIAPlayer()
    {
	_log.debug("removeIAPlayer()");

	for (int index = 0; index < _game.getPlayers().size(); index++)
	{
	    if (_game.getPlayers().get(index) instanceof IAPlayer)
	    {
		_game.getPlayers().remove(index);
		return;
	    }
	}
    }

    private void notifyTurn(IPlayer player)
    {
	_log.debug("notifyTurn()");

	// TODO Auto-generated method stub
    }

    private void notifyDeckChanged(IPlayer player, Deck deck)
    {
	_log.debug("notifyDeckChanged()");

	// TODO Auto-generated method stub
    }

    public IGame getGame()
    {
	_log.debug("getGame()");

	return _game;
    }

    private void setGame(IGame game)
    {
	_log.debug("setGame()");

	this._game = game;
    }

    public void runApp()
    {
	_log.debug("runApp()");
    }

}
