package com.sxq.database.data.bean;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.UserField;

/**
 * Created by SXQ on 2017/6/9.
 */

public class User {

    @SerializedName(UserField.USER_NAME)
    private String mUserName  ;

    @SerializedName(UserField.PASS)
    private String mPass ;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }
}
