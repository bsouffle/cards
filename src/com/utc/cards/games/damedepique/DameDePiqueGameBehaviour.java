package com.utc.cards.games.damedepique;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.common.jade.AbstractGameBehaviour;

// Behaviour 
public class DameDePiqueGameBehaviour extends AbstractGameBehaviour
{
    private boolean d�butTour;
    private int tour;
    private int nbPli;
    private boolean partiFini;

    public DameDePiqueGameBehaviour()
    {
	super();
	tour = 1;
	d�butTour = true;
	nbPli = 0;
	partiFini = false;
	// TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 8354989627797871147L;
    private static Logger log = LoggerFactory
	    .getLogger(DameDePiqueGameBehaviour.class);

    @Override
    public void action()
    {
	// TODO: distribution des cartes entres les diff�rents joueurs
	if (d�butTour) // si l'on est au d�but du tour, il faut effectuer des
		       // �changes de cartes
	{
	    switch (tour) { // en fonction du tour que l'on est en train de
			    // faire, on �changera avec un voisin particulier
	    case 1:
		// TODO: �change de 3 cartes � donner au voisin de gauche
		break;
	    case 2:
		// TODO: �change de 3 cartes � donner au voisin de face
		break;
	    case 3:
		// TODO: �change de 3 cartes � donner au voisin de droite
		break;
	    case 4:
		// pas d'�changes de carte
		break;
	    }
	    tour = tour % 4 + 1;
	    d�butTour = false;
	} else
	{
	    if (nbPli < 13)
	    {

	    }
	}

	// TODO Auto-generated method stub
    }

    void echangeCarte(int direction)
    {
	// TODO:faire m�thode
    }

    @Override
    public boolean done()
    {
	// TODO Auto-generated method stub
	return partiFini; // si la parti est fini, alors l'agent game se fini
			  // �galement
    }

}
