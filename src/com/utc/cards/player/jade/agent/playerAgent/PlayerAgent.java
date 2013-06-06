package com.utc.cards.player.jade.agent.playerAgent;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.common.ontology.CardsOntology;
import com.utc.cards.model.PlayerModel;
import com.utc.cards.model.deck.Deck;

public class PlayerAgent extends Agent implements IPlayerAgent
{

    private static final long serialVersionUID = -7812647282008239070L;
    private static Logger log = LoggerFactory.getLogger(PlayerAgent.class);

    private PlayerModel model;
    private Context context;

    private Codec codec = new SLCodec();
    private Ontology onto = CardsOntology.Instance();

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

	ContentManager cm = getContentManager();
	cm.registerLanguage(codec);
	cm.registerOntology(onto);
	cm.setValidationMode(false);

	// / Add initial behaviours
	// écoute du choix du jeu et de la liste des joueurs
	addBehaviour(new PlayerListenerBehaviour(this));
	// inscription et écoute de la liste des joueurs
	addBehaviour(new PlayersManager(this));

	// // Initialize the message used to convey spoken sentences
	// spokenMsg = new ACLMessage(ACLMessage.INFORM);
	// spokenMsg.setConversationId(CHAT_ID);

	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IPlayerAgent.class, this);

    }

    @Override
    public void getHostGameName()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void joinHostGame()
    {

    }

    @Override
    public void playTurn()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void askAdvice()
    {
	// TODO Auto-generated method stub

    }

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

    public Codec getCodec()
    {
	return codec;
    }

    public Ontology getOntolygy()
    {
	return onto;
    }

}
