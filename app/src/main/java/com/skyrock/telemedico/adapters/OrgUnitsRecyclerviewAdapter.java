package com.skyrock.telemedico.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.skyrock.telemedico.Models.OrganisationUnitsModel;
import com.skyrock.telemedico.R;
import java.util.List;

public class OrgUnitsRecyclerviewAdapter extends RecyclerView.Adapter<OrgUnitsRecyclerviewAdapter.OrgUnitsViewHolder> {

    private List<OrganisationUnitsModel> orgUnits;

    public OrgUnitsRecyclerviewAdapter(List<OrganisationUnitsModel> orgUnits) {
        this.orgUnits = orgUnits;
    }

    @NonNull
    @Override
    public OrgUnitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.tei_item, parent, false);

        return new OrgUnitsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrgUnitsViewHolder holder, int position) {
        holder.orgUnit.setText(orgUnits.get(position).getDisplayName());

    }

    @Override
    public int getItemCount() {
        return orgUnits == null ? 0: orgUnits.size();
    }


    class OrgUnitsViewHolder extends RecyclerView.ViewHolder{

        TextView orgUnit;
        LinearLayout layout;

        public OrgUnitsViewHolder(@NonNull View itemView) {
            super(itemView);

            orgUnit = itemView.findViewById(R.id.org_unit);
            layout = itemView.findViewById(R.id.tei_card);
        }
    }
}
