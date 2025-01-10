package indubitables.config.old.subsystem;

import androidx.annotation.NonNull;

public class Sample implements Comparable<Sample> {
    String name;
    double x;
    double y;

    public Sample(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Sample o) {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " at (" + x + ", " + y + ")";
    }
}
