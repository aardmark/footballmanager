package au.net.mjktech.footballmanager;

import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    boolean firstTime = true;
    boolean isChronometerRunning = false;
    private long timeWhenStopped = 0;
    String[] squad = { "Andrew", "Brady", "Cooper", "Josh", "Julian", "Kaiden", "Shannon" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView playerListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, squad);
        playerListView.setAdapter(adapter);
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
