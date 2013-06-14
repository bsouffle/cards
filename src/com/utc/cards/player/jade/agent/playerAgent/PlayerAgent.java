package com.utc.cards.player.jade.agent.playerAgent;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.Intent;

import com.utc.cards.Constants;
import com.utc.cards.common.jade.Mapper;
import com.utc.cards.common.view.CardView;
import com.utc.cards.games.GameContainer;
import com.utc.cards.model.PlayerModel;
import com.utc.cards.model.game.GameStatus;
import com.utc.cards.model.game.Info;
import com.utc.cards.model.game.InfoType;
import com.utc.cards.model.game.PlayerTry;

public class PlayerAgent extends Agent implements IPlayerAgent
{

    private static final long serialVersionUID = -7812647282008239070L;
    private static Logger log = LoggerFactory.getLogger(PlayerAgent.class);

    private PlayerModel model;
    private Context context;

    private String selectedGame = "";
    private String gmail;

    @Override
    protected void setup()
    {
        super.setup();
        Object[] args = getArguments();
        if (args != null && args.length > 0)
        {
            if (args[0] instanceof Context)
            {
                context = (Context) args[0];
            }
            else
            {
                log.error("Missing Context arg during agent setup");
            }
            if (args[1] instanceof PlayerModel)
            {
                model = (PlayerModel) args[1];
            }
            else
            {
                log.error("Missing PlayerModel arg during agent setup");
            }
            if (args[2] instanceof String)
            {
                gmail = (String) args[2];
            }
            else
            {
                log.error("Missing gmail arg during agent setup");
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
    public ACLMessage sendCards(List<CardView> cards)
    {
        StringWriter sw = new StringWriter();
        ACLMessage m = new ACLMessage(ACLMessage.INFORM);

        try
        {
            PlayerTry pt = new PlayerTry();
            pt.setCards(cards);
            Mapper.getObjectMapper().writeValue(sw, pt);

            System.out.println("Message serialized in Info: " + sw.toString());

            Info info = new Info();
            info.setJson(sw.toString());
            info.setType(InfoType.VALID_PLAY);

            Mapper.getObjectMapper().writeValue(sw, info);

            System.out.println("Message to send: " + sw.toString());

            m.setContent(sw.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return m;
    }

    public Context getContext()
    {
        return context;
    }

    public void onGameSubscriptionAgree()
    {
        log.debug("onGameSubscriptionAgree()");
        // FIXME: disable connect button
        notifyInfo("Connexion réussie");
    }

    public void onGameSubscriptionRefuse()
    {
        log.debug("onGameSubscriptionRefuse()");
        notifyInfo("Connexion refusée");
    }

    /**
     * pour afficher une popup
     * 
     * @param info
     */
    public void notifyInfo(String info)
    {
        log.debug("notifyInfo()");
        Intent intent = new Intent(Constants.POP_INFO);
        intent.putExtra(InfoType.INFO.name(), info);
        log.debug("Sending INTENT " + intent.getAction());
        context.sendBroadcast(intent);
    }

    /**
     * met a jour la liste affichée des participants
     * 
     * @param players
     */
    public void notifyPlayersChanged(String[] players)
    {
        log.debug("notifyPlayersChanged()");
        Intent intent = new Intent(Constants.PLAYER_LIST);
        intent.putExtra(Constants.PLAYER_LIST, players);
        log.debug("Sending INTENT " + intent.getAction());
        context.sendBroadcast(intent);
    }

    public void onGameStart()
    {
        // change gameStatus
        model.setGame(GameContainer.getGameByName(selectedGame));
        model.getGame().setStatus(GameStatus.IN_GAME);

        // on informe la vue : l'activity
        Intent intent = new Intent();
        intent.setAction(Constants.SHOW_GAME);
        log.debug("Sending INTENT " + intent.getAction());
        context.sendBroadcast(intent);
    }

    public void onGameSelection(String gameName)
    {
        log.debug("onGameSelection({})", gameName);
        selectedGame = gameName;
        // update UI, affiche le nom du jeu
        Intent intent = new Intent(Constants.GAME_NAME);
        log.debug("Sending INTENT " + intent.getAction());
        intent.putExtra(Constants.GAME_NAME, selectedGame);
        context.sendBroadcast(intent);
    }

    public String getGmail()
    {
        return gmail;
    }

}
