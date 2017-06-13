package com.sxq.database.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.source.BookDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookLocalDataSource implements BookDataSource {

    @Nullable
    private static BookLocalDataSource INSTANCE = null;

    public static BookLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (BookLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }


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

    @Override
    public void deleteBook(@NonNull long bookNo) {

    }

    @Override
    public Observable<List<Book>> searchBooks(@NonNull String keyWord) {
        return null;
    }

    @Override
    public Observable<List<Book>> getBooks(long readerNo) {
        return null;
    }

    @Override
    public Observable<List<Book>> refresh(long readerNo) {
        return null;
    }
}
