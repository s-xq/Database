package com.sxq.database.mvp.readers;

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
import com.sxq.database.data.bean.Reader;
import com.sxq.database.interfaze.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SXQ on 2017/6/9.
 */

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder> {

    @NonNull
    private final Context mContext;

    @NonNull
    private final LayoutInflater mInflater;

    @NonNull
    private List<Reader> mReaders;

    @NonNull
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public ReaderAdapter(@NonNull Context context, @NonNull List<Reader> readers) {
        this.mContext = context;
        this.mReaders = readers;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReaderViewHolder(mInflater.inflate(R.layout.item_reader, parent, false), mOnRecyclerViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(ReaderViewHolder holder, int position) {
        if (mReaders != null && mReaders.size() != 0) {
            Reader item = mReaders.get(position);
            if (item != null) {
                holder.textViewReaderName.setText(item.getReaderName());
                holder.textViewReaderAddress.setText(item.getHomeAddress());
                holder.textViewReaderPhoneNumber.setText(item.getPhoneNumber());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mReaders != null) {
            return mReaders.size();
        } else {
            return 0;
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnRecyclerViewItemClickListener = listener;
    }

    public void updateData(List<Reader> readers) {
        this.mReaders.clear();
        this.mReaders.addAll(readers);
        notifyDataSetChanged();
    }

    public class ReaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        AppCompatTextView textViewReaderName;
        AppCompatTextView textViewReaderPhoneNumber;
        AppCompatTextView textViewReaderAddress;
        AppCompatTextView textViewAvatar;
        CircleImageView circleImageViewAvatar;
        LinearLayout layoutMain;

        private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

        public ReaderViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);

            textViewReaderName = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderName);
            textViewReaderAddress = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderAddress);
            textViewReaderPhoneNumber = (AppCompatTextView) itemView.findViewById(R.id.textViewReaderPhoneNumber);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            circleImageViewAvatar = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutReaderItemMain);

            itemView.setOnClickListener(this);
            this.mOnRecyclerViewItemClickListener = listener;
            //TODO MENU onCreateContextMenu
        }

        @Override
        public void onClick(View v) {
            if (this.mOnRecyclerViewItemClickListener != null) {
                mOnRecyclerViewItemClickListener.OnItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (menu != null) {
                ((MainActivity) mContext).setSelectedBookNumber(mReaders.get(getLayoutPosition()).getReaderNo());
                Reader reader = mReaders.get(getLayoutPosition());
                menu.setHeaderTitle(reader.getReaderName());
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
