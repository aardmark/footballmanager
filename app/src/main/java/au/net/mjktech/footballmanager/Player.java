package au.net.mjktech.footballmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Comparable<Player>, Parcelable {
    private String firstName;
    private String lastName;
    private boolean isAvailable;

    public Player(String firstName) {
        this(firstName, "");
    }

    public Player(String firstName, String lastName) {
        this(firstName, lastName, true);
    }

    public Player(String firstName, String lastName, boolean isAvailable) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAvailable = isAvailable;
    }

    public Player(Parcel parcel) {
        firstName = parcel.readString();
        lastName = parcel.readString();
        isAvailable = (parcel.readInt() == 1);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        firstName = value;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        lastName = value;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return firstName;
    }

    @Override
    public int compareTo(Player that) {
        if (this.isAvailable() && that.isAvailable()) {
            return 0;
        }
        if (!this.isAvailable() && !that.isAvailable()) {
            return 0;
        }
        return (this.isAvailable()) ? -1 : 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeInt(isAvailable ? 1 : 0);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        public Player createFromParcel(Parcel parcel) {
            return new Player(parcel);
        }
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}

