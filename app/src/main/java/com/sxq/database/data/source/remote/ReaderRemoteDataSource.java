package com.sxq.database.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.ReaderDataSource;
import com.sxq.database.retrofit.RetrofitClient;
import com.sxq.database.retrofit.RetrofitService;
import com.sxq.database.util.SqlUtil;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public class ReaderRemoteDataSource implements ReaderDataSource {

    @Nullable
    private static ReaderRemoteDataSource INSTANCE = null;

    public static ReaderRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ReaderRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReaderRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Reader>> getReaders() {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getReaders(SqlUtil.getAllReaders());
    }

    @Override
    public Observable<Reader> getReader(long readerNo) {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getReader(SqlUtil.getReader(readerNo));
    }

    @Override
    public Observable<List<Reader>> refreshReaders() {
        return getReaders();
    }

    @Override
    public Observable<Reader> refreshReader(long readerNo) {
        return getReader(readerNo);
    }

    @Override
    public Observable<List<Reader>> searchReaders(@NonNull String keyword) {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getReaders(SqlUtil.searchReadersByKeyword(keyword));
    }

    @Override
    public Observable<List<Reader>> getReaders(long bookNo) {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getReaders(SqlUtil.getLentReadersByBookNo(bookNo));
    }
}
