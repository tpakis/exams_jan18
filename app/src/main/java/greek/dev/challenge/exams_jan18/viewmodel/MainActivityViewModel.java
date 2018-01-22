package greek.dev.challenge.exams_jan18.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import greek.dev.challenge.exams_jan18.model.Place;

/**
 * Created by programbench on 1/22/2018.
 */

public class MainActivityViewModel extends ViewModel {
    private static boolean internetState = false;
    private MutableLiveData<ArrayList<Place>> itemsListObservable;

    public void setInternetState(boolean internetState) {
        this.internetState = internetState;
        Log.v("ViewModel", String.valueOf(internetState));
    }

    public MutableLiveData<ArrayList<Place>> getItemsListObservable() {
        if (itemsListObservable == null){
            itemsListObservable = new MutableLiveData<ArrayList<Place>>();
        }
        return itemsListObservable;
    }
    public void addPlace(Place place){
        ArrayList<Place> placesListTemp;
        placesListTemp = itemsListObservable.getValue();
        if (placesListTemp == null) {
            placesListTemp = new ArrayList<Place>();
        }
        placesListTemp.add(place);

        itemsListObservable.setValue(placesListTemp);
    }
}
