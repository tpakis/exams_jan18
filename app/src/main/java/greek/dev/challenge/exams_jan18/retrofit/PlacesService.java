package greek.dev.challenge.exams_jan18.retrofit;

import greek.dev.challenge.exams_jan18.model.PlacesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by programbench on 1/22/2018.
 */

public interface PlacesService {
    @GET("json?key=AIzaSyDxMS3PF2yWo36KT5LtewvM6ggh5uarqA0")
    Call<PlacesResponse> getItems(@Query("query") String placesQuery);
}
