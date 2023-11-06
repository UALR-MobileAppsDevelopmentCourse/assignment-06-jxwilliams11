package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ualr.recyclerviewassignment.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class Adapter extends RecyclerView.Adapter {
    private List<Inbox> mItems;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public Adapter(Context context, List<Inbox> items){
        this.mItems = items;
        this.mContext = context;
    }

    public interface OnItemClickListener{
        void OnItemClick(View view, Inbox obj, int position);

        void OnThumbnailClick(View view, Inbox obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mOnItemClickListener = mItemClickListener;
    }

    public void addItem(int position, Inbox item){
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void deleteItem(int position){
        if(position >= mItems.size()){
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void clear() {
        for (Inbox x : mItems) {
            x.setSelected(false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        vh = new InboxViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InboxViewHolder viewHolder = (InboxViewHolder)holder;
        Inbox i = mItems.get(position);
        viewHolder.emailIcon.setText(i.getFrom().substring(0,1));
        viewHolder.emailDate.setText(i.getDate());
        viewHolder.emailBody.setText(i.getMessage());
        viewHolder.emailSender.setText(i.getFrom());
        viewHolder.emailAddress.setText(i.getEmail());

        if(i.isSelected()){
            viewHolder.deleteImage.setVisibility(View.VISIBLE);
            viewHolder.emailIcon.setText("");
            viewHolder.inbox.setBackgroundResource(R.color.colorAccentDark);
        }
        else{
            viewHolder.emailIcon.setText(i.getFrom().substring(0,1));
            viewHolder.deleteImage.setVisibility(View.INVISIBLE);
            TypedValue outValue = new TypedValue();
            mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            viewHolder.inbox.setBackgroundResource(outValue.resourceId);

        }
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder{
        public TextView emailIcon;
        public TextView emailDate;
        public TextView emailBody;
        public TextView emailSender;
        public TextView emailAddress;
        public ImageView deleteImage;
        public View inbox;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);
            emailIcon = itemView.findViewById(R.id.thumbnail);
            emailDate = itemView.findViewById(R.id.date);
            emailBody = itemView.findViewById(R.id.body);
            emailSender = itemView.findViewById(R.id.name);
            emailAddress = itemView.findViewById(R.id.address);
            inbox = itemView.findViewById(R.id.lyt_parent);
            deleteImage = (ImageView)itemView.findViewById(R.id.deleteImg);

            emailIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mOnItemClickListener.OnThumbnailClick(view, mItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });

            inbox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mOnItemClickListener.OnItemClick(view, mItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }
}
