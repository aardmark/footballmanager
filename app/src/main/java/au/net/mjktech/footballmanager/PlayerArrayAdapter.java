package au.net.mjktech.footballmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class PlayerArrayAdapter extends ArrayAdapter<Player> {

    private final Context context;
    private final ArrayList<Player> players;

    public PlayerArrayAdapter(Context context, int textViewResourceId, ArrayList<Player> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.players = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player player = players.get(position);
        View rowView = convertView;
        if (rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.player_row, parent, false);
        }

        TextView playerNameView = (TextView)rowView;
        playerNameView.setText(player.getFirstName());
        if ((position < 4) && player.isAvailable())
        {
            playerNameView.setBackgroundColor(Color.GREEN);
        }
        else
        {
            playerNameView.setBackgroundColor(player.isAvailable() ? Color.YELLOW : Color.RED);
        }

        return rowView;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
