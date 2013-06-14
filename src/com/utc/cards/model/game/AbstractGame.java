package com.utc.cards.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.player.IPlayer;

public abstract class AbstractGame implements IGame
{

    private static Logger log = LoggerFactory.getLogger(AbstractGame.class);

    protected String name;
    protected Deck deck;
    protected IRules rules = null;
    protected GameStatus status = GameStatus.SUBSCRIPTION;
    protected int resource = -1;
    protected List<IPlayer> receivers;
    protected GameStep nextTurn;
    protected int indexPlayer;
    protected Map<IPlayer, Deck> exchange;

    public Map<IPlayer, Deck> getExchange()
    {
        return exchange;
    }

    protected Integer maxPlayerCount = null;
    protected Integer minPlayerCount = null;
    protected List<Integer> legalPlayerList = null;
    protected int[] legalPlayerCount = null;

    /**
     * la liste des joueurs dans leur ordre de jeu (après lancement de la partie, sinon ordre non garanti)
     */
    protected List<IPlayer> _players = new ArrayList<IPlayer>();

    public AbstractGame(String name, int maxPlayerCount, int minPlayerCount)
    {
        this.name = name;
        this.maxPlayerCount = maxPlayerCount;
        this.minPlayerCount = minPlayerCount;
        log.debug("AbstractGame() (" + minPlayerCount + " to " + maxPlayerCount + " players)");
    }

    public AbstractGame(String name, List<Integer> legalPlayerCount)
    {
        this(name, legalPlayerCount.get(0), legalPlayerCount.get(legalPlayerCount.size() - 1));
        this.legalPlayerList = legalPlayerCount;
        logLegalPlayerCountConstructor();
    }

    private void logLegalPlayerCountConstructor()
    {
        boolean first = true;
        StringBuilder legalPlayerString = new StringBuilder();

        for (int index = 0; index < this.legalPlayerList.size(); index++)
        {
            if (first)
            {
            }
            else if (index == (this.legalPlayerList.size() - 1))
            {
                legalPlayerString.append(" or ");
            }
            else
            {
                legalPlayerString.append(", ");
            }

            legalPlayerString.append(this.legalPlayerList.get(index));
            first = false;
        }

        log.debug("AbstractGame() (require " + legalPlayerString + " players)");
    }

    @Override
    public String getName()
    {
        log.debug("getName()");

        return name;
    }

    @Override
    public int getMaxPlayerCount()
    {
        log.debug("getMaxPlayerCount()");

        return maxPlayerCount;
    }

    @Override
    public int getMinPlayerCount()
    {
        log.debug("getMinPlayerCount()");

        return minPlayerCount;
    }

    public final List<Integer> getLegalPlayerList()
    {
        log.debug("getLegalPlayerList()");
        return legalPlayerList;
    }

    @Override
    public final List<IPlayer> getPlayers()
    {
        log.debug("getPlayers()");
        return _players;
    }

    @Override
    public List<String> getPlayerNames()
    {
        ArrayList<String> res = new ArrayList<String>();

        for (IPlayer p : getPlayers())
        {
            res.add(p.getName());
        }

        return res;
    }

    @Override
    public final void addPlayer(IPlayer player)
    {
        log.debug("addPlayer()");
        _players.add(player);
    }

    @Override
    public final void removePlayer(IPlayer player)
    {
        log.debug("removePlayer()");
        _players.remove(player);
    }

    // @Override
    // public final Deck getHand(Player player) {
    // log.debug("getHand()");
    // return hands.get(player);
    // }
    //
    // @Override
    // public final void setHand(Player player, Deck hand) {
    // log.debug("setHand()");
    // hands.put(player, hand);
    // }
    //
    // @Override
    // public final void replacePlayer(Player playerToRemove, Player
    // playerToAdd) {
    // log.debug("replacePlayer()");
    // Deck hand = hands.get(playerToRemove);
    // hands.remove(playerToRemove);
    // hands.put(playerToAdd, hand);
    // }

    protected abstract Deck createDeck();

    @Override
    public final Deck getDeck()
    {
        log.debug("getAllGameCards()");
        if (deck == null)
        {
            deck = createDeck();
        }
        return deck;
    }

    // seulement pour l'agent "RulesAgent"
    protected abstract IRules createRules();

    @Override
    public final IRules getRules()
    {
        log.debug("getAllGameCards()");
        if (rules == null)
        {
            rules = createRules();
        }
        return rules;
    }

    @Override
    public GameStatus getStatus()
    {
        return status;
    }

    @Override
    public void setStatus(GameStatus status)
    {
        this.status = status;
    }

    @Override
    public int getLogoResource()
    {
        return resource;
    }

    @Override
    public void setLogoResource(int res)
    {
        resource = res;
    }

    public GameStep getNextTurn()
    {
        // TODO Auto-generated method stub
        return nextTurn;
    }

    public List<IPlayer> getTarget()
    {
        // TODO Auto-generated method stub
        return receivers;
    }

    @Override
    public IPlayer getPlayerByName(String name)
    {
        for (IPlayer p : _players)
        {
            if (p.getName().toUpperCase().equals(name.toUpperCase()))
                return p;
        }

        return null;
    }

}
