package com.example.caringnest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.security.Key;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class CustomAdapterUser extends RecyclerView.Adapter<ViewHolderUser> {
    UserMain userMain;
    List<Model_user> modelUserList;
    Context context;
    String id;

    public CustomAdapterUser(UserMain userMain, List<Model_user> modelUserList) {
        this.userMain = userMain;
        this.modelUserList = modelUserList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_user, viewGroup, false);

        ViewHolderUser viewHolderUser = new ViewHolderUser(itemView);
        viewHolderUser.setOnClickListener(new ViewHolderUser.ClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                String name = modelUserList.get(position).getName();
                String phone = modelUserList.get(position).getPhone();
                String location = modelUserList.get(position).getLocation();

                String id = modelUserList.get(position).getId();

                userMain.click(id);

            }
        });
        return viewHolderUser;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser viewHolderUser, int i) {
        viewHolderUser.mName.setText(modelUserList.get(i).getName());
        viewHolderUser.mPhone.setText(modelUserList.get(i).getPhone());
        viewHolderUser.mLocation.setText(modelUserList.get(i).getLocation());
    }

    @Override
    public int getItemCount() {
        return modelUserList.size();
    }
}
