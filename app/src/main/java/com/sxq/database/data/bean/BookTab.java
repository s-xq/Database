package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.BookTabField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class BookTab {
    @SerializedName(BookTabField.BOOK_NO)
    private long mBookNo ;

    @SerializedName(BookTabField.BOOK_TYPE_NO)
    private String mBookTypeNo;

    @SerializedName(BookTabField.NUM)
    private long mNum ;

    @SerializedName(BookTabField.TOTAL_NUM)
    private long mTotalNum ;

    public long getBookNo() {
        return mBookNo;
    }

    public void setBookNo(long bookNo) {
        mBookNo = bookNo;
    }

    public String getBookTypeNo() {
        return mBookTypeNo;
    }

    public void setBookTypeNo(String bookTypeNo) {
        mBookTypeNo = bookTypeNo;
    }

    public long getNum() {
        return mNum;
    }

    public void setNum(long num) {
        mNum = num;
    }

    public long getTotalNum() {
        return mTotalNum;
    }

    public void setTotalNum(long totalNum) {
        mTotalNum = totalNum;
    }
}
