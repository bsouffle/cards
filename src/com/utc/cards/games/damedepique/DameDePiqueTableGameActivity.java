package com.utc.cards.games.damedepique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.utc.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.common.view.RectView;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.Fold;
import com.utc.cards.model.game.IGame;
import com.utc.cards.model.player.HumanPlayer;
import com.utc.cards.model.player.IPlayer;
import com.utc.cards.table.view.AbstractTableGameActivity;

public class DameDePiqueTableGameActivity extends AbstractTableGameActivity
{

    private RelativeLayout _foldCardsLayout;
    private RelativeLayout _foldPlayerNamesLayout;
    private TableLayout _tableLayout;

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
    public void onCreateSpecificView(Bundle savedInstanceState)
    {
        log.debug("Dame de pique: onCreateHook");

        _foldCardsLayout = (RelativeLayout) findViewById(R.id.foldCardsLayout);
        _foldPlayerNamesLayout = (RelativeLayout) findViewById(R.id.foldPlayerNamesLayout);
        _tableLayout = (TableLayout) findViewById(R.id.scoreTable);

        // Pour tests ...
        IGame g = new DameDePique();
        IPlayer p1 = new HumanPlayer("Benoit");
        IPlayer p2 = new HumanPlayer("Bobby");
        IPlayer p3 = new HumanPlayer("Flo");
        IPlayer p4 = new HumanPlayer("Vincent");

        p1.setDisplayColor(Color.RED);
        p2.setDisplayColor(Color.YELLOW);
        p3.setDisplayColor(Color.BLUE);
        p4.setDisplayColor(Color.GREEN);

        p1.setScore(100);
        p2.setScore(10);
        p3.setScore(-1000);
        p4.setScore(1);

        g.addPlayer(p1);
        g.addPlayer(p2);
        g.addPlayer(p3);
        g.addPlayer(p4);

        Deck base = g.getDeck();
        new HostModel();
        HostModel.Instance().setGame(g);

        Map<String, Deck> m = new HashMap<String, Deck>();
        Deck d = new Deck();
        d.add(base.get(0));
        m.put(p1.getName(), d);

        d = new Deck();
        d.add(base.get(1));
        m.put(p2.getName(), d);

        d = new Deck();
        d.add(base.get(2));
        m.put(p3.getName(), d);

        d = new Deck();
        d.add(base.get(3));
        m.put(p4.getName(), d);

        Fold f = new Fold(m);

        updateBoard(f);
        updateScore();
    }

    @Override
    public void updateBoard(Fold fold)
    {
        if (_foldCardsLayout != null && _foldPlayerNamesLayout != null)
        {
            int cpt = 0;

            CardView.CARD_WIDTH = (int) (displayDimentions.x * 0.12);

            int w = CardView.CARD_WIDTH;
            int h = w / 20;

            int x = displayDimentions.x / 2 - w / 2;
            int y = displayDimentions.y / 2 - h / 2;

            for (String k : fold.getFoldCards().keySet())
            {
                IPlayer p = HostModel.Instance().getGame().getPlayerByName(k);

                if (p != null)
                {
                    RectView rv = new RectView(x, y, w, h, p.getDisplayColor(), true, _foldPlayerNamesLayout.getContext());

                    CardView cv = new CardView(fold.getFoldCards().get(k).get(0), _foldCardsLayout.getContext());

                    int rot = cpt * 90;

                    cv.setRotation(rot);
                    rv.setRotation(rot);

                    Point pt = getCenterDistance(cpt);

                    cv.setX(pt.x);
                    cv.setY(pt.y);

                    rv.setTranslationX((int) (pt.x * 1.55));
                    rv.setTranslationY((int) (pt.y * 1.55));

                    _foldCardsLayout.addView(cv);
                    _foldPlayerNamesLayout.addView(rv);
                }

                cpt++;

                if (cpt == 4)
                    break;
            }
        }
    }

    private void updateScore()
    {
        ArrayList<Pair<String, Integer>> scoreList = new ArrayList<Pair<String, Integer>>();

        for (IPlayer p : HostModel.Instance().getGame().getPlayers())
        {
            log.debug("Player Score: " + p.getName() + " - " + p.getScore());

            scoreList.add(new Pair<String, Integer>(p.getName(), p.getScore()));
        }

        // On tri la liste afin que le joueur ayant le meilleur score soit en tête de liste
        orderScoreList(scoreList);

        drawScoreTable(scoreList);
    }

    private void orderScoreList(ArrayList<Pair<String, Integer>> scoreList)
    {
        boolean changementHappened = true;

        while (changementHappened)
        {
            changementHappened = false;

            for (int i = 0; i < scoreList.size() - 1; i++)
            {
                if (scoreList.get(i).second < scoreList.get(i + 1).second)
                {
                    Pair<String, Integer> tmp = scoreList.get(i + 1);
                    scoreList.remove(i + 1);
                    scoreList.add(i + 1, scoreList.get(i));

                    scoreList.remove(i);
                    scoreList.add(i, tmp);

                    changementHappened = true;
                }
            }
        }
    }

    private void drawScoreTable(List<Pair<String, Integer>> scores)
    {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) _tableLayout.getLayoutParams();
        lp.width = (int) (displayDimentions.x * 0.2);
        _tableLayout.setLayoutParams(lp);

        int textSize = (int) (displayDimentions.x * 0.015);

        TableRow row;
        TextView t1, t2;
        RectView rv;

        for (Pair<String, Integer> s : scores)
        {
            IPlayer p = HostModel.Instance().getGame().getPlayerByName(s.first);

            if (p != null)
            {
                row = new TableRow(this);

                rv = new RectView(0, 15, 20, 20, p.getDisplayColor(), false, _foldPlayerNamesLayout.getContext());

                t1 = new TextView(this);
                t2 = new TextView(this);

                t1.setPadding(10, 0, 0, 0);

                t1.setText(s.first);
                t2.setText("" + s.second);

                t1.setTextSize(textSize);
                t2.setTextSize(textSize);

                t1.setTypeface(null, Typeface.ITALIC);
                t2.setTypeface(null, Typeface.BOLD);

                row.addView(rv);
                row.addView(t1);
                row.addView(t2);

                _tableLayout.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private Point getCenterDistance(int pos)
    {
        int d = (int) (displayDimentions.x * 0.15);
        Point p = new Point();
        switch (pos)
        {
        case 0:
            p = new Point(0, -d);
            break;
        case 1:
            p = new Point(d, 0);
            break;
        case 2:
            p = new Point(0, d);
            break;
        case 3:
            p = new Point(-d, 0);
            break;
        default:
            break;
        }

        return p;
    }
}
