package com.skyrock.telemedico.adapters;

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

    @NonNull
    @Override
    public OrgUnitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrgUnitsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
