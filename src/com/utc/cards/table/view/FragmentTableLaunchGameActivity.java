package com.utc.cards.table.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utc.cards.R;
import com.utc.cards.model.HostModel;

public class FragmentTableLaunchGameActivity extends Fragment
{
    private static Logger log = LoggerFactory.getLogger(FragmentTableLaunchGameActivity.class);

    private Point _screenDimention = new Point();
    private View _rootView;
    private ListView _listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _rootView = inflater.inflate(R.layout.fragment_activity_launch_game, container, false);
        getScreenSize();

        setSelectedGameLogoAndLabel();
        updatePlayerList();

        return _rootView;
    }

    private void getScreenSize()
    {
        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(_screenDimention);
    }

    public void setSelectedGameLogoAndLabel()
    {
        // Logo du jeu selectionné
        //
        //

        ImageView img = (ImageView) _rootView.findViewById(R.id.selectedGameLogo);

        Drawable tmp = this.getActivity().getApplicationContext().getResources().getDrawable(HostModel.Instance().getGame().getLogoResource());

        double diff = (double) tmp.getIntrinsicHeight() / (double) tmp.getIntrinsicWidth();

        double w = _screenDimention.x * 0.25;
        double h = w * diff;

        img.setImageResource(HostModel.Instance().getGame().getLogoResource());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) w, (int) h);

        lp.gravity = Gravity.CENTER_VERTICAL;

        img.setLayoutParams(lp);

        //
        //

        // Label du jeu selectionné
        //
        //

        TextView text = (TextView) _rootView.findViewById(R.id.selectedGameLabel);

        text.setText(HostModel.Instance().getGame().getName());

        //
        //

        // Positionnement des infos relatives au jeu selectionné (label + logo)
        //
        //

        LinearLayout container = (LinearLayout) _rootView.findViewById(R.id.selectedGameInfo);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        lp2.leftMargin = (int) (_screenDimention.x * 0.2);
        lp2.topMargin = (int) (_screenDimention.y * 0.2);

        container.setLayoutParams(lp2);

        //
        //

    }

    public void updatePlayerList()
    {
        if (_listView == null)
        {
            _listView = (ListView) _rootView.findViewById(R.id.subscriberList);

            // _listView.setOnItemClickListener(new
            // AdapterView.OnItemClickListener()
            // {
            //
            // @Override
            // public void onItemClick(AdapterView<?> parent, final View view,
            // int position, long id)
            // {
            // log.debug("Player clicked: " +
            // _selectedGame.getPlayers().get(position).getName());
            // }
            // });

            double w = _screenDimention.x * 0.3;
            System.out.println("test: " + w);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) w, LayoutParams.WRAP_CONTENT);

            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.topMargin = 15;
            lp.bottomMargin = 120;

            _listView.setLayoutParams(lp);
        }

        String[] players = (String[]) HostModel.Instance().getPlayersMap().keySet().toArray();
        // List<String> players = Arrays.asList("Benoit", "Bobby", "Flo", "Arnaud", "Benoit", "Bobby", "Flo", "Arnaud", "Benoit", "Bobby", "Flo", "Arnaud");
        _listView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, players));

    }
}
