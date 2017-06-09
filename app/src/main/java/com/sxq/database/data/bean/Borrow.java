package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.BorrowField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class Borrow {

    @SerializedName(BorrowField.BORROW_NO)
    private long mBorrowNo ;

    @SerializedName(BorrowField.BORROW_DATE)
    private String mBorrowDate;

    @SerializedName(BorrowField.RETURN_DATE)
    private String mReturnDate;

    @SerializedName(BorrowField.NOTE_INFO)
    private String mNoteInfo;

    @SerializedName(BorrowField.BOOK_NO)
    private long mBookNo ;

    @SerializedName(BorrowField.READER_NO)
    private long mReaderNo;

    public long getBorrowNo() {
        return mBorrowNo;
    }

    public void setBorrowNo(long borrowNo) {
        mBorrowNo = borrowNo;
    }

    public String getBorrowDate() {
        return mBorrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        mBorrowDate = borrowDate;
    }

    public String getReturnDate() {
        return mReturnDate;
    }

    public void setReturnDate(String returnDate) {
        mReturnDate = returnDate;
    }

    public String getNoteInfo() {
        return mNoteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        mNoteInfo = noteInfo;
    }

    public long getBookNo() {
        return mBookNo;
    }

    public void setBookNo(long bookNo) {
        mBookNo = bookNo;
    }

    public long getReaderNo() {
        return mReaderNo;
    }

    public void setReaderNo(long readerNo) {
        mReaderNo = readerNo;
    }
}
