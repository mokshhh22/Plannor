package com.enigma.dif.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM Events WHERE date LIKE :date")
    LiveData<List<Events>> getEventByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Events event);

    @Update
    void updateEvent(Events event);

    @Delete
    void deleteEvent(Events event);
}
