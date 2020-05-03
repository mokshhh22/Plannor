package com.enigma.dif.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.enigma.dif.Main.MainActivity;
import com.enigma.dif.R;
import com.enigma.dif.Scheduler.SchedulerActivity;
import com.enigma.dif.Utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private ArrayList<Date> datesList;
    private ArrayList<HolidayModel> holidayList;
    private Context context;

    public CalendarAdapter(Context context, ArrayList<Date> datesList, ArrayList<HolidayModel> holidayList) {
        this.context = context;
        this.datesList = datesList;
        this.holidayList = holidayList;
    }

    public void addToList(ArrayList<Date> data) {
        datesList = data;
        notifyDataSetChanged();
    }

    public void updateHolidayList(ArrayList<HolidayModel> list) {
        holidayList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Date date = datesList.get(position);
        SimpleDateFormat wd = new SimpleDateFormat("E");
        SimpleDateFormat md = new SimpleDateFormat("d");

        //convert date object to week name and day
        String weekName = wd.format(date);
        String dayValue = md.format(date);

        //set list item background colors
        if (Integer.parseInt(dayValue) % 2 == 0)
            holder.eventsLL.setBackgroundColor(ContextCompat.getColor(context, R.color.calendarListLight));
        else
            holder.eventsLL.setBackgroundColor(ContextCompat.getColor(context, R.color.calendarListDark));

        //highlight current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = sdf.format(new Date());
        String d2 = sdf.format(date);
        if (d1.equals(d2))
            holder.currentDateLL.setBackgroundColor(ContextCompat.getColor(context, R.color.WHITE));
        else
            holder.currentDateLL.setBackgroundColor(ContextCompat.getColor(context, R.color.BLACK));

        //set all text field
        holder.weekDay.setText(weekName);
        holder.monthDay.setText(dayValue);

        ArrayList<HolidayModel> temp = CommonUtils.filterHolidayByDay(holidayList, d2);
        if (temp.size() >= 2) {
            //set Public holiday details
            holder.holiday1.setVisibility(View.VISIBLE);
            holder.holiday2.setVisibility(View.VISIBLE);

            holder.PHTV1.setText(temp.get(0).getName());
            holder.PHTV2.setText(temp.get(1).getName());

        } else if (temp.size() == 1) {
            holder.holiday1.setVisibility(View.VISIBLE);
            holder.holiday2.setVisibility(View.GONE);
            holder.PHTV1.setText(temp.get(0).getName());
        } else {
            holder.holiday1.setVisibility(View.GONE);
            holder.holiday2.setVisibility(View.GONE);
        }

        //set on click listener
        holder.eventsLL.setOnClickListener(v -> {
            // Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, SchedulerActivity.class);
            intent.putExtra(SchedulerActivity.DATE_SELECTED, date.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView weekDay, monthDay, PHTV1, PHTV2;
        public LinearLayout eventsLL, holiday1, holiday2;
        public View currentDateLL;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDay = itemView.findViewById(R.id.weekDay);
            monthDay = itemView.findViewById(R.id.monthDay);
            eventsLL = itemView.findViewById(R.id.eventsLL);
            currentDateLL = itemView.findViewById(R.id.currentDayLL);
            holiday1 = itemView.findViewById(R.id.holiday1);
            holiday2 = itemView.findViewById(R.id.holiday2);
            PHTV1 = itemView.findViewById(R.id.PHTV1);
            PHTV2 = itemView.findViewById(R.id.PHTV2);
        }
    }
}
