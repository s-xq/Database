package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SXQ on 2017/6/8.
 */

public class Reader {

    @SerializedName("reader_no")
    private long mReaderNo ;

    @SerializedName("name")
    private String mReadrName ;

    @SerializedName("sex")
    private String mReaderSex ;

    @SerializedName("home_address")
    private String mHomeAddress ;

    @SerializedName("phone_number")
    private String mPhoneNumbaer ;

    @SerializedName("work_cam")
    private String mWorkCam ;

    @SerializedName("create_date")
    private String mCreateDate ;

    @SerializedName("note")
    private String mNote ;

    public long getReaderNo() {
        return mReaderNo;
    }

    public void setReaderNo(long readerNo) {
        mReaderNo = readerNo;
    }

    public String getReadrName() {
        return mReadrName;
    }

    public void setReadrName(String readrName) {
        mReadrName = readrName;
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

    public String getPhoneNumbaer() {
        return mPhoneNumbaer;
    }

    public void setPhoneNumbaer(String phoneNumbaer) {
        mPhoneNumbaer = phoneNumbaer;
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
