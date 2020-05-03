package com.enigma.dif.Scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.enigma.dif.Database.Events;
import com.enigma.dif.R;
import com.enigma.dif.Utils.CommonUtils;
import com.enigma.dif.Utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class SchedulerActivity extends AppCompatActivity implements ScheduleContract.View {

    // Ui elements
    private ImageButton backBT, addBt;
    private TextView dayTV, dateTV, deleteTV, doneTV, dateSheetTV, timeSheetTV;
    private ListView scheduleList, todoList;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText taskET, locationET;
    private RadioButton scheduleRB, todoRB;
    private RadioGroup radioGroup;
    private LinearLayout scheduleLL;

    // static keys
    public static String DATE_SELECTED = "date";

    // local variables
    private SchedulePresenter presenter;
    private Date date;
    private Context context;
    private ScheduleAdapter sAdapter;
    private TodoAdapter tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_main);
        context = this;

        //get current date from intent
        long d = getIntent().getLongExtra(DATE_SELECTED, 0);
        date = new Date(d);

        assignUIElements();
        setAllTextViews();
        assignClickListeners();

        //set up schedule list adapter
        sAdapter = new ScheduleAdapter(this, new ArrayList<>());
        scheduleList.setAdapter(sAdapter);

        //set up to-do list adapter
        tAdapter = new TodoAdapter(this, new ArrayList<>());
        todoList.setAdapter(tAdapter);

        // default configurations for activity
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        //set up presenter
        presenter = new SchedulePresenter(this);
        presenter.bindView(this);

        final Observer<List<Events>> listObserver = events -> {
            // filter schedule and to-do from list
            ArrayList<Events> x = new ArrayList<>();
            ArrayList<Events> y = new ArrayList<>();

            for (Events e : events) {
                if (e.getCategory().equals(Constants.SCHEDULE))
                    x.add(e);
                else
                    y.add(e);
            }
            sAdapter.addAll(x);
            tAdapter.addAll(y);

        };
        presenter.getEventsListFromDatabase(date).observe(this, listObserver);

    }

    private void setAllTextViews() {
        //set right time on heading
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,d MMMM");
        String[] s1 = sdf.format(date).split(",");
        dayTV.setText(s1[0]);
        dateTV.setText(s1[1]);

        dateSheetTV.setText(CommonUtils.formatedSheetDate(date));
    }

    private void assignUIElements() {
        backBT = findViewById(R.id.backBT);
        addBt = findViewById(R.id.addBT);
        dayTV = findViewById(R.id.dayTV);
        dateTV = findViewById(R.id.dateTV);
        scheduleList = findViewById(R.id.schedule_list);
        todoList = findViewById(R.id.todo_list);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        taskET = findViewById(R.id.taskET);
        deleteTV = findViewById(R.id.deleteTV);
        doneTV = findViewById(R.id.doneTV);
        scheduleRB = findViewById(R.id.scheduleRB);
        todoRB = findViewById(R.id.todoRB);
        radioGroup = findViewById(R.id.radioGroup);
        dateSheetTV = findViewById(R.id.dateSheetTV);
        timeSheetTV = findViewById(R.id.timeSheetTV);
        scheduleLL = findViewById(R.id.scheduleLL);
        locationET = findViewById(R.id.locationET);
    }

    public void assignClickListeners() {
        backBT.setOnClickListener(v -> SchedulerActivity.super.onBackPressed());

        scheduleRB.setOnClickListener(v -> {
            scheduleLL.setVisibility(View.VISIBLE);
            if (!scheduleRB.isChecked()) {
                scheduleRB.setChecked(true);
                todoRB.setChecked(false);
            }
        });

        todoRB.setOnClickListener(v -> {
            scheduleLL.setVisibility(View.GONE);
            if (!todoRB.isChecked()) {
                todoRB.setChecked(true);
                scheduleRB.setChecked(false);
            }
        });

        timeSheetTV.setOnClickListener(v -> {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    String am_pm;
                    if(hour > 12) {
                        am_pm = "PM";
                        hour = hour - 12;
                    }
                    else
                        am_pm = "AM";
                    if (minute > 9)
                        timeSheetTV.setText(hour+":"+minute+" "+am_pm);
                    else
                        timeSheetTV.setText(hour+":0"+minute+" "+am_pm);
                }
            }, mHour, mMinute, false);

            dialog.show();
        });

        addBt.setOnClickListener(v -> {
            //default configurations
            clearBottomSheet();
            scheduleRB.setChecked(true);
            todoRB.setChecked(false);
            scheduleLL.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            deleteTV.setVisibility(View.GONE);
            doneTV.setText("Done");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            doneTV.setOnClickListener(view -> {
                if (!taskET.getText().toString().trim().equals("")) {
                    Events event = new Events();
                    event.setMessage(taskET.getText().toString().trim());
                    event.setDate(CommonUtils.getStringDateForDb(date));

                    if (scheduleRB.isChecked()) {
                        event.setCategory(Constants.SCHEDULE);
                        event.setTime(timeSheetTV.getText().toString().trim());
                        if (!locationET.getText().toString().trim().equals(""))
                            event.setLocation(locationET.getText().toString().trim());
                        else
                            event.setLocation("location");
                    } else
                        event.setCategory(Constants.TODO);

                    //add to database
                    presenter.addEventToDb(event);

                    //lower bottom sheet
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    clearBottomSheet();

                } else {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            });
        });

        scheduleList.setOnItemClickListener((parent, view, position, id) -> {
            // default config
            clearBottomSheet();

            scheduleLL.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.GONE);
            deleteTV.setVisibility(View.VISIBLE);
            deleteTV.setText("delete");
            doneTV.setText("Update");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            Events event = (Events) parent.getItemAtPosition(position);
            taskET.setText(event.getMessage());
            locationET.setText(event.getLocation());
            timeSheetTV.setText(event.getTime());

            deleteTV.setOnClickListener(v -> {
                presenter.deleteEventFromDb(event);
                clearBottomSheet();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });

            doneTV.setOnClickListener(v2 -> {
                event.setMessage(taskET.getText().toString());
                event.setLocation(locationET.getText().toString());
                event.setTime(timeSheetTV.getText().toString());

                presenter.updateEvent(event);
                clearBottomSheet();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
        });

        todoList.setOnItemClickListener((parent, view, position, id) -> {
            // default config
            clearBottomSheet();

            scheduleLL.setVisibility(View.GONE);
            deleteTV.setVisibility(View.VISIBLE);
            deleteTV.setText("delete");
            doneTV.setText("Update");
            radioGroup.setVisibility(View.GONE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            Events event = (Events) parent.getItemAtPosition(position);
            taskET.setText(event.getMessage());

            deleteTV.setOnClickListener(v -> {
                presenter.deleteEventFromDb(event);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                clearBottomSheet();
            });

            doneTV.setOnClickListener(v2 -> {
                event.setMessage(taskET.getText().toString());
                presenter.updateEvent(event);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                clearBottomSheet();
            });
        });
    }

    public void clearBottomSheet() {
        //clear Text views in bottom sheet
        taskET.setText("");
        locationET.setText("");
        timeSheetTV.setText("11:00 AM");
    }
}
