package com.enigma.dif.Scheduler;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enigma.dif.Database.Events;
import com.enigma.dif.Utils.Constants;
import com.enigma.dif.Utils.ScheduleUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchedulePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View mView;
    private Context context;
    private ScheduleUtils utils;
    private MutableLiveData<Events> eventsLiveData;

    SchedulePresenter(Context context) {
        this.context = context;
        utils = new ScheduleUtils(context, this);
    }

    @Override
    public LiveData<List<Events>> getEventsListFromDatabase(Date date) {
        return utils.getScheduleListFromDB(date);
    }

    @Override
    public void deleteEventFromDb(Events event) {
        utils.deleteEventFromDb(event);
    }

    @Override
    public void updateEvent(Events event) {
        utils.updateEvent(event);
    }

    @Override
    public void addEventToDb(Events event) {
        utils.addEvent(event);
    }

    @Override
    public void bindView(ScheduleContract.View view) {
        if (mView == null)
            mView = view;
    }

    @Override
    public void unBindView() {
        mView = null;
    }
}
