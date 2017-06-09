package com.sxq.database.mvp.readers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.database.data.bean.Reader;

import java.util.List;


/**
 * Created by SXQ on 2017/6/8.
 */

public class ReaderFragment extends Fragment implements ReaderContract.View{
    public ReaderFragment() {
    }

    public static ReaderFragment newInstance() {
        return new ReaderFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(ReaderContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showReaders(@NonNull List<Reader> readers) {

    }

    @Override
    public void showEmptyView(boolean toShow) {

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void showReaderRemovedMsg(String readerName) {

    }
}
