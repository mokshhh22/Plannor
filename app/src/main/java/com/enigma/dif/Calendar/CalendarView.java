package com.enigma.dif.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enigma.dif.R;
import com.enigma.dif.Utils.CommonUtils;
import com.enigma.dif.CustomViews.VerticalTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Custom calendar view which can be used as widget in layout files
public class CalendarView extends LinearLayout {

    //for logging
    private static final String LOGTAG = CalendarView.class.getCanonicalName();

    private Context context;
    
    //internal components
    public VerticalTextView titleTV;
    public RecyclerView recyclerView;
    public CalendarAdapter adapter;

    public CalendarView(Context context) {
        super(context);
        this.context = context;
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initControl(context, attrs);
    }

    //load xml layouts
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_calendar_view, this);

        assignUiElements();
    }

    private void assignUiElements() {
        titleTV = findViewById(R.id.titleTV);
        recyclerView = findViewById(R.id.recyclerView);

        //set recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set recycler adapter
        adapter = new CalendarAdapter(getContext(), new ArrayList<>(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    public void updateCalendarList(Calendar calendar) {
        //set title text
        titleTV.setText(CommonUtils.getMonthTitleText(calendar.getTime()));

        getHolidaysApi(calendar);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<Date> datesList = new ArrayList<>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // fill datesList array
        do {
            datesList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (calendar.get(Calendar.DAY_OF_MONTH) != 1);

        adapter.addToList(datesList);
        recyclerView.scrollToPosition(day - 1);
    }

    public void getHolidaysApi(Calendar calendar) {
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;

        ArrayList<HolidayModel> holiday_list = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(String.valueOf(y)).child(String.valueOf(m)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        HolidayModel model = data.getValue(HolidayModel.class);
                        holiday_list.add(model);
                    }
                    adapter.updateHolidayList(holiday_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CalendarView", "loadHoliday:onCancelled", databaseError.toException());
            }
        });

    }
}
