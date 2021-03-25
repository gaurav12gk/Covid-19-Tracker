package com.example.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<CountryModal> {
    private Context context;
    private List<CountryModal> list;
    private List<CountryModal> listfiltered;

    public CountryAdapter(@NonNull Context context, List<CountryModal> list) {

        super(context, R.layout.list_custom_item, list);
        this.context = context;
        this.list = list;
        this.listfiltered = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, null, true);
        TextView countryname = view.findViewById(R.id.countryname);
        ImageView flag = view.findViewById(R.id.flag);

        countryname.setText(listfiltered.get(position).getCountry());
        Glide.with(context).load(listfiltered.get(position).getFlag()).into(flag);

        return view;
    }

    @Override
    public int getCount() {
        return listfiltered.size();
    }

    @Nullable
    @Override
    public CountryModal getItem(int position) {
        return listfiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = list.size();
                    filterResults.values = list;

                } else {
                    List<CountryModal> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CountryModal itemsModel : list) {
                        if (itemsModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listfiltered = (List<CountryModal>) results.values;
                AffectedCountry.countryModalslist = (List<CountryModal>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}