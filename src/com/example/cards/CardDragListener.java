package com.example.cards;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class CardDragListener implements OnDragListener
{
	private CardsContainer _container;

	private View _selectedView;
	
	public CardDragListener(CardsContainer container)
	{
		_container = container;
	}
	
	@Override
	public boolean onDrag(View view, DragEvent dragEvent) {
		int action = dragEvent.getAction();
	    
		switch (action) 
		{
	    	case DragEvent.ACTION_DRAG_STARTED:
	    		//view.setBackgroundColor(Color.YELLOW);
	    		
	    		_selectedView = (View) dragEvent.getLocalState();
	    		break;
	    		
	    	case DragEvent.ACTION_DRAG_ENTERED:
	    		//view.setBackgroundColor(Color.RED);
	    		break;
	    		
	    	case DragEvent.ACTION_DRAG_EXITED:        
	    		//view.setBackgroundColor(Color.BLUE);
	    		break;
	    	
	    	case DragEvent.ACTION_DRAG_LOCATION:
	    		if(_selectedView != null)
	    		{
	    			float x = dragEvent.getX();
		    		
		    		String cardName = (String) _selectedView.getTag();
		    		
		    		_container.update(cardName, x);
	    		}
	    		
	    		break;
	    	
	    	case DragEvent.ACTION_DROP:
	    		if(_selectedView != null)
	    		{
	    			float x = dragEvent.getX();
		    		
		    		String cardName = (String) _selectedView.getTag();
		    		
		    		//System.out.println("X: " + x + " Y: " + y);
		    		
		    		cardName = (String) _selectedView.getTag();
		    		
		    		_container.update(cardName, x);
	    		
		    		_selectedView.setVisibility(View.VISIBLE);
	    		}
	    		
			    break;
			    
	    	case DragEvent.ACTION_DRAG_ENDED:
	    		//view.setBackgroundColor(Color.GREEN);
	    		
	    		_container.refresh();
	    		
	    	default:
	    		break;
	    }
		
	    return true;
	}

}
