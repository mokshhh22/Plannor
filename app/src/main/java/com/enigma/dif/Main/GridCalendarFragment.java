package com.enigma.dif.Main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.enigma.dif.R;

import java.util.Calendar;
import java.util.Date;

public class GridCalendarFragment extends Fragment {

    private CalendarView gridCalendarView;
    private Context context;
    private onGridFragmentInteraction listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            listener = (onGridFragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDateListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_calendar_view, container, false);
        gridCalendarView = view.findViewById(R.id.grid_calendar);
        //return view layout
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //set listener on grid calendar date change
        gridCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                listener.updateListOnChange(calendar);
            }
        });
    }

    public interface onGridFragmentInteraction {
        void updateListOnChange(Calendar c);
    }
}
