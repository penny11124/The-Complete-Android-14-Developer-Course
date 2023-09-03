package com.techmania.userrecordsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    ArrayList<ItemModel> itemModelArrayList;

    public UserAdapter(Context context, ArrayList<ItemModel> itemModelArrayList) {
        this.context = context;
        this.itemModelArrayList = itemModelArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        ItemModel user = itemModelArrayList.get(position);
        holder.userName.setText(user.getUserName());
        holder.phoneNum.setText(user.getPhoneNum());
        holder.group.setText(user.getGroup());
    }

    @Override
    public int getItemCount() {
        return itemModelArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView phoneNum;
        TextView group;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textViewUserName);
            phoneNum = itemView.findViewById(R.id.textViewPhone);
            group = itemView.findViewById(R.id.textViewGroup);
        }
    }
}
