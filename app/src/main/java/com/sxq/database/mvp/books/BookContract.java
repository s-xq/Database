package com.sxq.database.mvp.books;

import android.support.annotation.NonNull;

import com.sxq.database.data.bean.Book;
import com.sxq.database.mvp.BasePresenter;
import com.sxq.database.mvp.BaseView;

import java.util.List;

/**
 * Created by SXQ on 2017/6/8.
 */

public interface BookContract {


    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void showBooks(@NonNull List<Book> books);

        void showEmptyView(boolean toShow);

        void showNetWorkError();

        void showPackageRemovedMsg(String bookName);
    }

    interface Presenter extends BasePresenter{

        void loadBooks();

        void refreshBooks();

        void deleteBook(int position);

        void setCurrentBookFilterType(BookFilterType bookFilterType);

    }

}
