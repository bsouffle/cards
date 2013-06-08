package com.utc.cards.games.damedepique;

import android.os.Bundle;

import com.utc.cards.R;
import com.utc.cards.table.view.AbstractTableGameActivity;

public class DameDePiqueTableGameActivity extends AbstractTableGameActivity
{

    @Override
    public int getLayout()
    {
        return R.layout.activity_dame_de_pique_table_game;
    }

    @Override
    protected int getMenu()
    {
        return R.menu.menu_dame_de_pique_game_host;
    }

    @Override
    public void onCreateHook(Bundle savedInstanceState)
    {

    }

}
