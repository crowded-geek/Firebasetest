package com.example.yash.firebasetest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yash.firebasetest.MessageActivity;
import com.example.yash.firebasetest.Model.Chat;
import com.example.yash.firebasetest.Model.User;
import com.example.yash.firebasetest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT=0;
    private static final int MSF_TEXT_RIGHT=1;
    private Context mcontext;
    private List<Chat> mChat;
    private String imageurl;
    FirebaseUser fuser;

    public MessageAdapter(Context mcontext,List<Chat> mChat,String imageurl)
    {
        this.mChat=mChat;
        this.imageurl=imageurl;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i==MSF_TEXT_RIGHT)
        {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.chat_item_right, viewGroup,false);
        return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view=LayoutInflater.from(mcontext).inflate(R.layout.chat_item_left, viewGroup,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Chat chat=mChat.get(i);
        viewHolder.show_message.setText(chat.getMessage());
        if(imageurl.equals("default"))
        {
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(mcontext).load(imageurl).into(viewHolder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser=FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid()))
        {
            return MSF_TEXT_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
