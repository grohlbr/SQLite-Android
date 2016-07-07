package com.rocha.arthur.gerenciarveiculo.filters;

import android.widget.Filter;

/**
 * Created by arthur on 06/07/16.
 */

public class VehicleFilter extends Filter {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        if(constraint == null || constraint.length() == 0){
            filterResults.values = "";
        }


        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

    }
}
