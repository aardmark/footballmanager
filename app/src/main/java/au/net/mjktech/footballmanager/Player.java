package au.net.mjktech.footballmanager;

public class Player implements Comparable<Player> {
    private String firstName;
    private String lastName;
    private boolean isAvailable;

    public Player(String firstName)
    {
        this(firstName, "");
    }

    public Player(String firstName, String lastName)
    {
        this(firstName, lastName, true);
    }

    public Player(String firstName, String lastName, boolean isAvailable)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAvailable = isAvailable;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String value)
    {
        firstName = value;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String value)
    {
        lastName = value;
    }

    public boolean isAvailable() { return isAvailable; }

    public void setAvailability(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public String toString()
    {
        return firstName;
    }

    @Override
    public int compareTo(Player that) {
        if (this.isAvailable() && that.isAvailable()) { return 0; }
        if (!this.isAvailable() && !that.isAvailable()) { return 0; }
        return (this.isAvailable()) ? -1 : 1;
    }
}

