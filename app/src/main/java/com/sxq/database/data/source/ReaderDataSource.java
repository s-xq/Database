package com.sxq.database.data.source;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Reader;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public interface ReaderDataSource {

    Observable<List<Reader>> getReaders();

    Observable<Reader> getReader(long readerNo);

    Observable<List<Reader>> refreshReaders();

    Observable<Reader> refreshReader(long readerNo);

    Observable<List<Reader>> searchReaders(@NonNull String keyword);

    /**
     * 根据{@link com.sxq.database.util.constants.Table.BookField#BOOK_NO}返回该书所有的借阅者
     *
     * @param bookNo
     * @return
     */
    Observable<List<Reader>> getReaders(long bookNo);


}
