package com.glaserproject.spacexploration.DbTests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTest {

    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {

        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];

    }

    public static <T> List<T> getListValue(LiveData<List<T>> liveData) throws InterruptedException {

        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<List<T>> observer = new Observer<List<T>>() {
            @Override
            public void onChanged(@Nullable List<T> t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (List<T>) data[0];
    }
}
