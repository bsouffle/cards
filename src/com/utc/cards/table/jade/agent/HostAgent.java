package com.utc.cards.table.jade.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import jade.core.AID;
import jade.core.Agent;
//import jade.domain.DFService;
//import jade.domain.FIPAException;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HostAgent extends Agent implements IHostAgent
{

    private static final long serialVersionUID = -8649418473966240098L;
    private static Logger _log = LoggerFactory
	    .getLogger(HostAgent.class);

    @Override
    public void setup()
    {
	_log.debug("HostAgent.setup()");
    }

    // public AID getReceiver()
    // {
    // DFAgentDescription template = new DFAgentDescription();
    // ServiceDescription sd = new ServiceDescription();
    // sd.setType("Operations");
    // sd.setName("Multiplication");
    // template.addServices(sd);
    // try
    // {
    // DFAgentDescription[] result = DFService.search(this, template);
    // if (result.length > 0)
    // {
    // int index = (int) (Math.random() * result.length);
    // AID receiver = result[index].getName();
    // return receiver;
    // }
    // } catch (FIPAException fe)
    // {
    //
    // }
    // return null;
    //  }
}
