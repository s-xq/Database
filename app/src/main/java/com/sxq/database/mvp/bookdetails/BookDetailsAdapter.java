package com.sxq.database.mvp.bookdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxq.database.R;
import com.sxq.database.data.bean.Book;
import com.sxq.database.data.bean.Reader;
import com.sxq.database.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by SXQ on 2017/6/11.
 */

public class BookDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private Context mContext;

    @NonNull
    private LayoutInflater mInflater;

    @NonNull
    private Book mBook;

    @Nullable
    private List<Reader> mLentReaders;

    private static final int TYPE_HEADER = 0x01;
    private static final int TYPE_READERS = 0x02;
    private static final int TYPE_ADD = 0x03;

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    /**
     * @param context
     * @param book
     * @param lentReaders 该书的借阅者
     */
    public BookDetailsAdapter(@NonNull Context context, @NonNull Book book, @Nullable List<Reader> lentReaders) {
        this.mContext = context;
        this.mBook = book;
        this.mLentReaders = lentReaders;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if(mLentReaders != null && position > 0 && position < getItemCount() - 1){
            return TYPE_READERS;
        }else{
            return TYPE_ADD;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new BookDetailsViewHolder(mInflater.inflate(R.layout.item_book_details_header, parent, false));
        } else if(viewType == TYPE_READERS){
            return new LendReaderViewHolder(mInflater.inflate(R.layout.item_reader, parent, false));
        }else{
            return new AddViewHolder(mInflater.inflate(R.layout.item_add , parent , false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            if (holder instanceof BookDetailsViewHolder) {
                ((BookDetailsViewHolder) holder).tvNo.setText(mBook.getBookNo()+"");
                ((BookDetailsViewHolder) holder).tvName.setText(mBook.getBookName());
                ((BookDetailsViewHolder) holder).tvAuthor.setText(mBook.getBookAuthor());
                ((BookDetailsViewHolder) holder).tvCreateDate.setText(mBook.getCreateDate());
                ((BookDetailsViewHolder) holder).tvPublisher.setText(mBook.getBookPublisher());
                ((BookDetailsViewHolder) holder).tvKeyword.setText(mBook.getKeyWord());
                ((BookDetailsViewHolder) holder).tvIsBorrow.setText(mBook.getIsBorrow());
                ((BookDetailsViewHolder) holder).tvPrice.setText(mBook.getPrice() + "");
                ((BookDetailsViewHolder) holder).tvPage.setText(mBook.getPage() + "");
            }
        } else if(position > 0 && position < getItemCount() - 1){
            if (holder instanceof LendReaderViewHolder) {
                if (mLentReaders != null && mLentReaders.size() != 0) {
                    Reader item = mLentReaders.get(position - 1);
                    if (item != null) {
                        ((LendReaderViewHolder) holder).textViewReaderName.setText(item.getReaderName());
                        ((LendReaderViewHolder) holder).textViewReaderAddress.setText(item.getHomeAddress());
                        ((LendReaderViewHolder) holder).textViewReaderPhoneNumber.setText(item.getPhoneNumber());
                    }
                }
            }
        }else{
            if(holder instanceof  AddViewHolder){
                //
            }
        }


    }

    @Override
    public int getItemCount() {
        if (mLentReaders == null) {
            return 2;
        } else {
            return mLentReaders.size() + 2;
        }
    }

    public void updateData(Book book, List<Reader> lentReaders) {
        this.mBook = book;
        this.mLentReaders = lentReaders;
        notifyDataSetChanged();
    }

    /**
     * 设置底部增加按钮的点击监听
     * @param listener
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnRecyclerViewItemClickListener = listener;
    }


    /**
     * 顶层展示的书籍信息
     */
    public class BookDetailsViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNo;
        AppCompatTextView tvName;
        AppCompatTextView tvAuthor;
        AppCompatTextView tvCreateDate;
        AppCompatTextView tvPublisher;
        AppCompatTextView tvKeyword;
        AppCompatTextView tvIsBorrow;
        AppCompatTextView tvPrice;
        AppCompatTextView tvPage;

        public BookDetailsViewHolder(View itemView) {
            super(itemView);

            tvNo = (AppCompatTextView) itemView.findViewById(R.id.textViewBookNumber);
            tvName = (AppCompatTextView) itemView.findViewById(R.id.textViewName);
            tvAuthor = (AppCompatTextView) itemView.findViewById(R.id.textViewBookAuthor);
            tvCreateDate = (AppCompatTextView) itemView.findViewById(R.id.textViewBookCreateDate);
            tvPublisher = (AppCompatTextView) itemView.findViewById(R.id.textViewBookPublisher);
            tvKeyword = (AppCompatTextView) itemView.findViewById(R.id.textViewKeyword);
            tvIsBorrow = (AppCompatTextView) itemView.findViewById(R.id.textViewBookIsBorrow);
            tvPrice = (AppCompatTextView) itemView.findViewById(R.id.textViewBookPrice);
            tvPage = (AppCompatTextView) itemView.findViewById(R.id.textViewBookPages);


        }
    }


    /**
     * 该书的借阅者
     */
    public class LendReaderViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewReaderName;
        AppCompatTextView textViewReaderPhoneNumber;
        AppCompatTextView textViewReaderAddress;
        AppCompatTextView textViewAvatar;
        CircleImageView circleImageViewAvatar;
        LinearLayout layoutMain;

        public LendReaderViewHolder(View itemView) {
            super(itemView);

            textViewReaderName = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderName);
            textViewReaderAddress = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderAddress);
            textViewReaderPhoneNumber = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderPhoneNumber);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            circleImageViewAvatar = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutReaderItemMain);

        }
    }

    /**
     * 底部添加按钮
     */
    public class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView mCircleImageView ;

        public AddViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = (CircleImageView)itemView.findViewById(R.id.circleImageViewAdd);
        }

        @Override
        public void onClick(View v) {
            if(mOnRecyclerViewItemClickListener != null){
                mOnRecyclerViewItemClickListener.OnItemClick(v , getLayoutPosition());
            }
        }
    }

}
