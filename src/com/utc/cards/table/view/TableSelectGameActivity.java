package com.utc.cards.table.view;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.digitalaria.gama.wheel.Wheel;
import com.utc.cards.R;
import com.utc.cards.games.GameContainer;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.IGame;
import com.utc.cards.utils.Utils;

public class TableSelectGameActivity extends Activity
{

    private Resources _res;
    private Wheel _wheel;
    private ArrayList<IGame> _games;
    private Point _screenDimention = new Point();

    private static Logger log = LoggerFactory.getLogger(TableSelectGameActivity.class);
    // private IHostAgent hostAgent;
    // private Handler mHandler = new Handler();

    private IGame _selectedGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        // chargement des jeux pour afficher la liste
        _games = new ArrayList<IGame>(GameContainer.getGames());

        // Arrangement graphique pour la roue: s'il y a moins de 6 jeux, on
        // duplique le premier jeu
        while (_games.size() < 6 && _games.size() > 0)
        {
            _games.add(_games.get(0));
        }

        getScreenSize();

        drawCarousel();
    }

    public void launchSelectedGame()
    {
        IGame selectedGame = getSelectedGame();
        if (selectedGame != null)
        {
            log.debug("launchSelectedGame");
            // la validation de la selection charge le jeu dans le model

            HostModel.Instance().setGame(selectedGame);
            // informe les joueurs du jeu selectionne
            // hostAgent.sendGameSelected();
            // affiche l'écran d'inscription
            Intent intent = new Intent(this, TableLaunchGameActivity.class);
            startActivity(intent);
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

    // private void loadHostAgent()
    // {
    // log.debug("loadHostAgent()");
    // // mHandler to update view from another thread
    // mHandler.post(new Runnable()
    // {
    //
    // @Override
    // public void run()
    // {
    // hostAgent = HostAgentManager.instance().getAgent(TableSelectGameActivity.this, Constants.CARDS_HOST_AGENT_NAME, IHostAgent.class);
    // log.debug("hostAgent loaded !");
    //
    // }
    // });
    // }

    private void drawCarousel()
    {
        _res = getApplicationContext().getResources();

        int diameter = (int) (_screenDimention.x / 1.3);

        _wheel = (Wheel) findViewById(R.id.wheel);
        _wheel.setItems(getDrawableFromData(_games));

        _wheel.setWheelDiameter(diameter);

        MyWheelListener wl = new MyWheelListener(_wheel, this);

        _wheel.setOnItemClickListener(wl);
        _wheel.setOnItemSelectionUpdatedListener(wl);

        LinearLayout l = (LinearLayout) findViewById(R.id.wheelContainer);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.bottomMargin = (int) (-_screenDimention.y);

        l.setLayoutParams(lp);

        setGameToLaunch(0); // Par défaut le jeu à lancer est le premier de la liste
    }

    private void updateGameToLaunchLabel(String name)
    {
        Button b = (Button) findViewById(R.id.selectGameButton);

        b.setText(name);
    }

    // public IHostAgent getHostAgent()
    // {
    // return hostAgent;
    // }

    public void setGameToLaunch(int index)
    {
        _selectedGame = _games.get(index);

        updateGameToLaunchLabel(_selectedGame.getName());
    }

    // Récupère une liste d'objects Drawable à partir des jeux IGame et de leur ressource logo
    private Drawable[] getDrawableFromData(ArrayList<IGame> data)
    {
        Drawable[] ret = new Drawable[data.size()];
        int i = 0;

        for (IGame g : data)
        {
            Drawable tmp = _res.getDrawable(g.getLogoResource());

            double diff = (double) tmp.getIntrinsicHeight() / (double) tmp.getIntrinsicWidth();

            double w = _screenDimention.x * 0.2;
            double h = w * diff;

            ret[i++] = Utils.resize(tmp, w, h);
        }

        return ret;
    }

    // Méthode "OnClick" liée à la vue
    public void selectGameClick(View view)
    {
        System.out.println("Launch Game");

        launchSelectedGame();
    }
}
