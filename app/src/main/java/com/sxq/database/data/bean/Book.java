package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.BookField;


/**
 * Created by SXQ on 2017/6/8.
 */

public class Book {


    @SerializedName(BookField.BOOK_NO)
    private long mBookNo;

    @SerializedName(BookField.BOOK_NAME)
    private String mBookName ;

    @SerializedName(BookField.BOOK_AUTHOR)
    private String mBookAuthor ;

    @SerializedName(BookField.CREATE_DATE)
    private String mCreateDate ;

    @SerializedName(BookField.BOOK_PUBLISHER)
    private String mBookPublisher ;

    @SerializedName(BookField.KEY_WORD)
    private String mKeyWord ;

    @SerializedName(BookField.IS_BORROW)
    private String mIsBorrow ;

    @SerializedName(BookField.PRICE)
    private float mPrice ;

    @SerializedName(BookField.PAGE)
    private int mPage ;

    public long getBookNo() {
        return mBookNo;
    }

    public void setBookNo(long bookNo) {
        mBookNo = bookNo;
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String bookName) {
        mBookName = bookName;
    }

    public String getBookAuthor() {
        return mBookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        mBookAuthor = bookAuthor;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String createDate) {
        mCreateDate = createDate;
    }

    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    public String getIsBorrow() {
        return mIsBorrow;
    }

    public void setIsBorrow(String isBorrow) {
        mIsBorrow = isBorrow;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public String getBookPublisher() {
        return mBookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        mBookPublisher = bookPublisher;
    }

    @Override
    public String toString(){
        String format = "%d , mBookName = %s ";
        return String.format(format , mBookNo, mBookName+"");
    }
}
