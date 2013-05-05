package com.example.cards;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.trevorpage.tpsvg.SVGParserRenderer;
import com.trevorpage.tpsvg.SVGView;

public class Card 
{
	public static int CARD_WIDTH = 200;
	
	private Context _context;
	private int _value;
	private String _name;
	private int _resourceId;
	private SVGView _view;
	private boolean _hasBeenSent;
	
	public Card(Context context, String name, int resource)
	{
		_context = context;
		_name = name;
		_resourceId = resource;
	}

	public Card(Context context, String name, int resource, int value)
	{
		_context = context;
		_name = name;
		_resourceId = resource;
		_value = value;
	}

	public int get_value() {
		return _value;
	}

	public String get_name() {
		return _name;
	}

	public int get_resourceId() {
		return _resourceId;
	}
	
	public SVGView getView()
	{
		if(_view == null)
		{
			_view = new SVGView(_context);
        	
        	// Tag of the view (-> card name)
			_view.setTag(get_name());
        	
        	// Set the dimension
        	RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(CARD_WIDTH, LayoutParams.WRAP_CONTENT);
        	_view.setLayoutParams(p);
        	
        	// Parse the SVG resource to view object
	        SVGParserRenderer image = new SVGParserRenderer(_context, get_resourceId());
	        _view.setSVGRenderer(image, null);
	        
	        // Add listener for handling Drag/Drog actions
	        _view.setOnTouchListener(new CardTouchListener());
	        
		}
		
		return _view;
	}
	
	public boolean hasBeenSent()
	{
		return _hasBeenSent;
	}
	
	public void setHasBeenSent(boolean b)
	{
		_hasBeenSent = b;
	}
}
