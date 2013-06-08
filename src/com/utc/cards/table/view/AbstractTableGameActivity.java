package com.utc.cards.table.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public abstract class AbstractTableGameActivity extends Activity implements ITableGameActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onCreateHook(savedInstanceState);
    }

    public abstract void onCreateHook(Bundle savedInstanceState);

}
