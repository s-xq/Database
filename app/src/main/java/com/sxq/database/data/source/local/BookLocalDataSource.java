package com.sxq.database.data.source.local;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.source.BookDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookLocalDataSource implements BookDataSource{
    @Override
    public Observable<List<Book>> getBooks() {
        return null;
    }

    @Override
    public Observable<Book> getBook(long bookNo) {
        return null;
    }

    @Override
    public Observable<List<Book>> refreshBooks() {
        return null;
    }

    @Override
    public Observable<Book> refreshBook(long bookNo) {
        return null;
    }
}
