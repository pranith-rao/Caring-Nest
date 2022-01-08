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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomAdapterAdmin extends RecyclerView.Adapter<ViewHolderAdmin> {
    AdminMain adminMain;
    List<Model_admin> modelAdminList;
    Context context;

    public CustomAdapterAdmin(AdminMain adminMain, List<Model_admin> modelAdminList) {
        this.adminMain = adminMain;
        this.modelAdminList = modelAdminList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderAdmin onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_admin, viewGroup, false);

        ViewHolderAdmin viewHolderAdmin = new ViewHolderAdmin(itemView);
        viewHolderAdmin.setOnClickListener(new ViewHolderAdmin.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminMain);
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            String id = modelAdminList.get(position).getId();
                            String name = modelAdminList.get(position).getName();
                            String location = modelAdminList.get(position).getLocation();
                            String phone = modelAdminList.get(position).getPhone();
                            String email = modelAdminList.get(position).getEmail();

                            Intent intent = new Intent(adminMain, AdminAddDetails.class);

                            intent.putExtra("pId", id);
                            intent.putExtra("pName", name);
                            intent.putExtra("pEmail", email);
                            intent.putExtra("pPhone", phone);
                            intent.putExtra("pLocation", location);

                            adminMain.startActivity(intent);
                        }
                        if(which == 1){
                            adminMain.deleteData(position);
                        }
                    }
                }).create().show();;
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String name = modelAdminList.get(position).getName();
                String phone = modelAdminList.get(position).getPhone();
                String email = modelAdminList.get(position).getEmail();
                String location = modelAdminList.get(position).getLocation();

                Toast.makeText(adminMain, name+"\n"+phone+"\n"+location+"\n"+email, Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolderAdmin;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdmin viewHolderAdmin, int i) {
        viewHolderAdmin.mName.setText(modelAdminList.get(i).getName());
        viewHolderAdmin.mPhone.setText(modelAdminList.get(i).getPhone());
        viewHolderAdmin.mEmail.setText(modelAdminList.get(i).getEmail());
        viewHolderAdmin.mLocation.setText(modelAdminList.get(i).getLocation());
    }

    @Override
    public int getItemCount() {
        return modelAdminList.size();
    }
}




