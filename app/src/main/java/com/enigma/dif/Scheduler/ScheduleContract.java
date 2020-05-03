package com.enigma.dif.Scheduler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.enigma.dif.BasePresenter;
import com.enigma.dif.BaseView;
import com.enigma.dif.Database.Events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ScheduleContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {
        void addEventToDb(Events events);

        LiveData<List<Events>> getEventsListFromDatabase(Date date);

        void deleteEventFromDb(Events event);

        void updateEvent(Events event);
    }
}
