package au.net.mjktech.footballmanager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

public class Stopwatch extends Chronometer implements View.OnClickListener, View.OnLongClickListener {

    private long timeWhenStopped = 0;
    private boolean isStarted = false;

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

    @Override
    public void onClick(View view) {
        if (isStarted)
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
        isStarted = true;
        super.start();
    }

    @Override
    public void stop() {
        timeWhenStopped = getBase() - SystemClock.elapsedRealtime();
        isStarted = false;
        super.stop();
    }

    public void reset() {
        setBase(SystemClock.elapsedRealtime());
        timeWhenStopped = 0;
    }

    protected void onPause() {
        super.stop();
    }

    protected void onResume() {
        if (isStarted) {
            super.start();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.base = this.getBase();
        ss.timeWhenStopped = this.timeWhenStopped;
        ss.started = this.isStarted;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        this.setBase(ss.base);
        this.timeWhenStopped = ss.timeWhenStopped;
        this.isStarted = ss.started;
        if (!this.isStarted) this.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
    }

    static class SavedState extends BaseSavedState {
        long base;
        long timeWhenStopped;
        boolean started;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.base = in.readLong();
            this.timeWhenStopped = in.readLong();
            this.started = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.base);
            out.writeLong(this.timeWhenStopped);
            out.writeInt(this.started ? 1 : 0);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
