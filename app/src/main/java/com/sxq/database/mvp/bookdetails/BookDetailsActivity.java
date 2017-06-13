package com.sxq.database.mvp.bookdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sxq.database.R;
import com.sxq.database.data.source.BookRepository;
import com.sxq.database.data.source.ReaderRepository;
import com.sxq.database.data.source.local.BookLocalDataSource;
import com.sxq.database.data.source.local.ReaderLocalDataSource;
import com.sxq.database.data.source.remote.BookRemoteDataSource;
import com.sxq.database.data.source.remote.ReaderRemoteDataSource;
import com.sxq.database.util.Logger;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String BOOK_NO = "BOOK_NO";

    private BookDetailsFragment mBookDetailsFragment;
    private static final String BOOK_DETAILS_FRAGMENT = BookDetailsFragment.class.getSimpleName();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);


        // Restore the status.
        if (savedInstanceState != null) {
            mBookDetailsFragment = (BookDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, BOOK_DETAILS_FRAGMENT);
        } else {
            mBookDetailsFragment = BookDetailsFragment.newInstance();
        }

        Explode explode = new Explode();
        explode.setDuration(500);
        explode.setInterpolator(new AccelerateDecelerateInterpolator());
        getWindow().setEnterTransition(explode);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mBookDetailsFragment)
                .commit();
        // Create the presenter.
        new BookDetailsPresenter(
                getIntent().getLongExtra(BOOK_NO, -1),
                BookRepository.getInstance(BookLocalDataSource.getInstance(), BookRemoteDataSource.getInstance()),
                ReaderRepository.getInstance(ReaderLocalDataSource.getInstance(), ReaderRemoteDataSource.getInstance()),
                mBookDetailsFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, BOOK_DETAILS_FRAGMENT, mBookDetailsFragment);
    }


}
