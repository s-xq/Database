package com.sxq.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sxq.database.data.bean.Book;
import com.sxq.database.retrofit.RetrofitClient;
import com.sxq.database.retrofit.RetrofitService;
import com.sxq.database.util.Logger;
import com.sxq.database.util.SqlUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Subscriber<Book> subscriber = new Subscriber<Book>() {
        @Override
        public void onSubscribe(Subscription s) {
            Logger.d("注册观察");
        }

        @Override
        public void onNext(Book book) {
            Logger.d(book.toString());
        }

        @Override
        public void onError(Throwable t) {
            Logger.e(t.getMessage());
        }

        @Override
        public void onComplete() {
            Logger.d("观察完成");
        }
    };

    private Observer<Book> observer = new Observer<Book>() {
        @Override
        public void onSubscribe(Disposable d) {
            Logger.d("注册观察");
        }

        @Override
        public void onNext(Book value) {
            Logger.d(value.toString());
        }

        @Override
        public void onError(Throwable e) {
            Logger.e(e.getMessage());
        }

        @Override
        public void onComplete() {
            Logger.d("观察完成");
        }
    };

    private Button bt1 ;
    private Button bt2 ;
    private Button bt3 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1: {
                test1();
                break;
            }
            case R.id.bt2: {
                test2();
                break;
            }
            case R.id.bt3: {
                test3();
                break;
            }
        }
    }

    private void test2() {
        RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getBookList(SqlUtil.getAllBooks())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Book>, ObservableSource<Book>>() {
                    @Override
                    public ObservableSource<Book> apply(List<Book> books) throws Exception {
                        return Observable.fromIterable(books);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void test1() {
        RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getBookList(SqlUtil.getAllBooks())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Book>, Observable<List<Book>>>() {
                    @Override
                    public Observable<List<Book>> apply(List<Book> books) throws Exception {
                        return Observable.fromArray(books);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booklist -> {
                    Toast.makeText(MainActivity.this, booklist.toString(), Toast.LENGTH_SHORT).show();
                    Logger.e(booklist.toString());
                });
    }

    private void test3(){

    }
}
