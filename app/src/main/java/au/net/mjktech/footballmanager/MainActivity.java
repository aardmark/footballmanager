package au.net.mjktech.footballmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends ActionBarActivity {
    static final String SQUAD = "squad";
    static boolean resetOnSubstitution = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        resetOnSubstitution = sharedPref.getBoolean("reset_on_substitution_preference", true);

        final ArrayList<Player> squad;
        final PlayerArrayAdapter adapter;
        ListView playerListView = (ListView) findViewById(R.id.listView);

        if (savedInstanceState == null) {
            squad = AssembleSquad();
        }
        else {
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
                if (resetOnSubstitution) ((Stopwatch) findViewById(R.id.stopwatch)).reset();
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CharSequence text = resetOnSubstitution ? "Resetting on subs" : "Not Resetting on subs";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((Stopwatch) findViewById(R.id.stopwatch)).onPause();
        ((Stopwatch) findViewById(R.id.matchTimer)).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((Stopwatch) findViewById(R.id.stopwatch)).onResume();
        ((Stopwatch) findViewById(R.id.matchTimer)).onResume();
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

    public void showPreferences(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return;
            default:
                return;
        }
    }

    private ArrayList<Player> AssembleSquad() {
        ArrayList<Player> ret = new ArrayList<>();
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
