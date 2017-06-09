package com.sxq.database.mvp.bookdetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sxq.database.R;

public class BookDetailsActivity extends AppCompatActivity {

    public static final String  BOOK_NO = "BOOK_NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
    }
}
