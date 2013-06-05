package com.utc.cards.common.ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.CFReflectiveIntrospector;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class CardsOntology extends Ontology implements CardsVocabulary
{

    private static final long serialVersionUID = 1217186994689530296L;

    private static Ontology instance = new CardsOntology();

    public static Ontology Instance()
    {
	return instance;
    }

    private CardsOntology()
    {

	super(ONTOLOGY_NAME, BasicOntology.getInstance(),
		new CFReflectiveIntrospector());

	try
	{
	    add(new PredicateSchema(JOINED), Joined.class);
	    add(new PredicateSchema(LEFT), Left.class);
	    add(new PredicateSchema(SPOKEN), Spoken.class);

	    PredicateSchema ps = (PredicateSchema) getSchema(JOINED);
	    ps.add(JOINED_WHO, (ConceptSchema) getSchema(BasicOntology.AID), 1,
		    ObjectSchema.UNLIMITED);

	    ps = (PredicateSchema) getSchema(LEFT);
	    ps.add(LEFT_WHO, (ConceptSchema) getSchema(BasicOntology.AID), 1,
		    ObjectSchema.UNLIMITED);

	    ps = (PredicateSchema) getSchema(SPOKEN);
	    ps.add(SPOKEN_WHAT,
		    (PrimitiveSchema) getSchema(BasicOntology.STRING));
	} catch (OntologyException oe)
	{
	    oe.printStackTrace();
	}
    }
}