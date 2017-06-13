package com.sxq.database.mvp.readers;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.ReaderRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SXQ on 2017/6/9.
 */

public class ReaderPresenter implements ReaderContract.Presenter {

    @NonNull
    private final ReaderContract.View mView;

    @NonNull
    private final ReaderRepository mReaderRepository;

    @NonNull
    private final CompositeDisposable mCompositeDisposable;

    public ReaderPresenter(@NonNull ReaderContract.View view, @NonNull ReaderRepository readerRepository) {
        mView = view;
        mReaderRepository = readerRepository;
        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadReaders();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadReaders() {
        mCompositeDisposable.clear();
        Disposable disposable = mReaderRepository
                .getReaders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Reader>>() {
                    @Override
                    public void onNext(List<Reader> value) {
                        mView.showReaders(value);
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
    public void refreshReaders() {
        Disposable disposable = mReaderRepository
                .refreshReaders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Reader>>() {
                    @Override
                    public void onNext(List<Reader> value) {
                        mView.showReaders(value);
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
    public void deleteReader(int position) {
        if (position < 0) {
            return;
        }

        Disposable disposable = mReaderRepository
                .getReaders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Reader>>() {
                    @Override
                    public void onNext(List<Reader> value) {
                        Reader mayRemoveReader = value.get(position);
                        if (mayRemoveReader != null) {
                            //TODO 请求删除

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
}
