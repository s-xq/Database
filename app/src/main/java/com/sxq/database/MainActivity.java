package com.sxq.database;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sxq.database.data.source.BookRepository;
import com.sxq.database.data.source.local.BookLocalDataSource;
import com.sxq.database.data.source.remote.BookRemoteDataSource;
import com.sxq.database.mvp.books.BookFragment;
import com.sxq.database.mvp.books.BookPresenter;
import com.sxq.database.mvp.readers.ReaderFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private BookFragment mBookFragment;
    private ReaderFragment mReaderFragment;

    private BookPresenter mBookPresenter;

    private static final String KEY_NAV_ITEM = "CURRENT_NAV_ITEM";
    private int mSelectedNavItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Init the fragments.
        if (savedInstanceState != null) {
            mBookFragment = (BookFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BookFragment");
            mReaderFragment = (ReaderFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ReaderFragment");
            mSelectedNavItem = savedInstanceState.getInt(KEY_NAV_ITEM);
        } else {
            mBookFragment = (BookFragment) getSupportFragmentManager().findFragmentById(R.id.content_main);
            if (mBookFragment == null) {
                mBookFragment = BookFragment.newInstance();
            }

            mReaderFragment = (ReaderFragment) getSupportFragmentManager().findFragmentById(R.id.content_main);
            if (mReaderFragment == null) {
                mReaderFragment = ReaderFragment.newInstance();
            }
        }


        // Add the fragments.
        if (!mBookFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, mBookFragment, "BookFragment")
                    .commit();
        }

        if (!mReaderFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, mReaderFragment, "ReaderFragment")
                    .commit();
        }

        BookRepository.destroyInstance();

        mBookPresenter = new BookPresenter(mBookFragment,
                BookRepository.getInstance(BookLocalDataSource.getInstance(),
                        BookRemoteDataSource.getInstance()
                ));

        //TODO ReaderPresenter


        if (mSelectedNavItem == 0) {
            showBookFragment();
        } else if (mSelectedNavItem == 1) {
            showReaderFragment();
        }


    }

    /**
     * Store the state when the activity may be recycled.
     * @param outState The state data.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Menu menu = mNavigationView.getMenu();
        if (menu.findItem(R.id.nav_book).isChecked()) {
            outState.putInt(KEY_NAV_ITEM, 0);
        } else if (menu.findItem(R.id.nav_reader).isChecked()) {
            outState.putInt(KEY_NAV_ITEM, 1);
        }
        // Store the fragments' states.
        if (mBookFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "BookFragment", mBookFragment);
        }
        if (mReaderFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ReaderFragment", mReaderFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_book) {

            showBookFragment();

        } else if (id == R.id.nav_reader) {

            showReaderFragment();

        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setSelectedBookNumber(long bookNo) {
        if (mBookFragment != null) {
            mBookFragment.setSelectedBookNumber(bookNo);
        }
    }


    /**
     * Init views by calling findViewById.
     */
    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

    }


    private void showBookFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mBookFragment);
        fragmentTransaction.hide(mReaderFragment);
        fragmentTransaction.commit();

        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mNavigationView.setCheckedItem(R.id.nav_book);

    }

    private void showReaderFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mBookFragment);
        fragmentTransaction.show(mReaderFragment);
        fragmentTransaction.commit();

        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mNavigationView.setCheckedItem(R.id.nav_reader);

    }
}
