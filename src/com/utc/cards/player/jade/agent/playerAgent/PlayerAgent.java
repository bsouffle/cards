package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.Agent;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;

import com.utc.cards.Constants;
import com.utc.cards.games.GameContainer;
import com.utc.cards.model.PlayerModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.GameStatus;

public class PlayerAgent extends Agent implements IPlayerAgent
{

    private static final long serialVersionUID = -7812647282008239070L;
    private static Logger log = LoggerFactory.getLogger(PlayerAgent.class);

    private PlayerModel model;
    private Context context;

    private String selectedGame = "";

    @Override
    protected void setup()
    {
	super.setup();
	Object[] args = getArguments();
	if (args != null && args.length > 0)
	{
	    if (args[0] instanceof Context)
	    {
		setContext((Context) args[0]);
	    } else
	    {
		log.error("Missing Context arg during agent setup");
	    }
	    if (args[1] instanceof PlayerModel)
	    {
		model = (PlayerModel) args[1];
	    } else
	    {
		log.error("Missing PlayerModel arg during agent setup");
	    }
	}

	// Add initial behaviours
	// écoute du choix du jeu
	// écoute de la liste des joueurs
	addBehaviour(new PlayerListenerBehaviour(this));

	// tentative d'inscription automatique au jeu
	joinHostGame();

	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IPlayerAgent.class, this);
    }

    @Override
    public void joinHostGame()
    {
	// envoi une demande d'inscription au lancement de l'agent, et a la
	// demande si la partie est pleine et qqn est parti
	addBehaviour(new PlayerSubscriptionBehaviour(this));
    }

    @Override
    public void onPlayerTurn()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void askAdvice()
    {
	// TODO Auto-generated method stub

    }

    // shortcut for MAIN deck
    @Override
    public void sendCards(Deck cards)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void sendCards(Map<String, Deck> cards)
    {
	// TODO Auto-generated method stub

    }

    public Context getContext()
    {
	return context;
    }

    public void setContext(Context context)
    {
	this.context = context;
    }

    public void onGameSubscriptionAgree()
    {
	// TODO Auto-generated method stub
    }

    public void onGameSubscriptionRefuse()
    {
	// TODO Auto-generated method stub

    }

    public void notifyInfo(String content)
    {
	// TODO Auto-generated method stub

    }

    public void notifyPlayersChanged(String[] players)
    {
	// TODO Auto-generated method stub

    }

    public void onGameStart()
    {
	// TODO Auto-generated method stub
	// change gameStatus
	model.setGame(GameContainer.getGameByName(selectedGame));
	model.getGame().setStatus(GameStatus.IN_GAME);

	// on informe la vue : l'activity
	Intent intent = new Intent();
	intent.setAction(Constants.SHOW_GAME);
	log.debug("Sending broadcast " + intent.getAction());
	context.sendBroadcast(intent);
	// load game activity implementation
	// wait for cards and turn
    }

    public void onGameSelection(String gameName)
    {
	selectedGame = gameName;

    }

}
