package com.sxq.database.mvp;

import android.view.View;

/**
 * Created by SXQ on 2017/6/8.
 */

public interface BaseView<T> {

    void initViews(View view);

    void setPresenter(T presenter);

}
