package com.sxq.database.data.source.local;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.ReaderDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public class ReaderLocalDataSource implements ReaderDataSource {

    @Nullable
    private static ReaderLocalDataSource INSTANCE = null;

    public static ReaderLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ReaderLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReaderLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Reader>> getReaders() {
        return null;
    }

    @Override
    public Observable<Reader> getReader(long readerNo) {
        return null;
    }

    @Override
    public Observable<List<Reader>> refreshReaders() {
        return null;
    }

    @Override
    public Observable<Reader> refreshReader(long readerNo) {
        return null;
    }

    @Override
    public Observable<List<Reader>> searchReaders(@NonNull String keyword) {
        return null;
    }

    @Override
    public Observable<List<Reader>> getReaders(long bookNo) {
        return null;
    }

    @Override
    public Observable<List<Reader>> refreshReaders(long bookNo) {
        return null;
    }
}
