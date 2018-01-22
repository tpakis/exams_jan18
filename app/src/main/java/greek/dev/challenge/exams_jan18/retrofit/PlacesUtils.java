package greek.dev.challenge.exams_jan18.retrofit;

/**
 * Created by programbench on 1/22/2018.
 */

public class PlacesUtils {
    final static String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/";

    public static PlacesService getPlacesService() {
        return RetrofitClient.getClient(PLACES_BASE_URL).create(PlacesService.class);
    }
}
