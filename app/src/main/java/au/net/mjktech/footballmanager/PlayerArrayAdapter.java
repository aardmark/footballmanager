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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.player_row, parent, false);
        TextView playerNameView = (TextView) rowView.findViewById(R.id.playerName);
        Player player = players.get(position);
        playerNameView.setText(player.getFirstName());

        if (position < 4)
        {
            playerNameView.setBackgroundColor(Color.GREEN);
        }
        else
        {
            if (player.isAvailable())
            {
                playerNameView.setBackgroundColor(Color.YELLOW);
            }
            else
            {
                playerNameView.setBackgroundColor(Color.RED);
            }
        }

        return rowView;
    }
}
