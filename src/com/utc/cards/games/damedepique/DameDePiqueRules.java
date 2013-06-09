package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.utc.cards.R;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.card.Card;
import com.utc.cards.model.card.Color;
import com.utc.cards.model.card.TraditionnalCard;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.AbstractGameRules;
import com.utc.cards.model.game.Fold;
import com.utc.cards.model.player.IPlayer;

public class DameDePiqueRules extends AbstractGameRules
{

    @Override
    public IPlayer determineFirstPlayer(final Deck deck, final List<IPlayer> players)
    {
        Card deuxDeTrefle = deck.getCardByResourceId(R.raw.cards_2c);
        // celui qui a le deux de trÃªfle commence
        IPlayer firstPlayer = null;
        for (IPlayer player : players)
        {
            if (player.getHand().contains(deuxDeTrefle))
            {
                firstPlayer = player;
                break;
            }
        }
        return firstPlayer;
    }

    @Override
    public List<Deck> getInitialCardDistribution(final Deck deck, final List<IPlayer> players)
    {
        List<Deck> initialHands = new ArrayList<Deck>(players.size());
        int nbPlayers = players.size();
        Deck tas = new Deck(deck);
        int playerDeckSize = tas.size() / nbPlayers;

        for (int i = 0; i < nbPlayers; i++)
        {
            Deck initialHand = new Deck(playerDeckSize);
            // will add 13 or 9 random cards from allCards
            // remove choosen one at each time
            for (int j = 0; j < playerDeckSize; j++)
            {
                Card randomCard = getRandomCard(tas);
                initialHand.add(randomCard);
                tas.remove(randomCard);
            }
            initialHands.add(initialHand);
        }
        return initialHands;
    }

    private Card getRandomCard(Deck deck)
    {
        if (deck != null && !deck.isEmpty())
        {
            return deck.get((int) (Math.random() * (double) deck.size()));
        }
        return null;
    }

    @Override
    public boolean validerCoup(List<Card> Cards, IPlayer player, HostModel model)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Card conseilCoup(Deck handPlayer, IPlayer player, HostModel model)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getInitialScore()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int calculScore(Deck deck)
    {
        // TODO Auto-generated method stub

        return 0;
    }

    @Override
    public void calculScore(final Deck deck, Stack<Fold> plisFini, List<IPlayer> players)
    {
        Map<IPlayer, Integer> tmp = new HashMap<IPlayer, Integer>();
        Integer i;

        Deck pack = new Deck();
        Card dameDePique = deck.getCardByResourceId(R.raw.cards_qs);
        for (IPlayer player : players)
        {
            tmp.put(player, 0);
        }

        for (Fold fold : plisFini)
        {
            // recuperation des cartes du pli
            Map<String, Deck> pli = fold.getFoldCards();

            for (Deck foldEntry : pli.values())
            {
                pack.addAll(foldEntry);
            }

            // Verification de la dame de pique
            if (pack.contains(dameDePique))
            {
                i = tmp.get(fold.getWinner());
                i = i + 12;
                tmp.put(fold.getWinner(), i);
            }
            // Calcule du score du "gagnant" pour les coeurs
            for (Card card : pack)
            {
                if (((TraditionnalCard) card).getColor() == Color.HEARTS)
                {
                    i = tmp.get(fold.getWinner());
                    i++;
                    tmp.put(fold.getWinner(), i);
                }
            }

            pack.clear();
        }

        // Verification du cas du "controle"
        if (tmp.containsValue(25))
        {
            Set<Entry<IPlayer, Integer>> setEntry = tmp.entrySet();
            for (Entry<IPlayer, Integer> entry : setEntry)
            {
                i = tmp.get(entry.getKey());
                if (i == 0)
                    i = 25;
                else
                    i = 0;
                tmp.put(entry.getKey(), i);
            }
        }

        // Mise à jour du score dans le model
        for (IPlayer player : players)
        {
            i = tmp.get(player);
            i = i + player.getScore();
            player.setScore(i);
        }
    }

    @Override
    public void determinateWinnerCurrentFold()
    {
        // TODO Auto-generated method stub

    }

}
