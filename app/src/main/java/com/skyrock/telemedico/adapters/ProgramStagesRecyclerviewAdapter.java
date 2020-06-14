package com.skyrock.telemedico.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyrock.telemedico.Models.ProgramStagesModel;
import com.skyrock.telemedico.R;

import java.util.List;

public class ProgramStagesRecyclerviewAdapter extends RecyclerView.Adapter<ProgramStagesRecyclerviewAdapter.ProgramStagesViewHolder>{

    private List<ProgramStagesModel> programStages;

    public ProgramStagesRecyclerviewAdapter(List<ProgramStagesModel> programStages) {
        this.programStages = programStages;
    }

    @NonNull
    @Override
    public ProgramStagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.program_stages_item, parent, false);

        return new ProgramStagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramStagesViewHolder holder, int position) {
        holder.programStage.setText(programStages.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return programStages == null ? 0 : programStages.size() ;
    }

    class ProgramStagesViewHolder extends RecyclerView.ViewHolder{

        TextView programStage;

        public ProgramStagesViewHolder(@NonNull View itemView) {
            super(itemView);
            programStage = itemView.findViewById(R.id.program_stages);
        }
    }
}
