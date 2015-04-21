package au.net.mjktech.footballmanager;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

public class Stopwatch extends Chronometer implements View.OnClickListener, View.OnLongClickListener {

    private long timeWhenStopped = 0;
    private boolean started = false;

    public Stopwatch(Context context) {
        super(context);
        init();
    }

    public Stopwatch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Stopwatch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void onClick(View view) {
        if (started)
            stop();
        else
            start();
    }

    @Override
    public boolean onLongClick(View view) {
        reset();
        return true;
    }

    @Override
    public void start() {
        setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        started = true;
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
        started = false;
    }

    public void reset() {
        setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }

    protected void onPause() {
        super.stop();
    }

    protected void onResume() {
        if (started) {
            super.start();
        }
    }
}
