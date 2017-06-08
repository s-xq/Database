package com.sxq.database.data.source;

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

}
