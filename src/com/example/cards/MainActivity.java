package com.example.cards;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends Activity {

	private CardsContainer _cardsContainer;
	
	private Point _screenDimention = new Point();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		// Get the size of the display screen
		getScreenSize();
		
		// Draw some cards
		drawCards();
		
		// Set the panel used to send cards to the host
		setSendingPanel();
		
		// Set the bar which allows to modify the distance between two cards
		setSeekBar();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{
			case R.id.action_clue:
				_cardsContainer.centerCard("Club_4");
				break;

			default:
				break;
		}

		return true;
	}
	
	private void getScreenSize()
	{
		Display display = getWindowManager().getDefaultDisplay();
		display.getSize(_screenDimention);
	}
	
	private void drawCards() {
		final View view = findViewById(R.id.cardsLayout);
	
		if(view != null)
		{
			if(_screenDimention.y > 0)
			{
				// Set the cards dimension according to the size of the display screen
				int w = (int) (_screenDimention.x * 21/100);
				Card.CARD_WIDTH = w;
				
				// Set margins
				int m = (int) (_screenDimention.y * 3/100);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, m, 0, m);
				view.setLayoutParams(lp);
				
				// The container of the cards
				_cardsContainer = new CardsContainer((RelativeLayout) view, _screenDimention);
			}
		}
	}
	
	private void setSendingPanel() 
	{
		final View view = findViewById(R.id.sendingLayout);
		
		if(view != null)
		{
			if(_screenDimention.y > 0)
			{
				int h = (int) (_screenDimention.y * 10/100);
				view.setMinimumHeight(h);
			}
			
			
			view.setBackgroundColor(Color.GRAY);
			view.setOnDragListener(new SendDragListener(_cardsContainer));
		}		
	}
	
	private void setSeekBar() 
	{
		final View view = findViewById(R.id.seekBar);
		
		if(view != null)
		{			
			SeekBar bar = (SeekBar) view;
			
			bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
				{
					// When the user changes the progress value, we change the distance between cards
					CardsContainer.CARD_DISTANCE = progress;
					_cardsContainer.refresh();
				}

				@Override
				public void onStartTrackingTouch(SeekBar arg0){}

				@Override
				public void onStopTrackingTouch(SeekBar arg0){}
				
			});
		}
	}
}
