package com.enigma.dif.Scheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enigma.dif.Database.Events;
import com.enigma.dif.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TodoAdapter extends BaseAdapter {

    private ArrayList<Events> events;
    private Context context;
    private LayoutInflater inflater;

    public TodoAdapter(Context context, ArrayList<Events> events) {
        this.events = events;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position)  ;
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
        LinearLayout sch = view.findViewById(R.id.ScheduleExtraLL);
        sch.setVisibility(View.GONE);

        message.setText(events.get(position).getMessage());
        return view;
    }

    public void addAll(ArrayList<Events> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
