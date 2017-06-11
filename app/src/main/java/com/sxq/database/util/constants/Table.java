package com.sxq.database.util.constants;


/**
 * Created by SXQ on 2017/6/8.
 * <p>
 * 数据库表字段名
 *
 * 命名规则：表名+Field
 *
 * 每个表对应的字段类下面都有一个TABLE_NAME
 */
public class Table {

    public static class BookField {
        public static final String TABLE_NAME = "book";
        public static final String BOOK_NO = "book_no";
        public static final String BOOK_NAME = "book_name";
        public static final String BOOK_AUTHOR = "book_author";
        public static final String CREATE_DATE = "create_date";
        public static final String BOOK_PUBLISHER = "book_publisher";
        public static final String KEY_WORD = "key_word";
        public static final String IS_BORROW = "is_borrow";
        public static final String PRICE = "price";
        public static final String PAGE = "page";
    }

    public static class BookContent {
        // TODO 如果数据库修改了相关判断依据，此处注意修改
        public static final String JUDGE_LENT_YES = "yes";
        public static final String JUDGE_LENT_NO = "no";
    }


    public static final class ReaderField {
        public static final String TABLE_NAME = "reader";
        public static final String READER_NO = "reader_no";
        public static final String NAME = "name";
        public static final String SEX = "sex";
        public static final String HOME_ADDRESS = "home_address";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String WORK_CAM = "work_cam";
        public static final String CREATE_DATE = "create_date";
        public static final String NOTE = "note";
    }

    public static final class BookTabField {
        public static final String TABLE_NAME = "book_tab";
        public static final String BOOK_NO = "book_no";
        public static final String BOOK_TYPE_NO = "book_type_no";
        public static final String NUM = "num";
        public static final String TOTAL_NUM = "total_num";
    }

    public static final class BookTypeField {
        public static final String TABLE_NAME = "book_type";
        public static final String BOOK_TYPE_NO = "book_type_no";
        public static final String TYPE_NAME = "type_name";
        public static final String KEY_WORD = "key_word";
        public static final String NOTE = "note";
    }


    public static final class BorrowField {
        public static final String TABLE_NAME = "borrow";
        public static final String BORROW_NO = "borrow_no";
        public static final String BORROW_DATE = "borrow_date";
        public static final String RETURN_DATE = "return_date";
        public static final String NOTE_INFO = "note_info";
        public static final String BOOK_NO = "book_no";
        public static final String READER_NO = "reader_no";
    }

    public static final class ReaderBelongTypeField {
        public static final String TABLE_NAME = "reader_belong_type";
        public static final String READER_NO = "reader_no";
        public static final String TYPE_NAME = "type_name";
    }

    public static final class ReaderTypeField {
        public static final String TABLE_NAME = "reader_type";
        public static final String TYPE_NAME = "type_name";
        public static final String MAX_BORROW_MONTH = "max_borrow_month";
        public static final String MAX_BORROW_NUM = "max_borrow_num";
        public static final String VALID_YEAR = "valid_year";
    }

    public static final class UserField {
        public static final String TABLE_NAME = "user";
        public static final String USER_NAME = "user_name";
        public static final String PASS = "pass";
    }
}
