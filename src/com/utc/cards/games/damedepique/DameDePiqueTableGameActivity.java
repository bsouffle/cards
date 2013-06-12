package com.utc.cards.games.damedepique;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.utc.cards.R;
import com.utc.cards.common.view.CardView;
import com.utc.cards.model.deck.Deck;
import com.utc.cards.model.game.Fold;
import com.utc.cards.table.view.AbstractTableGameActivity;

public class DameDePiqueTableGameActivity extends AbstractTableGameActivity
{

    private RelativeLayout _foldLayout;

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

        _foldLayout = (RelativeLayout) findViewById(R.id.FoldLayout);

        // Pour tests ...
        Deck base = new DameDePique().createDeck();

        Map<String, Deck> m = new HashMap<String, Deck>();
        Deck d = new Deck();
        d.add(base.get(0));
        m.put("Benoit", d);

        d = new Deck();
        d.add(base.get(1));
        m.put("Bobby", d);

        d = new Deck();
        d.add(base.get(2));
        m.put("Flo", d);

        d = new Deck();
        d.add(base.get(3));
        m.put("Arnaud", d);

        Fold f = new Fold(m);

        updateBoard(f);
    }

    @Override
    public void updateBoard(Fold fold)
    {
        if (_foldLayout != null)
        {
            CardView.CARD_WIDTH = (int) (screenDimention.x * 0.12);
            int cpt = 0;
            for (String k : fold.getFoldCards().keySet())
            {

                CardView cv = new CardView(fold.getFoldCards().get(k).get(0), _foldLayout.getContext());

                cv.setRotation(cpt * 90);

                Point p = getCenterDistance(cpt);

                cv.setX(p.x);
                cv.setY(p.y);

                _foldLayout.addView(cv);

                cpt++;

                if (cpt == 4)
                    break;
            }
        }
    }

    private Point getCenterDistance(int pos)
    {
        int d = (int) (screenDimention.x * 0.15);
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
