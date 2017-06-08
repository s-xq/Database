package com.sxq.database.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.source.local.BookLocalDataSource;
import com.sxq.database.data.source.remote.BookRemoteDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookRepository implements BookDataSource{


    @Nullable  private static BookRepository INSTATNCE = null;
    @NonNull private BookDataSource mBookLocalDataSource ;
    @NonNull private BookDataSource mBookRemoteDataSource ;
    private Map<Long , Book> mCachedBooks;

    public BookRepository(BookLocalDataSource bookLocalDataSource , BookRemoteDataSource bookRemoteDataSource){
        this.mBookLocalDataSource = bookLocalDataSource;
        this.mBookRemoteDataSource = bookRemoteDataSource;
    }

    public static BookRepository getInstance(BookLocalDataSource bookLocalDataSource , BookRemoteDataSource bookRemoteDataSource){
        if(INSTATNCE == null){
            synchronized (BookRepository.class){
                if(INSTATNCE == null){
                    INSTATNCE = new BookRepository(bookLocalDataSource , bookRemoteDataSource);
                }
            }
        }
        return INSTATNCE;
    }

    public static void destroyInstance(){
        INSTATNCE = null;
    }

    @Override
    public Observable<List<Book>> getBooks() {
        if(mCachedBooks != null){
            return Observable.fromCallable(new Callable<List<Book>>(){
                @Override
                public List<Book> call() throws Exception {
                    List<Book> arrayList = new ArrayList<Book>(mCachedBooks.values());
                    Collections.sort(arrayList, new Comparator<Book>() {
                        @Override
                        public int compare(Book o1, Book o2) {
                            if(o1.getBookNo()>o2.getBookNo()){
                                return -1 ;
                            }else if(o1.getBookNo()<o2.getBookNo()){
                                return 1;
                            }
                            return 0;
                        }
                    });
                    return arrayList;
                }
            });
        }else{
            mCachedBooks = new LinkedHashMap<>();
            return mBookLocalDataSource.getBooks()
                    .flatMap(new Function<List<Book>, ObservableSource<List<Book>>>() {
                        @Override
                        public ObservableSource<List<Book>> apply(List<Book> books) throws Exception {
                            return Observable
                                    .fromIterable(books)
                                    .doOnNext(new Consumer<Book>() {
                                        @Override
                                        public void accept(Book book) throws Exception {
                                            mCachedBooks.put(book.getBookNo() , book);
                                        }
                                    })
                                    .toList()
                                    .toObservable();
                        }
                    });
        }
    }

    @Override
    public Observable<Book> getBook(long bookNo) {
        Book cachedBook = mCachedBooks.get(bookNo);
        if(cachedBook != null){
            return Observable.just(cachedBook);
        }
        return getBookWithNumberFromLocalRepository(bookNo);
    }

    @Override
    public Observable<List<Book>> refreshBooks() {
        return mBookRemoteDataSource
                .refreshBooks()
                .flatMap(new Function<List<Book>, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(List<Book> books) throws Exception {
                        return Observable
                                .fromIterable(books)
                                .doOnNext(new Consumer<Book>() {
                                    @Override
                                    public void accept(Book book) throws Exception {
                                        Book cachedBook = mCachedBooks.get(book.getBookNo());
                                        if(cachedBook != null){
                                            cachedBook.setBookAuthor(book.getBookAuthor());
                                            cachedBook.setBookName(book.getBookName());
                                            cachedBook.setBookPublisher(book.getBookPublisher());
                                            cachedBook.setCreateDate(book.getCreateDate());
                                            cachedBook.setKeyWord(book.getKeyWord());
                                            cachedBook.setPage(book.getPage());
                                            cachedBook.setPrice(book.getPrice());
                                            cachedBook.setIsBorrow(book.getIsBorrow());
                                        }
                                    }
                                })
                                .toList()
                                .toObservable();
                    }
                });
    }

    @Override
    public Observable<Book> refreshBook(long bookNo) {
        return mBookRemoteDataSource
                .refreshBook(bookNo)
                .flatMap(new Function<Book, ObservableSource<Book>>() {
                    @Override
                    public ObservableSource<Book> apply(Book book) throws Exception {
                        return Observable
                                .just(book)
                                .doOnNext(new Consumer<Book>() {
                                    @Override
                                    public void accept(Book book) throws Exception {
                                        Book cachedBook = mCachedBooks.get(book.getBookNo());
                                        if(cachedBook!= null){
                                            cachedBook.setBookAuthor(book.getBookAuthor());
                                            cachedBook.setBookName(book.getBookName());
                                            cachedBook.setBookPublisher(book.getBookPublisher());
                                            cachedBook.setCreateDate(book.getCreateDate());
                                            cachedBook.setKeyWord(book.getKeyWord());
                                            cachedBook.setPage(book.getPage());
                                            cachedBook.setPrice(book.getPrice());
                                            cachedBook.setIsBorrow(book.getIsBorrow());
                                        }
                                    }
                                });
                    }
                });
    }

    private Observable<Book> getBookWithNumberFromLocalRepository(long bookNo){
        return mBookLocalDataSource
                .getBook(bookNo)
                .doOnNext(new Consumer<Book>() {
                    @Override
                    public void accept(Book book) throws Exception {
                        if(mCachedBooks == null){
                            mCachedBooks = new LinkedHashMap<Long, Book>();
                        }
                        mCachedBooks.put(bookNo , book);
                    }
                });
    }

}