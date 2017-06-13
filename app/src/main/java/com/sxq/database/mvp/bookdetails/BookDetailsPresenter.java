package com.sxq.database.mvp.bookdetails;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.BookRepository;
import com.sxq.database.data.source.ReaderRepository;
import com.sxq.database.util.Logger;
import com.sxq.database.util.constants.Table;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SXQ on 2017/6/11.
 */

public class BookDetailsPresenter implements BookDetailsContract.Presenter {

    private long mBookNo;
    private Book mBook;


    @NonNull
    private BookRepository mBookRepository;

    @NonNull
    private ReaderRepository mReaderRepository;

    @NonNull
    private BookDetailsContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    public BookDetailsPresenter(long bookNo, @NonNull BookRepository bookRepository, @NonNull ReaderRepository readerRepository, @NonNull BookDetailsContract.View view) {
        mBookNo = bookNo;
        mBookRepository = bookRepository;
        mReaderRepository = readerRepository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        loadBookDetails();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadBookDetails() {
        Disposable disposable1 = mBookRepository
                .getBook(mBookNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Book>() {
                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                        mView.showBooksDetails(mBook , null);
                        Logger.d("图书详情:" + mBook.toString());
                        Disposable disposable2 = mReaderRepository
                                .getReaders(book.getBookNo())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<List<Reader>>() {
                                    @Override
                                    public void onNext(List<Reader> readers) {
                                        String format = "书本：%s ，isBorrow = %s , 借阅者：%s";
                                        if(readers == null){
                                            Logger.d(String.format(format , mBook.toString() , mBook.getIsBorrow() , "null"));
                                        }else{
                                            Logger.d(String.format(format , mBook.toString() , mBook.getIsBorrow() , readers.toString()));
                                        }
                                        mView.showBooksDetails(book, readers);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mView.setLoadingIndicator(false);
                                        if(mBook.getIsBorrow().equals(Table.BookContent.JUDGE_LENT_YES)){
                                            mView.showNetworkError();
                                            Logger.d("初始加载网络异常,图书信息" + book.toString());
                                        }
                                    }

                                    @Override
                                    public void onComplete() {
                                        mView.setLoadingIndicator(false);
                                    }
                                });
                        mCompositeDisposable.add(disposable2);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingIndicator(false);
                        mView.showNetworkError();
                        Logger.d("初始加载网络异常");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mView.setLoadingIndicator(false);
                    }
                });

        mCompositeDisposable.add(disposable1);

    }

    @Override
    public void refreshBookDetails() {
        Disposable disposable1 = mBookRepository
                .refreshBook(mBookNo)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Book>() {
                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                        mView.showBooksDetails(mBook , null);
                        Logger.d("图书详情:" + mBook.toString());
                        if (book != null && book.getIsBorrow().equals(Table.BookContent.JUDGE_LENT_YES)) {
                            Disposable disposable2 = mReaderRepository
                                    .refreshReaders(book.getBookNo())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableObserver<List<Reader>>() {
                                        @Override
                                        public void onNext(List<Reader> readers) {
                                            String format = "书本：%s ，isBorrow = %s , 借阅者：%s";
                                            if(readers == null){
                                                Logger.d(String.format(format , mBook.toString() , mBook.getIsBorrow() , "null"));
                                            }else{
                                                Logger.d(String.format(format , mBook.toString() , mBook.getIsBorrow() , readers.toString()));
                                            }
                                            mView.showBooksDetails(book, readers);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            mView.setLoadingIndicator(false);
                                            if(mBook.getIsBorrow().equals(Table.BookContent.JUDGE_LENT_YES)){
                                                mView.showNetworkError();
                                                Logger.d("初始加载网络异常, 图书信息" + mBook.toString());
                                            }                                        }

                                        @Override
                                        public void onComplete() {
                                            mView.setLoadingIndicator(false);
                                        }
                                    });
                            mCompositeDisposable.add(disposable2);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingIndicator(false);
                        mView.showNetworkError();
                        Logger.d("刷新网络异常");
                    }

                    @Override
                    public void onComplete() {
                        mView.setLoadingIndicator(false);
                    }
                });

        mCompositeDisposable.add(disposable1);
    }

    @Override
    public String getBookName() {
        if (mBook != null) {
            return mBook.getBookName();
        } else {
            return "";
        }
    }

    @Override
    public void updateBookName(@NonNull String input) {
        //TODO
    }
}
