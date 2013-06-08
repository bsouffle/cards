package com.utc.cards.common.view.impl;

import static com.utc.cards.Constants.GMAIL;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.utc.cards.R;

/**
 * Fragment that appears in the "content_frame" of the CardsActivity
 */
public class FragmentCardsActivity extends Fragment
{

    private static Logger log = LoggerFactory
	    .getLogger(FragmentCardsActivity.class);

    private Point _screenDimention = new Point();
    private View _rootView;

    public FragmentCardsActivity()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	_rootView = inflater.inflate(R.layout.fragment_activity_cards,
		container, false);
	getScreenSize();

	setLogoDimension();
	setUserGmail();
	return _rootView;
    }

    private void getScreenSize()
    {
	Display display = this.getActivity().getWindowManager()
		.getDefaultDisplay();
	display.getSize(_screenDimention);
    }

    private void setLogoDimension()
    {
	ImageView img = (ImageView) _rootView.findViewById(R.id.logoCards);

	double diff = 0.5;
	double w = _screenDimention.y * 0.7;
	double h = w * diff;

	LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) w,
		(int) h);

	lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
	lp.topMargin = (int) (_screenDimention.y * 0.1);

	img.setLayoutParams(lp);
    }

    private void setUserGmail()
    {
	AccountManager manager = (AccountManager) this.getActivity()
		.getSystemService(Activity.ACCOUNT_SERVICE);
	Account[] list = manager.getAccounts();
	for (Account account : list)
	{
	    if (account.type.equalsIgnoreCase("com.google"))
	    {
		SharedPreferences settings = this.getActivity()
			.getSharedPreferences(JADE_CARDS_PREFS_FILE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(GMAIL, account.name);
		editor.commit();
		log.info("JADE_CARDS_PREFS_FILE :");
		log.info(GMAIL + " = " + account.name);
	    }
	}
    }

}