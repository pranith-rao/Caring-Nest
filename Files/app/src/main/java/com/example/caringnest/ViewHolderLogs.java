package com.example.caringnest;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderLogs extends RecyclerView.ViewHolder {
    TextView mpName,mpPhone,mpLocation,maEmail,maName,maPhone,maLocation,dt;
    View mView;

    public ViewHolderLogs(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
        dt = itemView.findViewById(R.id.dt);

        mpName = itemView.findViewById(R.id.pName);
        mpPhone = itemView.findViewById(R.id.pPhone);
        mpLocation = itemView.findViewById(R.id.pLocation);

        maName = itemView.findViewById(R.id.aName);
        maPhone = itemView.findViewById(R.id.aPhone);
        maLocation = itemView.findViewById(R.id.aLocation);
        maEmail = itemView.findViewById(R.id.aEmail);

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
    }

    public static ViewHolderLogs.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int position);
    }
    public static void setOnClickListener(ViewHolderLogs.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
