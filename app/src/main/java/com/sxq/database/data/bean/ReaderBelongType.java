package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.ReaderBelongTypeField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class ReaderBelongType {

    @SerializedName(ReaderBelongTypeField.READER_NO)
    private long mReaderNo ;

    @SerializedName(ReaderBelongTypeField.TYPE_NAME)
    private String mTypeName ;

    public long getReaderNo() {
        return mReaderNo;
    }

    public void setReaderNo(long readerNo) {
        mReaderNo = readerNo;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }
}
