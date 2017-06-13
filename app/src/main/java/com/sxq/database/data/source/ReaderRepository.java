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
import com.sxq.database.util.constants.Table;

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

public class ReaderRepository implements ReaderDataSource {

    @Nullable
    private static ReaderRepository INSTANCE = null;
    @NonNull
    private ReaderDataSource mReaderLocalDataSource;
    @NonNull
    private ReaderDataSource mReaderRemoteDataSource;
    private Map<Long, Reader> mCachedReaders;

    public ReaderRepository(ReaderLocalDataSource readerLocalDataSource, ReaderRemoteDataSource readerRemoteDataSource) {
        this.mReaderLocalDataSource = readerLocalDataSource;
        this.mReaderRemoteDataSource = readerRemoteDataSource;
    }

    public static ReaderRepository getInstance(ReaderLocalDataSource readerLocalDataSource, ReaderRemoteDataSource readerRemoteDataSource) {
        if (INSTANCE == null) {
            synchronized (ReaderRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReaderRepository(readerLocalDataSource, readerRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    /**
     * 如果内存中有数据则直接读取内存中的数据，否则是从远程拉取
     *
     * @return
     */
    @Override
    public Observable<List<Reader>> getReaders() {
        if (mCachedReaders != null) {
            Logger.d("从内存中加载的读者信息：" + mCachedReaders.toString());
            return Observable.fromCallable(new Callable<List<Reader>>() {
                @Override
                public List<Reader> call() throws Exception {
                    List<Reader> arrayList = new ArrayList<Reader>(mCachedReaders.values());
                    Collections.sort(arrayList, new Comparator<Reader>() {
                        @Override
                        public int compare(Reader o1, Reader o2) {
                            if (o1.getReaderNo() > o2.getReaderNo()) {
                                return 1;
                            } else if (o1.getReaderNo() < o2.getReaderNo()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    return arrayList;
                }
            });
        } else {
            mCachedReaders = new LinkedHashMap<>();
            return mReaderRemoteDataSource
                    .getReaders()
                    .flatMap(new Function<List<Reader>, ObservableSource<List<Reader>>>() {
                        @Override
                        public ObservableSource<List<Reader>> apply(List<Reader> readers) throws Exception {
                            Logger.d("远程拉取的读者信息：" + readers.toString());
                            return Observable
                                    .fromIterable(readers)
                                    .doOnNext(new Consumer<Reader>() {
                                        @Override
                                        public void accept(Reader reader) throws Exception {
                                            mCachedReaders.put(reader.getReaderNo(), reader);
                                        }
                                    })
                                    .toList()
                                    .toObservable();
                        }
                    });
        }
    }

    @Override
    public Observable<Reader> getReader(long readerNo) {
        if (mCachedReaders != null && mCachedReaders.get(readerNo) != null) {
            Reader cachedReader = mCachedReaders.get(readerNo);
            return Observable.just(cachedReader);
        } else {
            return mReaderRemoteDataSource
                    .getReader(readerNo)
                    .doOnNext(new Consumer<Reader>() {
                        @Override
                        public void accept(Reader reader) throws Exception {
                            if (mCachedReaders == null) {
                                mCachedReaders = new LinkedHashMap<Long, Reader>();
                            }
                            /**
                             * 更新内存中该读者的信息
                             */
                            mCachedReaders.put(reader.getReaderNo(), reader);
                        }
                    });
        }
    }

    @Override
    public Observable<List<Reader>> refreshReaders() {
        return mReaderRemoteDataSource
                .refreshReaders()
                .flatMap(new Function<List<Reader>, ObservableSource<List<Reader>>>() {
                    @Override
                    public ObservableSource<List<Reader>> apply(List<Reader> readers) throws Exception {
                        Logger.d("从远程刷新的读者的信息：" + readers.toString());
                        /**
                         * 清除内存中的所有读者信息，重新赋值
                         */
                        mCachedReaders = new LinkedHashMap<Long, Reader>();
                        return Observable
                                .fromIterable(readers)
                                .doOnNext(new Consumer<Reader>() {
                                    @Override
                                    public void accept(Reader reader) throws Exception {
                                        mCachedReaders.put(reader.getReaderNo(), reader);
                                    }
                                })
                                .toList()
                                .toObservable();
                    }
                });
    }

    @Override
    public Observable<Reader> refreshReader(long readerNo) {
        return mReaderRemoteDataSource
                .getReader(readerNo)
                .flatMap(new Function<Reader, ObservableSource<Reader>>() {
                    @Override
                    public ObservableSource<Reader> apply(Reader reader) throws Exception {
                        if (mCachedReaders == null) {
                            mCachedReaders = new LinkedHashMap<Long, Reader>();
                        }
                        mCachedReaders.put(reader.getReaderNo(), reader);
                        return Observable.just(reader);
                    }
                });
    }

    @Override
    public Observable<List<Reader>> searchReaders(@NonNull String keyword) {
        return mReaderRemoteDataSource.searchReaders(keyword);
    }

    @Override
    public Observable<List<Reader>> getReaders(long bookNo) {
        return BookRepository
                .getInstance(BookLocalDataSource.getInstance(), BookRemoteDataSource.getInstance())
                .getBook(bookNo)
                .flatMap(new Function<Book, ObservableSource<List<Reader>>>() {
                    @Override
                    public ObservableSource<List<Reader>> apply(Book book) throws Exception {
                        String isBorrow = book.getIsBorrow();
                        if (isBorrow.equals(Table.BookContent.JUDGE_LENT_YES)) {
                            return mReaderRemoteDataSource
                                    .getReaders(bookNo);
                        } else {
                            return Observable.just(new ArrayList<Reader>());
                        }
                    }
                });
    }

    @Override
    public Observable<List<Reader>> refreshReaders(long bookNo) {
        return BookRepository
                .getInstance(BookLocalDataSource.getInstance(), BookRemoteDataSource.getInstance())
                .refreshBook(bookNo)
                .flatMap(new Function<Book, ObservableSource<List<Reader>>>() {
                    @Override
                    public ObservableSource<List<Reader>> apply(Book book) throws Exception {
                        String isBorrow = book.getIsBorrow();
                        if (isBorrow.equals(Table.BookContent.JUDGE_LENT_YES)) {
                            return mReaderRemoteDataSource
                                    .getReaders(bookNo);
                        } else {
                            return Observable.just(new ArrayList<Reader>());
                        }
                    }
                });
    }
}
