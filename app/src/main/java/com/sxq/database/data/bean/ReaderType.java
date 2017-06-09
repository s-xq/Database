package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.ReaderTypeField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class ReaderType {

    @SerializedName(ReaderTypeField.TYPE_NAME)
    private String mTypeName ;

    @SerializedName(ReaderTypeField.MAX_BORROW_MONTH)
    private long mMaxBorrowMonth;

    @SerializedName(ReaderTypeField.MAX_BORROW_NUM)
    private long mMaxBorrowNum ;

    @SerializedName(ReaderTypeField.VALID_YEAR)
    private long mValidYear ;

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public long getMaxBorrowMonth() {
        return mMaxBorrowMonth;
    }

    public void setMaxBorrowMonth(long maxBorrowMonth) {
        mMaxBorrowMonth = maxBorrowMonth;
    }

    public long getMaxBorrowNum() {
        return mMaxBorrowNum;
    }

    public void setMaxBorrowNum(long maxBorrowNum) {
        mMaxBorrowNum = maxBorrowNum;
    }

    public long getValidYear() {
        return mValidYear;
    }

    public void setValidYear(long validYear) {
        mValidYear = validYear;
    }
}
