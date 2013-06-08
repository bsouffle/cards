package com.utc.cards.table.view;

import android.view.View;

import com.digitalaria.gama.wheel.Wheel;
import com.digitalaria.gama.wheel.WheelAdapter;
import com.digitalaria.gama.wheel.WheelAdapter.OnItemClickListener;
import com.digitalaria.gama.wheel.WheelAdapter.OnItemSelectionUpdatedListener;

// Utilisée pour la gestion des interactions avec la roue des jeux disponibles
public class MyWheelListener implements OnItemClickListener, OnItemSelectionUpdatedListener
{

    Wheel _wheel;
    TableSelectGameActivity _activity;

    public MyWheelListener(Wheel wheel, TableSelectGameActivity tableSelectGameActivity)
    {
        _wheel = wheel;
        _activity = tableSelectGameActivity;
    }

    @Override
    public void onItemSelectionUpdated(View view, int index)
    {
        _activity.setGameToLaunch(index);
    }

    @Override
    public void onItemClick(WheelAdapter<?> parent, View view, int position, long id)
    {

    }
}
