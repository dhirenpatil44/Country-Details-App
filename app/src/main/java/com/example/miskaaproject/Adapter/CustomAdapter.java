package com.example.miskaaproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miskaaproject.Model.CountryModel;
import com.example.miskaaproject.R;
import com.example.miskaaproject.Utility.HTTPRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<CountryModel> countryModelList;

    public CustomAdapter(Context context, List<CountryModel> countryModelList) {
        this.context = context;
        this.countryModelList = countryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomAdapter.ViewHolder holder, int position) {

        CountryModel countryModel = countryModelList.get(position);

        holder.countryName.setText(countryModel.getName());
        holder.capital.setText("Capital: "+countryModel.getCapital());
        holder.region.setText("Region: "+countryModel.getRegion());
        holder.subregion.setText("Subregion"+countryModel.getSubregion());
        holder.population.setText("Population: "+(countryModel.getPopulation()));
        holder.border.setText("Border: "+ countryModel.getBorders());
        holder.language.setText("languages: "+countryModel.getLanguages());

        HTTPRequest.fetchData(context,countryModel.getFlag(), holder.flagImg);
    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView countryName, capital, region, subregion, population, border, language;
        private ImageView flagImg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            flagImg= itemView.findViewById(R.id.flagImg);
            countryName = itemView.findViewById(R.id.countryName);
            capital = itemView.findViewById(R.id.capital);
            region = itemView.findViewById(R.id.region);
            subregion = itemView.findViewById(R.id.subregion);
            population = itemView.findViewById(R.id.population);
            border = itemView.findViewById(R.id.border);
            language = itemView.findViewById(R.id.language);

        }
    }
}
