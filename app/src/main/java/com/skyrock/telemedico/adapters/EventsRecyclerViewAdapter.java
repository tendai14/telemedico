package com.skyrock.telemedico.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyrock.telemedico.R;
import com.skyrock.telemedico.events.DataValuesModel;
import com.skyrock.telemedico.events.EventsModel;

import java.util.List;

public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<EventsRecyclerViewAdapter.EventsViewHolder> {

    private List<EventsModel> events;
    private Context context;

    public EventsRecyclerViewAdapter(List<EventsModel> events, Context context) {
        this.events = events;
        this.context = context;
    }


    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.events_item, parent, false);

        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

        List<DataValuesModel> data = events.get(position).getDataValues();

        for (int i = 0; i < data.size(); i++){
            for (int j = 0; j < i ; j++){
            holder.pulse.setText(data.get(j).getValue() + "bpm");
            holder.temperature.setText(data.get(i).getValue() + "\u2103");
            }

        }

    }

    @Override
    public int getItemCount() {
       return events == null ? 0: events.size();
    }

    class EventsViewHolder extends RecyclerView.ViewHolder{

        TextView pulse, temperature;

        Button viewNotes;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            pulse = itemView.findViewById(R.id.heart_rate);
            temperature = itemView.findViewById(R.id.temp);
            viewNotes = itemView.findViewById(R.id.btn_proceed_to_notes);
        }
    }
}
