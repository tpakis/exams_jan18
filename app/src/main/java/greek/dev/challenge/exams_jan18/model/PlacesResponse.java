package greek.dev.challenge.exams_jan18.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by programbench on 1/22/2018.
 */

public class PlacesResponse {
    @SerializedName("results")
    @Expose
    private List<Place> results = null;

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }
}
