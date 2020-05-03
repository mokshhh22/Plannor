package com.enigma.dif.Utils;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enigma.dif.Database.AppDatabase;
import com.enigma.dif.Database.Events;
import com.enigma.dif.Scheduler.SchedulePresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleUtils {

    private Context context;
    private SchedulePresenter schedulePresenter;
    private AppDatabase appDatabase;


    public ScheduleUtils(Context context, SchedulePresenter presenter) {
        this.context = context;
        this.schedulePresenter = presenter;
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public LiveData<List<Events>> getScheduleListFromDB(Date date) {
        return appDatabase.eventsDao().getEventByDate(CommonUtils.getStringDateForDb(date));
    }

    public void addEvent(Events event) {
        appDatabase.eventsDao().insertEvent(event);
    }

    public void deleteEventFromDb(Events event) {
        appDatabase.eventsDao().deleteEvent(event);
    }

    public void updateEvent(Events event) {
        appDatabase.eventsDao().updateEvent(event);
    }
}
