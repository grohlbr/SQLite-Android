package com.rocha.arthur.gerenciarveiculo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rocha.arthur.gerenciarveiculo.R;
import com.rocha.arthur.gerenciarveiculo.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 06/07/16.
 */

public class VehicleListAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<Veiculo> mVehicleList;
    VehicleFilters vehicleFilters;

    public VehicleListAdapter(Context mContext, List<Veiculo> mVehicleList) {
        this.mContext = mContext;
        this.mVehicleList = mVehicleList;
    }

    @Override
    public int getCount() {
        return mVehicleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVehicleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_vehicle_list,null);
        TextView tvBrand = (TextView) v.findViewById(R.id.tvBrand);
        TextView tvModel = (TextView) v.findViewById(R.id.tvModel);
        TextView tvBoard = (TextView) v.findViewById(R.id.tvBoard);
        TextView tvYear = (TextView) v.findViewById(R.id.tvYear);

        tvBrand.setText(mVehicleList.get(position).getMarca());
        tvModel.setText(mVehicleList.get(position).getModelo());
        tvBoard.setText(mVehicleList.get(position).getPlaca());
        tvYear.setText(mVehicleList.get(position).getAno());

        v.setTag(mVehicleList.get(position).getId());

        return v;
    }

    @Override
    public Filter getFilter() {
        if (vehicleFilters == null)
            vehicleFilters = new VehicleFilters();

        return vehicleFilters;
    }

    private class VehicleFilters extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                filterResults.values = mVehicleList;
                filterResults.count = mVehicleList.size();
            }else{
                ArrayList<Veiculo> filteredVehicles = new ArrayList<Veiculo>();

                for (Veiculo v : mVehicleList){
                    if(v.getMarca().toUpperCase().contains(constraint.toString())){
                        filteredVehicles.add(v);
                    }
                }

                filterResults.values = filteredVehicles;
                filterResults.count = filteredVehicles.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mVehicleList = (ArrayList<Veiculo>) results.values;
            notifyDataSetChanged();
        }
    }
}

