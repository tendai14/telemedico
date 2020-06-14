package com.skyrock.telemedico.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyrock.telemedico.Models.ProgramsModel;
import com.skyrock.telemedico.R;

import java.util.List;

public class ProgramsRecyclerviewAdapter extends RecyclerView.Adapter<ProgramsRecyclerviewAdapter.ProgramsViewHolder> {

    private List<ProgramsModel> programs;

    public ProgramsRecyclerviewAdapter(List<ProgramsModel> programs) {
        this.programs = programs;
    }

    @NonNull
    @Override
    public ProgramsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.programs_item, parent, false);

        return new ProgramsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramsViewHolder holder, int position) {

        holder.programS.setText(programs.get(position).getDisplayName());

    }

    @Override
    public int getItemCount() {
        return programs == null ? 0: programs.size();
    }

    class ProgramsViewHolder extends RecyclerView.ViewHolder{
        TextView programS;

        public ProgramsViewHolder(@NonNull View itemView) {
            super(itemView);

            programS = itemView.findViewById(R.id.programs);
        }
    }
}
