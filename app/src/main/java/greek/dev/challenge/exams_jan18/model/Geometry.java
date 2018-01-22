package greek.dev.challenge.exams_jan18.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programbench on 1/22/2018.
 */

public class Geometry {
    @SerializedName("location")
    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
