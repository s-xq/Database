package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.ReaderField;

/**
 * Created by SXQ on 2017/6/8.
 */

public class Reader {

    @SerializedName(ReaderField.READER_NO)
    private long mReaderNo ;

    @SerializedName(ReaderField.NAME)
    private String mReaderName;

    @SerializedName(ReaderField.SEX)
    private String mReaderSex ;

    @SerializedName(ReaderField.HOME_ADDRESS)
    private String mHomeAddress ;

    @SerializedName(ReaderField.PHONE_NUMBER)
    private String mPhoneNumber;

    @SerializedName(ReaderField.WORK_CAM)
    private String mWorkCam ;

    @SerializedName(ReaderField.CREATE_DATE)
    private String mCreateDate ;

    @SerializedName(ReaderField.NOTE)
    private String mNote ;

    public long getReaderNo() {
        return mReaderNo;
    }

    public void setReaderNo(long readerNo) {
        mReaderNo = readerNo;
    }

    public String getReaderName() {
        return mReaderName;
    }

    public void setReaderName(String readerName) {
        mReaderName = readerName;
    }

    public String getReaderSex() {
        return mReaderSex;
    }

    public void setReaderSex(String readerSex) {
        mReaderSex = readerSex;
    }

    public String getHomeAddress() {
        return mHomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        mHomeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getWorkCam() {
        return mWorkCam;
    }

    public void setWorkCam(String workCam) {
        mWorkCam = workCam;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String createDate) {
        mCreateDate = createDate;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}
