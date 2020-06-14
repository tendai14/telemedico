package com.skyrock.telemedico.adapters;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.skyrock.telemedico.R;
import com.skyrock.telemedico.events.DataValuesModel;
import com.skyrock.telemedico.events.EventsModel;
import com.skyrock.telemedico.ui.EventsActivity;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

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

            if (data.get(j).getValue().equals("75")){
                showNotification("Check Your Pulse, and follow Self diagnosis instructions");
            }

            if (data.get(j).getValue().equals("56")){
                    showNotification("Check Your temperature, and follow Self diagnosis instructions");
                }
            }

        }

        holder.viewNotes.setOnClickListener((v)->{
            Toast.makeText(context, "hahaha", Toast.LENGTH_SHORT).show();
        });

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


  private  void showNotification(String message){
        Intent intent = new Intent(context, EventsActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      Notification n  = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
          n = new Notification.Builder(context)
                  .setContentTitle("Condition Critical")
                  .setContentText(message)
                  .setSmallIcon(R.drawable.heart_rate_ico_two)
                  .setContentIntent(pIntent)
                  .setAutoCancel(true)
                  .setDefaults(Notification.DEFAULT_VIBRATE)
                  .setSound(alarmSound)
                  .build();
      }

      NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);



    }
}
