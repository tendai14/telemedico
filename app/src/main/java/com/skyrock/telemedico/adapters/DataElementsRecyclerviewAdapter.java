package com.skyrock.telemedico.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyrock.telemedico.Models.DataElementsModel;
import com.skyrock.telemedico.R;

import java.util.List;

public class DataElementsRecyclerviewAdapter extends RecyclerView.Adapter<DataElementsRecyclerviewAdapter.DataElementsViewHolder> {

    private List<DataElementsModel> dataElements;

    public DataElementsRecyclerviewAdapter(List<DataElementsModel> dataElements) {
        this.dataElements = dataElements;
    }


    @NonNull
    @Override
    public DataElementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.data_elements_item, parent, false);

        return new DataElementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataElementsViewHolder holder, int position) {

        holder.dataElement.setText(dataElements.get(position).getDisplayName());

        holder.dataElementCard.setOnClickListener((v)->{
//            Intent intent = new Intent(v.getContext(),);
//            intent.putExtra("id", dataElements.get(position).getId());
        });

    }

    @Override
    public int getItemCount() {
        return dataElements == null ? 0: dataElements.size();
    }

    class DataElementsViewHolder extends RecyclerView.ViewHolder{

        TextView dataElement;
        LinearLayout dataElementCard;

        public DataElementsViewHolder(@NonNull View itemView) {
            super(itemView);

            dataElement = itemView.findViewById(R.id.data_element);
            dataElementCard = itemView.findViewById(R.id.data_element_card);
        }
    }
}
