package com.enigma.dif.Main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.enigma.dif.Calendar.CalendarView;
import com.enigma.dif.R;

import java.util.Calendar;

public class ListCalendarFragment extends Fragment {

    private CalendarView cv;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_calendar_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cv = view.findViewById(R.id.calendar_view);
        cv.updateCalendarList(Calendar.getInstance());
    }

    public void updateListFragmentOnDateChange(Calendar calendar) {
        cv.updateCalendarList(calendar);
    }

}
