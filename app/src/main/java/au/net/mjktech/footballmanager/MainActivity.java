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

    private boolean firstTime = true;
    private boolean isChronometerRunning = false;
    private long timeWhenStopped = 0;
    private ArrayList<Player> squad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        squad = AssembleSquad();

        ListView playerListView = (ListView) findViewById(R.id.listView);
        final PlayerArrayAdapter adapter;
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
    protected void onPause()
    {
        super.onPause();
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.stop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (isChronometerRunning)
        {
            Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.start();
        }
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

    private ArrayList<Player> AssembleSquad()
    {
        ArrayList<Player> ret = new ArrayList<Player>();
        ret.add(new Player("Andrew"));
        ret.add(new Player("Brady"));
        ret.add(new Player("Cooper"));
        ret.add(new Player("Josh"));
        ret.add(new Player("Julian"));
        ret.add(new Player("Kaiden"));
        ret.add(new Player("Shannon","",false));
        return ret;
    }

    public void startStop(View view) {
        Chronometer chronometer = (Chronometer) view;
        if (firstTime) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            firstTime = false;
            timeWhenStopped = 0;
        }
        if (isChronometerRunning)
        {
            chronometer.stop();
            timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            isChronometerRunning = false;
        }
        else {
            chronometer.setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
            chronometer.start();
            isChronometerRunning = true;
        }
    }

    public void reset(View view) {
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        if (!isChronometerRunning)
        {
            firstTime = true;
        }
    }
}
