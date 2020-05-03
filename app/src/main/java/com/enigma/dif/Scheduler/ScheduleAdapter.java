package com.enigma.dif.Scheduler;

import android.content.Context;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.enigma.dif.Database.Events;
import com.enigma.dif.R;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {

    private ArrayList<Events> events;
    private Context context;
    private LayoutInflater inflater;

    public ScheduleAdapter(Context context, ArrayList<Events> events) {
        this.events = events;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Events getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.event_card_layout, parent, false);
        }
        TextView message = view.findViewById(R.id.main_text);
        TextView time = view.findViewById(R.id.timeTV);
        TextView location = view.findViewById(R.id.locationTV);

        message.setText(events.get(position).getMessage());
        time.setText(events.get(position).getTime());
        location.setText(events.get(position).getLocation());

        return view;
    }

    public void addAll(ArrayList<Events> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
