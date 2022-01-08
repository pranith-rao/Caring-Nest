package com.example.caringnest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAdmin extends RecyclerView.ViewHolder {
    TextView mName,mPhone,mLocation,mEmail;
    View mView;

    public ViewHolderAdmin(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        mName = itemView.findViewById(R.id.rName);
        mPhone = itemView.findViewById(R.id.rPhone);
        mLocation = itemView.findViewById(R.id.rLocation);
        mEmail = itemView.findViewById(R.id.rEmail);
    }

    public ViewHolderAdmin.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolderAdmin.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
