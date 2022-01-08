package com.example.caringnest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderUser extends RecyclerView.ViewHolder {
    TextView mName,mPhone,mLocation;
    View mView;

    public ViewHolderUser(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        mName = itemView.findViewById(R.id.rName);
        mPhone = itemView.findViewById(R.id.rPhone);
        mLocation = itemView.findViewById(R.id.rLocation);
    }

    public ViewHolderUser.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnClickListener(ViewHolderUser.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
