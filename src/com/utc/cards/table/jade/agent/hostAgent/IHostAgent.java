package com.utc.cards.table.jade.agent.hostAgent;

import com.utc.cards.model.HostModel;

public interface IHostAgent
{
    // public void openSuscription();

    // UI event
    public void sendGameSelected();

    // on game start when missing player
    // OR on player left
    public void createIAPlayer();

    public void removeIAPlayer();

    public HostModel getModel();

}
