package com.sxq.database.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.local.BookLocalDataSource;
import com.sxq.database.data.source.local.ReaderLocalDataSource;
import com.sxq.database.data.source.remote.BookRemoteDataSource;
import com.sxq.database.data.source.remote.ReaderRemoteDataSource;
import com.sxq.database.util.Logger;

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

public class BookRepository implements BookDataSource {


    @Nullable
    private static BookRepository INSTATNCE = null;
    @NonNull
    private BookDataSource mBookLocalDataSource;
    @NonNull
    private BookDataSource mBookRemoteDataSource;
    private Map<Long, Book> mCachedBooks;

    public BookRepository(BookLocalDataSource bookLocalDataSource, BookRemoteDataSource bookRemoteDataSource) {
        this.mBookLocalDataSource = bookLocalDataSource;
        this.mBookRemoteDataSource = bookRemoteDataSource;
    }

    public static BookRepository getInstance(BookLocalDataSource bookLocalDataSource, BookRemoteDataSource bookRemoteDataSource) {
        if (INSTATNCE == null) {
            synchronized (BookRepository.class) {
                if (INSTATNCE == null) {
                    INSTATNCE = new BookRepository(bookLocalDataSource, bookRemoteDataSource);
                }
            }
        }
        return INSTATNCE;
    }

    public static void destroyInstance() {
        INSTATNCE = null;
    }

    /**
     * 内存中如果有则返回内存中的数据，否则从远程拉取
     *
     * @return
     */
    @Override
    public Observable<List<Book>> getBooks() {
        if (mCachedBooks != null) {
            Logger.d("从内存加载的图书信息:" + mCachedBooks.toString());
            return Observable.fromCallable(new Callable<List<Book>>() {
                @Override
                public List<Book> call() throws Exception {
                    List<Book> arrayList = new ArrayList<Book>(mCachedBooks.values());
                    Collections.sort(arrayList, new Comparator<Book>() {
                        @Override
                        public int compare(Book o1, Book o2) {
                            if (o1.getBookNo() > o2.getBookNo()) {
                                return -1;
                            } else if (o1.getBookNo() < o2.getBookNo()) {
                                return 1;
                            }
                            return 0;
                        }
                    });
                    return arrayList;
                }
            });
        } else {
            mCachedBooks = new LinkedHashMap<>();
            return mBookRemoteDataSource
                    .getBooks()
                    .flatMap(new Function<List<Book>, ObservableSource<List<Book>>>() {
                        @Override
                        public ObservableSource<List<Book>> apply(List<Book> books) throws Exception {
                            for(Book book : books){
                                mCachedBooks.put(book.getBookNo(), book);
                            }
                            Logger.d("远程拉取的图书信息：" + books.toString());
                            Logger.d("保存的信息:" + mCachedBooks.values().toString());
                            return Observable
                                    .fromIterable(books)
                                    .doOnNext(new Consumer<Book>() {
                                        @Override
                                        public void accept(Book book) throws Exception {
                                            Logger.e("??????" + book.toString());
                                            //TODO 此处不会遍历整个List<Book>
//                                            mCachedBooks.put(book.getBookNo(), book);
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
        if (mCachedBooks != null && mCachedBooks.get(bookNo) != null) {
            Book cachedBook = mCachedBooks.get(bookNo);
            return Observable.just(cachedBook);
        } else {
            return mBookRemoteDataSource
                    .getBook(bookNo)
                    .doOnNext(new Consumer<Book>() {
                        @Override
                        public void accept(Book book) throws Exception {
                            if (mCachedBooks == null) {
                                mCachedBooks = new LinkedHashMap<Long, Book>();
                            }
                            /**
                             * 更新内存中该书的信息
                             */
                            mCachedBooks.put(book.getBookNo(), book);
                            Logger.d("网络加载，" + mCachedBooks.values().toString());
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Logger.d("数据仓库，远程请求，书号:" + bookNo + " , " +  "异常：" + throwable.getMessage());
                            throwable.printStackTrace();
                        }
                    });
        }
    }

    /**
     * 目前刷新只拉取远程数据
     *
     * @return
     */
    @Override
    public Observable<List<Book>> refreshBooks() {
        return mBookRemoteDataSource
                .refreshBooks()
                .flatMap(new Function<List<Book>, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(List<Book> books) throws Exception {
                        Logger.d("从远程刷新的图书信息：" + books.toString());
                        /**
                         * 清除内存中的所有图书信息，重新赋值
                         */
                        mCachedBooks = new LinkedHashMap<Long, Book>();
                        for(Book book : books){
                            mCachedBooks.put(book.getBookNo(), book);
                        }
                        Logger.d("保存的信息:" + mCachedBooks.values().toString());
                        return Observable
                                .fromIterable(books)
                                .doOnNext(new Consumer<Book>() {
                                    @Override
                                    public void accept(Book book) throws Exception {
//                                        mCachedBooks.put(book.getBookNo(), book);
//                                        Logger.d("网络加载，" + mCachedBooks.values().toString());
                                        //TODO 此处不会遍历整个List<Book>
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
                            if (mCachedBooks == null) {
                                mCachedBooks = new LinkedHashMap<Long, Book>();
                            }
                            mCachedBooks.put(bookNo, book);
                            return Observable
                                    .just(book);
                        }
                    });

    }

    @Override
    public void deleteBook(@NonNull long bookNo) {
        mBookLocalDataSource.deleteBook(bookNo);
        mCachedBooks.remove(bookNo);
        mBookRemoteDataSource.deleteBook(bookNo);
    }

    @Override
    public Observable<List<Book>> searchBooks(@NonNull String keyWord) {
        return mBookRemoteDataSource.searchBooks(keyWord);
    }

    @Override
    public Observable<List<Book>> getBooks(long readerNo) {
        return ReaderRepository
                .getInstance(ReaderLocalDataSource.getInstance(), ReaderRemoteDataSource.getInstance())
                .getReader(readerNo)
                .flatMap(new Function<Reader, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(Reader reader) throws Exception {
                        return mBookRemoteDataSource
                                .getBooks(reader.getReaderNo());
                    }
                });
    }

    @Override
    public Observable<List<Book>> refresh(long readerNo) {
        return ReaderRepository
                .getInstance(ReaderLocalDataSource.getInstance(), ReaderRemoteDataSource.getInstance())
                .refreshReader(readerNo)
                .flatMap(new Function<Reader, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(Reader reader) throws Exception {
                        return mBookRemoteDataSource
                                .getBooks(reader.getReaderNo());
                    }
                });
    }

    private Observable<Book> getBookWithNumberFromLocalRepository(long bookNo) {
        return mBookLocalDataSource
                .getBook(bookNo)
                .doOnNext(new Consumer<Book>() {
                    @Override
                    public void accept(Book book) throws Exception {
                        if (mCachedBooks == null) {
                            mCachedBooks = new LinkedHashMap<Long, Book>();
                        }
                        mCachedBooks.put(bookNo, book);
                    }
                });
    }


}
