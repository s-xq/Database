package com.sxq.database.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.source.BookDataSource;
import com.sxq.database.retrofit.RetrofitClient;
import com.sxq.database.retrofit.RetrofitService;
import com.sxq.database.util.Logger;
import com.sxq.database.util.SqlUtil;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookRemoteDataSource implements BookDataSource{

    @Nullable private static BookRemoteDataSource INSTANCE = null;

    public static BookRemoteDataSource getInstance(){
        if(INSTANCE == null){
            synchronized (BookRemoteDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new BookRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    @Override
    public Observable<List<Book>> getBooks() {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getBookList(SqlUtil.getAllBooks());
    }

    @Override
    public Observable<Book> getBook(long bookNo) {
        //TODO
        return null;

    }

    @Override
    public Observable<List<Book>> refreshBooks() {
        Logger.d("刷新全部书信息");
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getBookList(SqlUtil.getAllBooks());
    }

    @Override
    public Observable<Book> refreshBook(long bookNo) {
        //TODO
        return null;
    }

    @Override
    public void deleteBook(@NonNull long bookNo) {
        //TODO
    }

    @Override
    public Observable<List<Book>> searchBooks(@NonNull String keyWord) {
        return RetrofitClient
                .getInstance()
                .create(RetrofitService.class)
                .getBookList(SqlUtil.getBooksByKeyWord(keyWord));
    }
}
