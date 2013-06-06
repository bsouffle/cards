package com.utc.cards.table.jade.agent.hostAgent;

import jade.content.ContentManager;
import jade.content.abs.AbsAggregate;
import jade.content.abs.AbsPredicate;
import jade.content.abs.AbsTerm;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.introspection.AMSSubscriber;
import jade.domain.introspection.DeadAgent;
import jade.domain.introspection.Event;
import jade.domain.introspection.IntrospectionOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.utc.cards.common.ontology.CardsOntology;
import com.utc.cards.common.ontology.Joined;
import com.utc.cards.model.HostModel;
//import jade.core.AID;
//import jade.domain.DFService;
//import jade.domain.FIPAException;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HostAgent extends Agent implements IHostAgent, SubscriptionManager
{

    private static final long serialVersionUID = -8649418473966240098L;
    private static Logger log = LoggerFactory.getLogger(HostAgent.class);

    private HostModel model;
    private Context context;

    private Codec codec = new SLCodec();
    private Ontology onto = CardsOntology.Instance();

    private Map<AID, Subscription> participants = new HashMap<AID, Subscription>();
    private AMSSubscriber amsSubscriber;

    @Override
    protected void setup()
    {
	super.setup();
	Object[] args = getArguments();
	if (args != null && args.length > 0)
	{
	    // try catch for host test only, doesn't have Context class
	    try
	    {
		if (args[0] instanceof Context)
		{
		    setContext((Context) args[0]);
		} else
		{
		    log.error("Missing Context arg during agent setup");
		}
		if (args[1] instanceof HostModel)
		{
		    model = (HostModel) args[1];
		} else
		{
		    log.error("Missing HostModel arg during agent setup");
		}
	    } catch (Exception e)
	    {
		// e.printStackTrace();
	    }
	}

	ContentManager cm = getContentManager();
	cm.registerLanguage(codec);
	cm.registerOntology(onto);
	// cm.setValidationMode(false);

	// écoute la plupart des demandes des playerAgent
	addBehaviour(new HostListenerBehaviour(this));

	// Behaviours d'inscription au jeu
	MessageTemplate sTemplate = MessageTemplate.and(MessageTemplate
		.MatchPerformative(ACLMessage.SUBSCRIBE), MessageTemplate.and(
		MessageTemplate.MatchLanguage(codec.getName()),
		MessageTemplate.MatchOntology(onto.getName())));
	log.debug("addBehaviour : SubscriptionResponder");
	addBehaviour(new SubscriptionResponder(this, sTemplate, this));
	// Register to the AMS to detect when chat participants suddenly die
	createAMSSubscriber();
	log.debug("addBehaviour : amsSubscriber");
	addBehaviour(amsSubscriber);
	// expose l'interface pour la rendre accessible par les activity
	registerO2AInterface(IHostAgent.class, this);
    }

    private void createAMSSubscriber()
    {
	amsSubscriber = new AMSSubscriber() {
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    protected void installHandlers(Map handlersTable)
	    {
		// Fill the event handler table. We are only interested in the
		// DEADAGENT event
		handlersTable.put(IntrospectionOntology.DEADAGENT,
			new EventHandler() {
			    public void handle(Event ev)
			    {
				DeadAgent da = (DeadAgent) ev;
				AID id = da.getAgent();
				// If the agent was attending the chat -->
				// notify all other participants that it has
				// just left.
				if (participants.containsKey(id))
				{
				    try
				    {
					deregister((Subscription) participants
						.get(id));
				    } catch (Exception e)
				    {
					// Should never happen
					e.printStackTrace();
				    }
				}
			    }
			});
	    }
	};
    }

    @Override
    public void sendGameSelected()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void createIAPlayer()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeIAPlayer()
    {
	// TODO Auto-generated method stub

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
    // }

    @Override
    public boolean deregister(Subscription subscription)
	    throws FailureException
    {
	log.debug("DEREGISTER()");

	AID oldId = subscription.getMessage().getSender();
	// Remove the subscription
	if (participants.remove(oldId) != null)
	{
	    // Notify other participants if any
	    if (!participants.isEmpty())
	    {
		try
		{
		    ACLMessage notif = subscription.getMessage().createReply();
		    notif.setPerformative(ACLMessage.INFORM);
		    notif.clearAllReceiver();
		    AbsPredicate p = new AbsPredicate(CardsOntology.LEFT);
		    // pour passer une liste d'objets (Arraylist)
		    AbsAggregate agg = new AbsAggregate(BasicOntology.SEQUENCE);
		    agg.add((AbsTerm) BasicOntology.getInstance().fromObject(
			    oldId));
		    p.set(CardsOntology.LEFT_WHO, agg);
		    getContentManager().fillContent(notif, p);

		    Iterator<Subscription> it = participants.values()
			    .iterator();
		    while (it.hasNext())
		    {
			Subscription s1 = (Subscription) it.next();
			s1.notify(notif);
		    }
		} catch (Exception e)
		{
		    e.printStackTrace();
		}
	    }
	}
	return false;
    }

    @Override
    public boolean register(Subscription subscription) throws RefuseException,
	    NotUnderstoodException
    {
	log.debug("REGISTER()");
	try
	{
	    AID newId = subscription.getMessage().getSender();
	    // Notify the new participant about the others (if any) and VV
	    if (!participants.isEmpty())
	    {
		// The message for the new participant
		ACLMessage notif1 = subscription.getMessage().createReply();
		notif1.setPerformative(ACLMessage.INFORM);

		// The message for the old participants.
		// NOTE that the message is the same for all receivers (a part
		// from the
		// conversation-id that will be automatically adjusted by
		// Subscription.notify())
		// --> Prepare it only once outside the loop
		ACLMessage notif2 = (ACLMessage) notif1.clone();
		notif2.clearAllReceiver();
		Joined joined = new Joined();
		List<AID> who = new ArrayList<AID>(1);
		who.add(newId);
		joined.setWho(who);
		getContentManager().fillContent(notif2, joined);

		who.clear();
		Iterator<AID> it = participants.keySet().iterator();
		while (it.hasNext())
		{
		    AID oldId = it.next();

		    // Notify old participant
		    Subscription oldS = (Subscription) participants.get(oldId);
		    oldS.notify(notif2);

		    who.add(oldId);
		}

		// Notify new participant
		getContentManager().fillContent(notif1, joined);
		subscription.notify(notif1);
	    }

	    // Add the new subscription
	    participants.put(newId, subscription);
	    return false;
	} catch (Exception e)
	{
	    e.printStackTrace();
	    throw new RefuseException("Subscription error");
	}
    }

    protected void takeDown()
    {
	// Unsubscribe from the AMS
	send(amsSubscriber.getCancel());
	// FIXME: should inform current participants if any
    }

    public Context getContext()
    {
	return context;
    }

    public void setContext(Context context)
    {
	this.context = context;
    }
}
