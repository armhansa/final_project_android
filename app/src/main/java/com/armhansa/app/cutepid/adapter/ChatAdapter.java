package com.armhansa.app.cutepid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armhansa.app.cutepid.R;
import com.armhansa.app.cutepid.controller.BitmapConverter;
import com.armhansa.app.cutepid.model.User;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<Holder> {

    private List<User> users;

    private Context context;

    public ChatAdapter() {

    }

    public ChatAdapter(Context context) {
        this.context = context;
    }

    public interface ChatListener {
        void onClickInItem(int position);
    }

    private ChatListener listener;

    public void setListener(ChatListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_chat, parent, false);
        Holder holder = new Holder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        ImageView profileImage = holder.profileImage;
        TextView firstName = holder.firstName;
        TextView status = holder.status;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickInItem(position);
            }
        });


        Glide.with(context)
                .load(users.get(position).getProfile())
                .centerCrop()
                .into(profileImage);
        firstName.setText(String.format("%s, %d", users.get(position).getFirstName(), users.get(position).getAge()));
        status.setText(users.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public byte[] getByteArray(String data) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArrayOutputStream);

        try {
            out.write(data.getBytes());
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();

    }
}

class Holder extends RecyclerView.ViewHolder {
    public ImageView profileImage;
    public TextView firstName;
    public TextView status;

    public Holder(View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profileImage);
        firstName = itemView.findViewById(R.id.firstName);
        status = itemView.findViewById(R.id.status);

    }
}
