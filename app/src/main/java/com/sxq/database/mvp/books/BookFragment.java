package com.sxq.database.mvp.books;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxq.database.R;
import com.sxq.database.data.bean.Book;
import com.sxq.database.interfaze.OnRecyclerViewItemClickListener;
import com.sxq.database.mvp.addbook.AddBookActivity;
import com.sxq.database.mvp.bookdetails.BookDetailsActivity;
import com.sxq.database.util.Logger;

import java.util.List;

/**
 * Created by SXQ on 2017/6/8.
 */

public class BookFragment extends Fragment implements BookContract.View {

    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private BookContract.Presenter mPresenter;
    private BookAdapter mBookAdapter;
    private long mSelectedBookNo = 0;

    public BookFragment() {
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_book, container, false);
        initViews(contentView);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBookActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all: {
                        mPresenter.setCurrentBookFilterType(BookFilterType.ALL_BOOKS);
                    }
                    case R.id.nav_lent: {
                        mPresenter.setCurrentBookFilterType(BookFilterType.LENT_BOOKS);
                    }
                }
                mPresenter.loadBooks();

                return true;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshBooks();
            }
        });

        // Set true to inflate the options menu.
//        setHasOptionsMenu(true);

        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 恢复状态
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO 保存状态
    }

    @Override
    public void initViews(View view) {
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mBottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        mEmptyView = (LinearLayout) view.findViewById(R.id.emptyView);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void setPresenter(BookContract.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void showBooks(@NonNull List<Book> books) {
        if (books != null) {
            Logger.d("展示书的信息：" + books.toString());
        } else {
            Logger.d("展示书的信息，books is null");
        }
        if (mBookAdapter == null) {
            mBookAdapter = new BookAdapter(getContext(), books);
            mBookAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void OnItemClick(View v, int position) {
                    Logger.d("book item click , position = " + position);
                    Intent intent = new Intent(getContext(), BookDetailsActivity.class);
                    intent.putExtra(BookDetailsActivity.BOOK_NO, books.get(position).getBookNo());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    } else {
                        startActivity(intent);
                    }
                }

            });
            mRecyclerView.setAdapter(mBookAdapter);
        } else {
            mBookAdapter.updateData(books);
        }
        showEmptyView(books == null || books.isEmpty());
    }

    @Override
    public void showEmptyView(boolean toShow) {
        if (toShow) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showNetWorkError() {
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
    public void showPackageRemovedMsg(String bookName) {
        String msg = bookName
                + " "
                + getString(R.string.package_removed_msg);
        Snackbar.make(mFloatingActionButton, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mPresenter.recoverPackage();
                    }
                })
                .show();
    }

    public void setSelectedBookNumber(long bookNo) {
        this.mSelectedBookNo = bookNo;
    }
}
