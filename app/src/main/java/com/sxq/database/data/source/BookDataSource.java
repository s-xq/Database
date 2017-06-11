package com.sxq.database.data.source;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Book;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public interface BookDataSource {

    Observable<List<Book>> getBooks();

    Observable<Book> getBook(long bookNo);

    Observable<List<Book>> refreshBooks();

    Observable<Book> refreshBook(long bookNo);

    void deleteBook(@NonNull long bookNo);

    Observable<List<Book>> searchBooks(@NonNull String keyWord);

    /**
     * 根据{@link com.sxq.database.util.constants.Table.ReaderField#READER_NO}返回该读者借阅的所有书
     *
     * @param readerNo
     * @return
     */
    Observable<List<Book>> getBooks(long readerNo);

}

