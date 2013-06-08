package com.utc.cards.model.game;

public enum InfoType
{
    INFO, PLAYERS_LIST, GAME_SELECTION, GAME_START, GAME_END, PLAYER_TURN, INIT_CARDS, VALID_PLAY, PLAYER_START, ASK_ADVICE
}

// INFO : pur message popup
// PLAYER_LIST : demander la liste de joueur
// GAME_SELECTION : avertissement jeu sélectionné de la part de HostAgent
// GAME_START : avertissement début jeu de la part de HostAgent
// GAME_END : avertissement fin jeu de la part de HostAgent
// PLAYER_TURN : avertissement du tour du joueur par l'agent Game
// INIT_CARDS : demande de distribution des cartes de la part de l'agent Game à
// l'agent Rules
// VALID_PLAY : demande de validation d'un coup de la part d'un agent Player à
// l'agent Rules
// PLAYER_START : demande de l'identité du premier joueur de la part de l'agent
// Game à l'agent Rules
// ASK_ADVICE : demande d'un conseil de la part d'un joueur à l'agent Rules