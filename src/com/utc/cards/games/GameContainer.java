package com.utc.cards.games;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.utc.cards.games.damedepique.DameDePique;
import com.utc.cards.model.game.IGame;

/**
 * Contient tous les jeux, seul endroit où sont utilisés les constructeurs des jeux
 * @author Arnaud
 *
 */
public class GameContainer
{
    private static Set<IGame> games;
    private static SortedSet<String> gameNames;

    static {
	games = new HashSet<IGame>();
	gameNames = new TreeSet<String>();
	addGame(new DameDePique());
    }
    
    private GameContainer()
    {
    }

    private static void addGame(IGame game)
    {
	games.add(game);
	gameNames.add(game.getName());
    }

    // for PLAYER and HOST 
    // PLAYER to load the playerGameActivity implementation
    // HOST   to load the hostGameActivity implementation
    //		  and the rules implementation by RulesAgent
    public static IGame getGameByName(String name)
    {
	for (IGame game : games) {
	    if (game.getName().equals(name)) {
		return game;
	    }
	}
	return null;
    }
    
    // for HOST when app is launched
    public static SortedSet<String> getCompleteGameNameList() {
	return gameNames;
    }
}
