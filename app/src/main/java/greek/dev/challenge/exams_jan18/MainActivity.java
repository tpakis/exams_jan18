package greek.dev.challenge.exams_jan18;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greek.dev.challenge.exams_jan18.adapter.PlacesAdapter;
import greek.dev.challenge.exams_jan18.model.Place;
import greek.dev.challenge.exams_jan18.model.PlacesResponse;
import greek.dev.challenge.exams_jan18.retrofit.PlacesService;
import greek.dev.challenge.exams_jan18.retrofit.PlacesUtils;
import greek.dev.challenge.exams_jan18.viewmodel.MainActivityViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PlacesAdapter.PlacesResultsAdapterOnClickHandler {
    @BindView(R.id.google_api_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_button)
    Button mSearchButton;
    @BindView(R.id.search_query)
    EditText mSearchQueryText;
    @BindString(R.string.error_query)
    String errorquery;
    @BindView(R.id.todo_list_empty_view)
    public LinearLayout emptyView;
    private PlacesService mService;
    private MainActivityViewModel viewModel;
    private PlacesAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mService = PlacesUtils.getPlacesService();
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlacesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        checkForInternet();
        viewModel.getItemsListObservable().observe(MainActivity.this, new Observer<ArrayList<Place>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Place> mPlacesItemsList) {
                if (mPlacesItemsList.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
                mAdapter.setPlacesRvResults(mPlacesItemsList);
            }
        });
    }

    private void checkForInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        viewModel.setInternetState(netInfo != null && netInfo.isConnectedOrConnecting());
        //viewModel.getMyNasaItemsList("apollo");
    }
    @OnClick(R.id.search_button)
    public void startQuery(View view) {
        if(viewModel.getInternetState()) {
            String query = mSearchQueryText.getText().toString();
            if (query.length() > 0) {
                query = query.replace(' ', '+');
                loadAnswers(query);
            } else {
                Toast.makeText(MainActivity.this, errorquery, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadAnswers(String query) {
        mService.getItems(query).enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                List<Place> data = new ArrayList<Place>();
                if (response.isSuccessful()) {
                    data = response.body().getResults();
                    for (Place item : data) {
                        if (item.getOpeningHours() == null) {
                            Log.d("MainActivity", "succesfull json load");
                        } else {
                           viewModel.addPlace(item);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                // showErrorMessage();
                String message = t.getMessage();
                Log.d("failure", message);

            }
        });
    }

    @Override
    public void onClick(Place selectedPlaceItem) {
       // Toast.makeText(selectedPlaceItem.toString(),Toast.LENGTH_SHORT).show();
    }
}
