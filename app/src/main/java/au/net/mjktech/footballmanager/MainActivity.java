package au.net.mjktech.footballmanager;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends ActionBarActivity {
    static final String SQUAD = "squad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Player> squad;
        final PlayerArrayAdapter adapter;
        ListView playerListView = (ListView) findViewById(R.id.listView);

        if (savedInstanceState == null) {
            squad = AssembleSquad();
        }
        else {
            Stopwatch stopwatch = (Stopwatch) findViewById(R.id.stopwatch);
            squad = savedInstanceState.getParcelableArrayList(SQUAD);
        }

        adapter = new PlayerArrayAdapter(this, R.layout.player_row, squad);
        playerListView.setAdapter(adapter);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (!squad.get(position).isAvailable()) return;
                squad.add(squad.remove(position));
                Collections.sort(squad);
                adapter.notifyDataSetChanged();
            }
        });
        playerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                Player player = squad.get(position);
                player.setAvailability(!player.isAvailable());
                if (!player.isAvailable()) squad.add(squad.remove(position));
                Collections.sort(squad);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Stopwatch stopwatch = (Stopwatch) findViewById(R.id.stopwatch);
        stopwatch.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Stopwatch stopwatch = (Stopwatch) findViewById(R.id.stopwatch);
        stopwatch.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        ListView playerListView = (ListView) findViewById(R.id.listView);
        ArrayList<Player> squad = ((PlayerArrayAdapter)playerListView.getAdapter()).getPlayers();
        savedInstanceState.putParcelableArrayList(SQUAD, squad);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Player> AssembleSquad() {
        ArrayList<Player> ret = new ArrayList<Player>();
        ret.add(new Player("Andrew"));
        ret.add(new Player("Brady"));
        ret.add(new Player("Cooper"));
        ret.add(new Player("Josh"));
        ret.add(new Player("Julian"));
        ret.add(new Player("Kaiden"));
        ret.add(new Player("Shannon"));
        return ret;
    }
}
