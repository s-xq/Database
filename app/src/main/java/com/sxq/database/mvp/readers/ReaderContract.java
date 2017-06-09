package com.sxq.database.mvp.readers;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.mvp.BasePresenter;
import com.sxq.database.mvp.BaseView;
import com.sxq.database.mvp.books.BookContract;

import java.util.List;

/**
 * Created by SXQ on 2017/6/9.
 */

public class ReaderContract {

    interface View extends BaseView<ReaderContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showReaders(@NonNull List<Reader> readers);

        void showEmptyView(boolean toShow);

        void showNetWorkError();

        void showReaderRemovedMsg(String readerName);
    }

    interface Presenter extends BasePresenter {

        void loadReaders();

        void refreshReaders();

        void deleteReader(int position);

    }
}
