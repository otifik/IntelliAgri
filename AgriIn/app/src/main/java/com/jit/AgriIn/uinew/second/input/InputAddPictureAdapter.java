package com.jit.AgriIn.uinew.second.input;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jit.AgriIn.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class InputAddPictureAdapter extends RecyclerView.Adapter<InputAddPictureAdapter.ViewHolder> {

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public interface OnItemClickListener {
        void onItemClick(View var1, int var2);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mItemClickListener = l;
    }

    private final LayoutInflater mInflater;
    private final onAddPicClickListener mOnAddPicClickListener;
    private List<LocalMedia> list = new ArrayList<>();
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    public InputAddPictureAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_input_pic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size();
        return position == size;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    public List<LocalMedia> getData() {
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.ic_add_image);
            viewHolder.mImg.setOnClickListener(v -> mOnAddPicClickListener.onAddPicClick());
            viewHolder.mIvDel.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.mIvDel.setVisibility(View.VISIBLE);
            viewHolder.mIvDel.setOnClickListener(view -> {
                int index = viewHolder.getAdapterPosition();
                if (index != RecyclerView.NO_POSITION && list.size() > index) {
                    list.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, list.size());
                }
            });
            LocalMedia media = list.get(position);
            String path;
            path = media.getPath();
            Glide.with(viewHolder.itemView.getContext())
                    .load(!TextUtils.isEmpty(path) && path.startsWith("content://") && !media.isCut() && !media.isCompressed() ? Uri.parse(path)
                            : path)
                    .into(viewHolder.mImg);

            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(v -> {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(v, adapterPosition);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < 3) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImg;
        ImageView mIvDel;
        public ViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.fiv);
            mIvDel = itemView.findViewById(R.id.iv_del);
        }
    }
}
