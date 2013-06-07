package com.utc.cards.table.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitalaria.gama.wheel.Wheel;
import com.utc.cards.R;
import com.utc.cards.games.GameContainer;
import com.utc.cards.model.game.IGame;
import com.utc.cards.table.TableController;

public class TableSelectGameActivity extends Activity
{
    private IGame _selectedGame = null;
    private TableController _controller;

    private Resources _res;
    private Wheel _wheel;
    private ArrayList<IGame> _games;
    private Point _screenDimention = new Point();

    public TableSelectGameActivity()
    {
	_controller = new TableController();

	_games = new ArrayList<IGame>(GameContainer.getGames());

	while (_games.size() < 6 && _games.size() > 0)
	{
	    _games.add(_games.get(0));
	}

	for (IGame t : _games)
	{
	    System.out.println("Logooo " + t.getName() + " - "
		    + t.getLogoResource());
	}
    }

    public void launchSelectedGame()
    {
	IGame selectedGame = getSelectedGame();
	if (selectedGame != null)
	{
	    _controller.loadGame(selectedGame);
	}
    }

    private IGame getSelectedGame()
    {
	return _selectedGame;
    }

    private void getScreenSize()
    {
	Display display = getWindowManager().getDefaultDisplay();
	display.getSize(_screenDimention);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_game);

	getScreenSize();

	drawCarousel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.select_game, menu);
	return true;
    }

    private void drawCarousel()
    {
	_res = getApplicationContext().getResources();

	int diameter = _screenDimention.x / 2;

	_wheel = (Wheel) findViewById(R.id.wheel);
	_wheel.setItems(getDrawableFromData(_games));

	_wheel.setWheelDiameter(diameter);

	MyWheelListener wl = new MyWheelListener(_wheel, this);

	_wheel.setOnItemClickListener(wl);
	_wheel.setOnItemSelectionUpdatedListener(wl);

	LinearLayout l = (LinearLayout) findViewById(R.id.wheelContainer);
	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.FILL_PARENT,
		LinearLayout.LayoutParams.FILL_PARENT);

	lp.bottomMargin = (int) (-_screenDimention.y * 0.8);

	l.setLayoutParams(lp);

	setGameToLaunch(0);
    }

    public void setGameToLaunch(int i)
    {
	_selectedGame = _games.get(i);

	updateGameToLaunchLabel(_selectedGame.getName());
    }

    public void launchGameClick(View view)
    {
	System.out.println("Launch Game");

	launchSelectedGame();

	Intent intent = new Intent(this, TableLaunchGameActivity.class);
	startActivity(intent);
    }

    private void updateGameToLaunchLabel(String name)
    {
	TextView label = (TextView) findViewById(R.id.gameToLaunchLabel);

	label.setText(name);
    }

    private Drawable[] getDrawableFromData(ArrayList<IGame> data)
    {
	Drawable[] ret = new Drawable[data.size()];
	int i = 0;
	for (IGame g : data)
	{
	    System.out.println("logoo: " + g.getLogoResource());
	    Drawable tmp = _res.getDrawable(g.getLogoResource());

	    double diff = (double) tmp.getIntrinsicHeight()
		    / (double) tmp.getIntrinsicWidth();

	    double w = _screenDimention.x * 0.15;
	    double h = w * diff;

	    ret[i++] = resize(tmp, w, h);
	}

	return ret;
    }

    private Drawable resize(Drawable image, double w, double h)
    {
	Bitmap d = ((BitmapDrawable) image).getBitmap();
	Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, (int) w, (int) h,
		false);
	return new BitmapDrawable(bitmapOrig);
    }

}
