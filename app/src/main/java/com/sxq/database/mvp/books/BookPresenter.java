package com.sxq.database.mvp.books;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.source.BookRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.sxq.database.util.constants.Table.BookContent.JUDGE_LENT_YES;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookPresenter implements BookContract.Presenter {

    @NonNull
    private final BookContract.View mView;

    @NonNull
    private final BookRepository mBookRepository;

    @NonNull
    private final CompositeDisposable mCompositeDisposable;


    /**
     * 当前选择展示的书
     */
    private BookFilterType mCurrentBookFilterType = BookFilterType.ALL_BOOKS;


    public BookPresenter(@NonNull BookContract.View view, @NonNull BookRepository bookRepository) {
        mView = view;
        mBookRepository = bookRepository;
        mCompositeDisposable = new CompositeDisposable();
        this.mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadBooks();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadBooks() {
        mCompositeDisposable.clear();
        Disposable disposable = mBookRepository
                .getBooks()
                .flatMap(new Function<List<Book>, ObservableSource<Book>>() {
                    @Override
                    public ObservableSource<Book> apply(List<Book> books) throws Exception {
                        return Observable.fromIterable(books);
                    }
                })
                .filter(new Predicate<Book>() {
                    @Override
                    public boolean test(Book book) throws Exception {
                        String isBorrow = book.getIsBorrow();
                        switch (mCurrentBookFilterType) {
                            case LENT_BOOKS: {
                                if (isBorrow.equals(JUDGE_LENT_YES)) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                            case ALL_BOOKS: {
                                return true;
                            }
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Book>>() {
                    @Override
                    public void onNext(List<Book> value) {
                        mView.showBooks(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showEmptyView(true);
                        mView.setLoadingIndicator(false);
                        mView.showNetWorkError();
                    }

                    @Override
                    public void onComplete() {
                        mView.setLoadingIndicator(false);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void refreshBooks() {
        Disposable disposable = mBookRepository
                .refreshBooks()
                .flatMap(new Function<List<Book>, ObservableSource<Book>>() {
                    @Override
                    public ObservableSource<Book> apply(List<Book> books) throws Exception {
                        return Observable.fromIterable(books);
                    }
                })
                .filter(new Predicate<Book>() {
                    @Override
                    public boolean test(Book book) throws Exception {

                        //ERROR 过滤有问题

                        String isBorrow = book.getIsBorrow();
                        switch (mCurrentBookFilterType) {
                            case LENT_BOOKS: {
                                if (isBorrow.equals(JUDGE_LENT_YES)) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                            case ALL_BOOKS: {
                                return true;
                            }
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Book>>() {
                    @Override
                    public void onNext(List<Book> value) {
                        mView.showBooks(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingIndicator(false);
                        mView.showNetWorkError();
                    }

                    @Override
                    public void onComplete() {
                        mView.setLoadingIndicator(false);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteBook(int position) {
        if (position < 0) {
            return;
        }
        Disposable disposable = mBookRepository
                .getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Book>>() {
                    @Override
                    public void onNext(List<Book> value) {
                        Book mayRemovedBook = value.get(position);
                        if (mayRemovedBook != null) {
                            mBookRepository.deleteBook(mayRemovedBook.getBookNo());
                            value.remove(position);
                            mView.showBooks(value);
                            mView.showPackageRemovedMsg(mayRemovedBook.getBookName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setCurrentBookFilterType(BookFilterType bookFilterType) {
        this.mCurrentBookFilterType = bookFilterType;
    }
}
