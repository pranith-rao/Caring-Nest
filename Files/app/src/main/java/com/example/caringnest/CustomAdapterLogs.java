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

public class CustomAdapterLogs extends RecyclerView.Adapter<ViewHolderLogs> {
    AdminLogs adminLogs;
    List<Model_logs> modelLogs;
    Context context;

    public CustomAdapterLogs(AdminLogs adminLogs, List<Model_logs> modelLogs) {
        this.adminLogs = adminLogs;
        this.modelLogs = modelLogs;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderLogs onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_logs, viewGroup, false);

        ViewHolderLogs viewHolderLogs = new ViewHolderLogs(itemView);
        ViewHolderLogs.setOnClickListener(new ViewHolderLogs.ClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminLogs);
                String[] options = { "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            adminLogs.deleteData(position);
                        }
                    }
                }).create().show();;
            }

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(adminLogs, "Long press to delete", Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolderLogs;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLogs ViewHolderLogs, int i) {

        ViewHolderLogs.mpName.setText(modelLogs.get(i).getP_name());
        ViewHolderLogs.mpPhone.setText(modelLogs.get(i).getP_phone());
        ViewHolderLogs.mpLocation.setText(modelLogs.get(i).getP_location());

        ViewHolderLogs.maName.setText(modelLogs.get(i).getA_name());
        ViewHolderLogs.maPhone.setText(modelLogs.get(i).getA_phone());
        ViewHolderLogs.maEmail.setText(modelLogs.get(i).getA_email());
        ViewHolderLogs.maLocation.setText(modelLogs.get(i).getA_location());

        ViewHolderLogs.dt.setText(modelLogs.get(i).getTime());
    }

    @Override
    public int getItemCount() {
        return modelLogs.size();
    }
}




