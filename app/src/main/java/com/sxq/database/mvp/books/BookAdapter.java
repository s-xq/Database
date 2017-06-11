package com.sxq.database.mvp.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sxq.database.MainActivity;
import com.sxq.database.R;
import com.sxq.database.data.bean.Book;
import com.sxq.database.interfaze.OnRecyclerViewItemClickListener;
import com.sxq.database.util.Logger;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SXQ on 2017/6/9.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    @NonNull
    private final Context mContext;

    @NonNull
    private final LayoutInflater mInflater;

    @NonNull
    private List<Book> mBooks;

    @NonNull
    private OnRecyclerViewItemClickListener mItemClickListener;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mBooks = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookViewHolder(mInflater.inflate(R.layout.item_book, parent, false), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (mBooks != null && mBooks.size() != 0) {
            Book item = mBooks.get(position);
            if (item != null) {
                holder.textViewBookName.setText(item.getBookName());
                holder.textViewBookPublisher.setText(item.getBookPublisher());
                holder.textViewBookAuthor.setText(item.getBookAuthor());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mBooks != null) {
            return mBooks.size();
        } else {
            return 0;
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * Update the data. Keep the data is the latest.
     *
     * @param books The data.
     */
    public void updateData(List<Book> books) {
        this.mBooks.clear();
        this.mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        AppCompatTextView textViewBookName;
        AppCompatTextView textViewBookPublisher;
        AppCompatTextView textViewBookAuthor;
        AppCompatTextView textViewAvatar;
        CircleImageView circleImageViewAvatar;
        LinearLayout layoutMain;

        private OnRecyclerViewItemClickListener mItemClickListener;

        public BookViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);

            textViewBookName = (AppCompatTextView) itemView.findViewById(R.id.textViewBookName);
            textViewBookAuthor = (AppCompatTextView) itemView.findViewById(R.id.textViewBookAuthor);
            textViewBookPublisher = (AppCompatTextView) itemView.findViewById(R.id.textViewBookPublisher);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            circleImageViewAvatar = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutBookItemMain);

            this.mItemClickListener = listener;
            itemView.setOnClickListener(this);
            //TODO MENU onCreateContextMenu
        }

        @Override
        public void onClick(View v) {
            Logger.d("click");
            if (this.mItemClickListener != null) {
                mItemClickListener.OnItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (menu != null) {
                ((MainActivity) mContext).setSelectedBookNumber(mBooks.get(getLayoutPosition()).getBookNo());
                Book book = mBooks.get(getLayoutPosition());
                menu.setHeaderTitle(book.getBookName());
                // Set different title according to the data({@link Package#readable})
                /**
                 * TODO 修改跟删除书籍
                 */
/*                menu.add(Menu.NONE, R.id.action_copy_code, 0, R.string.copy_code);
                menu.add(Menu.NONE, R.id.action_share, 0, R.string.share);*/
            }
        }
    }
}
