package com.utc.cards;

public class Constants
{
    public static final String JADE_CARDS_PREFS_FILE = "jadeCardsPrefsFile";
    public static final String DEFAULT_JADE_PORT = "1099";

    public static final String HOST_PORT = "mainContainerPort";
    public static final String HOST_IP = "mainContainerIp";

    public static final String LOCAL_IP = "localIp";
    public static final String GMAIL = "gmail";

    // nom des agents
    public static final String CARDS_HOST_AGENT_NAME = "host-agent";
    public static final String CARDS_HOST_GAME_AGENT_NAME = "host-agent";
    public static final String CARDS_HOST_RULES_AGENT_NAME = "rules-agent";
    public static final String CARDS_PLAYER_AGENT_NAME = "player-agent";
    public static final String CARDS_PLAYER_HELPER_AGENT_NAME = "player-helper-agent";

    // Intents utilisés entre les agents et les activity (INTENT)
    public static final String SHOW_GAME = "SHOW_GAME";
    public static final String GAME_NAME = "GAME_NAME";
    public static final String POP_INFO = "POP_INFO";
    public static final String PLAYER_LIST = "PLAYER_LIST";
    public static final String PLAYER_JOIN = "PLAYER_JOIN";
}
