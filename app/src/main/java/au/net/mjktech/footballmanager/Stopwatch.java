package au.net.mjktech.footballmanager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.base = this.getBase();
        savedState.timeWhenStopped = this.timeWhenStopped;
        savedState.started = this.isStarted;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        this.setBase(savedState.base);
        this.timeWhenStopped = savedState.timeWhenStopped;
        this.isStarted = savedState.started;
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
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.base);
            out.writeLong(this.timeWhenStopped);
            out.writeInt(this.started ? 1 : 0);
        }

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
