package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.BookTypeField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class BookType {

    @SerializedName(BookTypeField.BOOK_TYPE_NO)
    private String mBookTypeNo;

    @SerializedName(BookTypeField.TYPE_NAME)
    private String mTypeName;

    @SerializedName(BookTypeField.KEY_WORD)
    private String mKeyWord;

    @SerializedName(BookTypeField.NOTE)
    private String mNote;

    public String getBookTypeNo() {
        return mBookTypeNo;
    }

    public void setBookTypeNo(String bookTypeNo) {
        mBookTypeNo = bookTypeNo;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}
