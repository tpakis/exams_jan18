package greek.dev.challenge.exams_jan18.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greek.dev.challenge.exams_jan18.R;
import greek.dev.challenge.exams_jan18.model.OpeningHours;
import greek.dev.challenge.exams_jan18.model.Place;

/**
 * Created by programbench on 1/22/2018.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ResultsHolder> {
    private final PlacesResultsAdapterOnClickHandler mClickHandler;
    private ArrayList<Place> placeItemsResults;

    public PlacesAdapter(PlacesResultsAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    @Override
    public ResultsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);

        return new ResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultsHolder holder, int position) {
        OpeningHours mHours;
        Place placeItem = placeItemsResults.get(position);
        holder.itemName.setText(placeItem.getName());
        holder.itemAdress.setText(placeItem.getFormattedAddress());
        holder.itemRating.setText(placeItem.getRating().toString()+ " stars");
        mHours = placeItem.getOpeningHours();
        if (mHours.getOpenNow()) {
            holder.itemOpem.setText("Open Now");
        } else {
            holder.itemOpem.setText("Closed Now");
        }
    }

    @Override
    public int getItemCount() {
        if (placeItemsResults == null) return 0;
        return placeItemsResults.size();
    }

    public void setPlacesRvResults(ArrayList<Place> placeItemsResults) {
        this.placeItemsResults = placeItemsResults;
        notifyDataSetChanged();
    }

    public interface PlacesResultsAdapterOnClickHandler {
        void onClick(Place selectedPlaceItem);
    }

    public class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textView_Name)
        public TextView itemName;
        @BindView(R.id.textView_Adress)
        public TextView itemAdress;
        @BindView(R.id.textView_Rating)
        public TextView itemRating;
        @BindView(R.id.textView_Open)
        public TextView itemOpem;


        public ResultsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAdapterPosition();
            Place selectedItem = placeItemsResults.get(positionClicked);
            mClickHandler.onClick(selectedItem);
        }
    }

}

