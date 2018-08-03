package com.glaserproject.spacexploration.DbTests;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.glaserproject.spacexploration.Room.SpacexDatabase;

import org.junit.After;
import org.junit.Before;

public class DbTest {

    protected SpacexDatabase database;

    @Before
    public void initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SpacexDatabase.class).build();
    }


    @After
    public void closeDb() {
        database.close();
    }
}
