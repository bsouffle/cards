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
import com.utc.cards.model.card.TraditionnalCard.TraditionnalCardNames;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.AbstractGameRules;
import com.utc.cards.model.game.Fold;
import com.utc.cards.model.player.IPlayer;

public class DameDePiqueRules extends AbstractGameRules
{
    private boolean _alreadyHearts = false;

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
        boolean responce = false;

        Card cardPlay = Cards.get(0);
        Fold plicourant = model.getCurrentFold();
        Deck hand = player.getHand(player.MAIN_HAND);

        Card callCard;

        // Détermination de la première carte du pli à partir du gagnant du tour précèdent
        if (model.getOldFolds().empty())
            callCard = null;
        else
            callCard = plicourant.getCards(model.getOldFolds().lastElement().getWinner()).get(0);

        // Si on est au premier tour
        if (callCard == null)
        {
            // Si c'est le premier à jouer
            if (plicourant.getFoldCards().isEmpty())
            {
                // Si la carte jouer est le 2 de trèfle
                if (cardPlay.equals(model.getGame().getDeck().getCardByResourceId(R.raw.cards_2c)))
                    responce = true;
            }
            else
            {
                // Si il joue la couleur appeler
                if (((TraditionnalCard) cardPlay).getColor() == ((TraditionnalCard) callCard).getColor())
                    responce = true;
                else
                {
                    responce = true;
                    // On vérifie qu'il n'a pas la couleur appeler dans sa main
                    for (Card card : hand)
                    {
                        if (((TraditionnalCard) card).getColor() == ((TraditionnalCard) callCard).getColor())
                            responce = false;
                    }
                    // Si il joue du coeur ou la dame de pique
                    if (responce && (((TraditionnalCard) cardPlay).getColor() == Color.HEARTS || cardPlay.equals(model.getGame().getDeck().getCardByResourceId(R.raw.cards_qs))))
                    {
                        // On vérifie qu'il n'a pas de pique ou de carreau
                        for (Card card : hand)
                        {
                            if ((((TraditionnalCard) card).getColor() == Color.SPADES && !cardPlay.equals(model.getGame().getDeck().getCardByResourceId(R.raw.cards_qs)))
                                    || ((TraditionnalCard) card).getColor() == Color.DIAMONDS)
                                responce = false;
                        }
                    }
                }
            }
        }
        else
        {
            // Si c'est le premier à jouer
            if (plicourant.getFoldCards().isEmpty())
            {
                // Si c'est autre chose que du coeur
                if (((TraditionnalCard) cardPlay).getColor() != Color.HEARTS)
                {
                    responce = true;
                }
                else
                { // Sinon on vérifie qu'il y a que du coeur dans sa main ou que du coeur
                  // a déjà été joué
                    responce = true;
                    if (_alreadyHearts)
                    {
                        for (Card card : hand)
                        {
                            responce = true;
                            if (((TraditionnalCard) card).getColor() != Color.HEARTS)
                                responce = false;
                        }
                    }
                }
            }
            else
            {
                // Si il joue la couleur appeler
                if (((TraditionnalCard) cardPlay).getColor() == ((TraditionnalCard) callCard).getColor())
                    responce = true;
                else
                {
                    responce = true;
                    // On vérifie qu'il n'a pas la couleur appeler dans sa main
                    for (Card card : hand)
                    {
                        if (((TraditionnalCard) card).getColor() == ((TraditionnalCard) callCard).getColor())
                            responce = false;
                    }
                }
            }
        }

        // Si la carte validé est du coeur on autorise les joueurs à en joué plus tard comme première carte
        if (responce && ((TraditionnalCard) cardPlay).getColor() == Color.HEARTS)
            this._alreadyHearts = true;

        return responce;
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

        // initalisation des scores temporaire
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
    // Determine le gagnat du pli courrant et l'ajoute à la pile de plis joués
    public void determinateWinnerCurrentFold(HostModel model)
    {
        Map<Card, IPlayer> reversFold = new HashMap<Card, IPlayer>();
        Fold fold = model.getCurrentFold();
        Card callCard;
        IPlayer winner;
        Card masterCard;
        List<IPlayer> players = model.getGame().getPlayers();
        Deck deck = new Deck();

        // Detremination de la carte appeler
        if (model.getOldFolds().empty())
        {
            callCard = model.getGame().getDeck().getCardByResourceId(R.raw.cards_2c);
        }
        else
            callCard = fold.getCards(model.getOldFolds().lastElement().getWinner()).get(0);

        // Creation d'un tableau permettant de récuperer les joueurs à partir de leur carte
        // Ainsi que récuperation du pli en temps que Deck
        for (Entry<String, Deck> entry : fold.getFoldCards().entrySet())
        {
            for (IPlayer player : players)
            {
                if (player.toString().equals(entry.getKey()))
                    reversFold.put(entry.getValue().get(0), player);
            }
            deck.add(entry.getValue().get(0));
        }

        masterCard = callCard;
        deck.remove(callCard);
        for (Card card : deck)
        {
            // Si la couleur est la même que la carte appelé
            if (((TraditionnalCard) masterCard).getColor() == ((TraditionnalCard) card).getColor())
            {
                // On verifie que la valeur est plus forte
                if (getValueByName(((TraditionnalCard) card).getType()) > getValueByName(((TraditionnalCard) masterCard).getType()))
                {
                    masterCard = card;
                }
            }
        }

        // On recuppère le winnier a partir de sa carte
        fold.setWinner(reversFold.get(masterCard));
        model.getOldFolds().push(fold);
    }

    // Renvoie une "puissance" de carte selon le type de la carte
    private int getValueByName(TraditionnalCardNames name)
    {
        switch (name)
        {
        case _AS:
            return 14;
        case _10:
            return 10;
        case _2:
            return 2;
        case _3:
            return 3;
        case _4:
            return 4;
        case _5:
            return 5;
        case _6:
            return 6;
        case _7:
            return 7;
        case _8:
            return 8;
        case _9:
            return 9;
        case _J:
            return 11;
        case _K:
            return 13;
        case _Q:
            return 12;
        default:
            return -1;
        }
    }
}
