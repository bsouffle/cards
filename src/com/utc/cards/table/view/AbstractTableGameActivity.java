package com.utc.cards.table.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.utc.cards.Constants;
import com.utc.cards.table.jade.agent.HostAgentManager;
import com.utc.cards.table.jade.agent.gameAgent.IGameAgent;
import com.utc.cards.table.jade.agent.hostAgent.IHostAgent;
import com.utc.cards.table.jade.agent.rulesAgent.IRulesAgent;
import com.utc.cards.utils.Utils;

public abstract class AbstractTableGameActivity extends Activity implements ITableGameActivity
{
    protected static Logger log = LoggerFactory.getLogger(AbstractTableGameActivity.class);

    protected IRulesAgent rulesAgent;
    protected IGameAgent gameAgent;
    protected IHostAgent hostAgent;
    protected Point displayDimentions = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        displayDimentions = Utils.getDisplayWindowsDimention(this);

        // r��cup��ration des agents
        // loadAgents();

        // log.debug("onCreate() : agent charg��s pour l'activity de JEU");

        setContentView(getLayout());

        onCreateSpecificView(savedInstanceState);
    }

    public abstract void onCreateSpecificView(Bundle savedInstanceState);

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(getMenu(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        // case R.id.game_menu_host:
        // // Intent showSettings = new Intent(this, SettingsActivity.class);
        // // startActivity(showSettings);
        // return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public abstract int getLayout();

    private void loadAgents()
    {
        hostAgent = HostAgentManager.instance().getAgent(this, Constants.CARDS_HOST_AGENT_NAME, IHostAgent.class);
        gameAgent = HostAgentManager.instance().getAgent(this, Constants.CARDS_HOST_GAME_AGENT_NAME, IGameAgent.class);
        rulesAgent = HostAgentManager.instance().getAgent(this, Constants.CARDS_HOST_RULES_AGENT_NAME, IRulesAgent.class);
    }

    protected abstract int getMenu();

}
