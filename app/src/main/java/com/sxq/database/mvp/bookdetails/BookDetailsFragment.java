package com.sxq.database.mvp.bookdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sxq.database.R;
import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.data.source.BookRepository;
import com.sxq.database.data.source.ReaderRepository;
import com.sxq.database.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by SXQ on 2017/6/11.
 */

public class BookDetailsFragment extends Fragment implements BookDetailsContract.View , OnRecyclerViewItemClickListener{

    private RecyclerView mRecyclerView;

    private FloatingActionButton mFloatingActionButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    private BookDetailsContract.Presenter mPresenter ;
    private BookDetailsAdapter mBookDetailsAdapter;

    private Toolbar mToolbar;

    public BookDetailsFragment(){
    }

    public static BookDetailsFragment newInstance(){
        return new BookDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_book_details , container , false);

        initViews(contentView);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNameDialog();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshBookDetails();
            }
        });

        setHasOptionsMenu(true);

        /**
         * ERROR 如果一部小心返回的是{@link container}，则会报错！ java.lang.RuntimeException: Unable to start activity ComponentInfo{com.sxq.database/com.sxq.database.mvp.bookdetails.BookDetailsActivity}: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
         */
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BookRepository.destroyInstance();
        ReaderRepository.destroyInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void initViews(View view) {
        BookDetailsActivity activity = (BookDetailsActivity) getActivity();
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle("图书详情");
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        setToolbarBackground(R.drawable.nav_header);
    }

    @Override
    public void setPresenter(BookDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showBooksDetails(@NonNull Book book, @Nullable List<Reader> readers) {
        if (mBookDetailsAdapter == null) {
            mBookDetailsAdapter = new BookDetailsAdapter(getContext(), book , readers);
            mRecyclerView.setAdapter(mBookDetailsAdapter);
            mBookDetailsAdapter.setOnRecyclerViewItemClickListener(this);
        } else {
            mBookDetailsAdapter.updateData(book , readers);
        }
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(mFloatingActionButton, R.string.network_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.go_to_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent().setAction(Settings.ACTION_SETTINGS));
                    }
                })
                .show();
    }

    @Override
    public void setToolbarBackground(@DrawableRes int resId) {
        mCollapsingToolbarLayout.setBackgroundResource(resId);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void OnItemClick(View v, int position) {
        //TODO 添加借阅者
        Toast.makeText(getActivity() , "点击添加借阅者" , Toast.LENGTH_SHORT).show();
    }

    private void showEditNameDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("修改书名");

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_package_name, null);
        final AppCompatEditText editText = (AppCompatEditText) view.findViewById(R.id.editTextName);
        editText.setText(mPresenter.getBookName());
        dialog.setView(view);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                if (input.isEmpty()) {
                    showInputIsEmpty();
                } else {
                    mPresenter.updateBookName(input);
                }
                dialog.dismiss();
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Show the message that the user's input is empty.
     */
    private void showInputIsEmpty() {
        Snackbar.make(mFloatingActionButton, "输入不允许为空", Snackbar.LENGTH_SHORT).show();
    }
}
