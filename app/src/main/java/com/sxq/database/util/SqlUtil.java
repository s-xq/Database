package com.sxq.database.util;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table;

/**
 * Created by SXQ on 2017/6/8.
 */

public class SqlUtil {

    public static PostData getAllBooks(){
        String format = "select * from %s";
        return new PostData(String.format(format , Table.BookField.TABLE_NAME));
    }

    public static PostData getBooksByKeyWord(String keyWord){
        String format = "select * from %s where %s like '%s'";
        return new PostData(String.format(format , Table.BookField.TABLE_NAME ,Table.BookField.BOOK_NAME , keyWord));
    }


    public static PostData getAllReader(){
        return new PostData("select * from reader");
    }



    public static class PostData {

        @SerializedName("user")
        private final String mUserName = "xiaoqiang";

        @SerializedName("pass")
        private final String mPassword = "123456";

        @SerializedName("sqlexe")
        private String mSqlexe ;

        public PostData(String sqlexe) {
            mSqlexe = sqlexe;
        }

        public String getUserName() {
            return mUserName;
        }

        public String getPassword() {
            return mPassword;
        }

        public String getSqlexe() {
            return mSqlexe;
        }

        public void setSqlexe(String sqlexe) {
            mSqlexe = sqlexe;
        }
    }
}
