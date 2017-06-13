package com.sxq.database.util;

import com.google.gson.annotations.SerializedName;
import com.sxq.database.util.constants.Table.*;

/**
 * Created by SXQ on 2017/6/8.
 */

public class SqlUtil {
    public static class PostData {

        @SerializedName("user")
        private final String mUserName = "xiaoqiang";

        @SerializedName("pass")
        private final String mPassword = "123456";

        @SerializedName("sqlexe")
        private String mSqlexe;

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

    /**
     * 通过{@link BookField#BOOK_NO}查询图书信息
     *
     * @param bookNo
     * @return
     */
    public static PostData getBookByNo(long bookNo) {
        String format = "select * from %s where %s = %d ";
        String sqlexe = String.format(format, BookField.TABLE_NAME, BookField.BOOK_NO, bookNo);
        Logger.d("根据书号获取书本信息:" + sqlexe);
        return new PostData(sqlexe);
    }

    public static PostData getAllBooks() {
        String format = "select * from %s";
        return new PostData(String.format(format, BookField.TABLE_NAME));
    }




    /**
     * 通过{@link ReaderField#READER_NO}查询该读者借阅的所有书的信息
     * <p>
     * #给出reader_no返回他借的所有的书
     * SELECT * FROM book WHERE book_no IN
     * (
     * SELECT book_no FROM borrow
     * WHERE reader_no = ?
     * )
     *
     * @param readerNo
     * @return
     */
    public static PostData getAllLentBooksByReaderNo(long readerNo) {
        String format = "select * from %s where %s in (select %s  from %s where %s = %d)";
        String sqlexe = String.format(format, BookField.TABLE_NAME, BookField.BOOK_NO, BookField.BOOK_NO, BorrowField.TABLE_NAME, ReaderField.READER_NO, readerNo);
        Logger.d("根据readerNo获取该读者借阅的所有书的信息：" + sqlexe);
        return new PostData(sqlexe);
    }

    public static PostData deleteBook(long bookNo) {
        //// TODO: 2017/6/11
        return null;
    }


    public static PostData searchBooksByKeyword(String keyword) {
        String format = "select * from %s (where %s like '%%s%' or %s like '%%s%' or %s like '%%s%' or %s like '%%s%')";
        String sqlexe = String.format(format, BookField.TABLE_NAME,
                BookField.BOOK_NO, keyword,
                BookField.BOOK_NAME, keyword,
                BookField.BOOK_AUTHOR, keyword,
                BookField.BOOK_PUBLISHER, keyword);
        Logger.d("模糊查询书:" + sqlexe);
        return new PostData(sqlexe);
    }


    public static PostData getAllReaders() {
        String format = "select * from %s";
        return new PostData(String.format(format, ReaderField.TABLE_NAME));
    }

    public static PostData getReader(long readerNo) {
        String format = "select * from %s where %s = %d";
        return new PostData(String.format(format, ReaderField.TABLE_NAME, ReaderField.READER_NO, readerNo));
    }


    public static PostData searchReadersByKeyword(String keyword) {
        String format = "select * from %s (where %s like '%%s%' or %s like '%%s%' or %s like '%%s%' or %s like '%%s%')";
        String sqlexe = String.format(format, ReaderField.TABLE_NAME,
                ReaderField.READER_NO, keyword,
                ReaderField.NAME, keyword,
                ReaderField.HOME_ADDRESS, keyword,
                ReaderField.PHONE_NUMBER, keyword);
        Logger.d("模糊查询读者:" + sqlexe);
        return new PostData(sqlexe);
    }

    /**
     * 通过{@link BookField#BOOK_NO}查询该书所有的借阅者
     * <p>
     * 给出book_no返回所有接这本书的人
     * SELECT * FROM reader WHERE reader_no IN
     * (
     * SELECT reader_no FROM borrow
     * WHERE book_no = ?
     * )
     *
     * @param bookNo
     * @return
     */
    public static PostData getLentReadersByBookNo(long bookNo) {
        String format = "select * from %s where %s in (select %s from %s where %s = %d )";
        String sqlexe = String.format(format, ReaderField.TABLE_NAME, ReaderField.READER_NO, ReaderField.READER_NO, BorrowField.TABLE_NAME, BookField.BOOK_NO, bookNo);
        Logger.d("查询该书所有的借阅者：" + sqlexe);
        return new PostData(sqlexe);
    }



}
