package com.sxq.database.mvp.bookdetails;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.mvp.BasePresenter;
import com.sxq.database.mvp.BaseView;

import java.util.List;

/**
 * Created by SXQ on 2017/6/11.
 */

public interface BookDetailsContract {

    interface View extends BaseView<Presenter> {

        /**
         * @param book
         * @param readers 该书的借阅者
         */
        void showBooksDetails(@NonNull Book book, @Nullable List<Reader> readers);

        void showNetworkError();

        void setToolbarBackground(@DrawableRes int resId);

        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {

        void loadBookDetails();

        void refreshBookDetails();

        String getBookName();

        void updateBookName(@NonNull String input);


    }


}
