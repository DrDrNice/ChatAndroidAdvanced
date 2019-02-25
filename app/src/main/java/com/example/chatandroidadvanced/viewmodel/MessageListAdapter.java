package com.example.chatandroidadvanced.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Message;

import java.util.LinkedList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private LayoutInflater inflater;
    private LinkedList<Message> messageList;
    private Context context;

    //todo insert user global variable
    //User user;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessageListAdapter(Context context, LinkedList<Message> messageList){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View itemView = inflater.inflate(R.layout.chat_item_me, parent, false);
            return new MessageViewHolder(itemView, this);
        } else if (viewType == MSG_TYPE_LEFT){
            View itemView = inflater.inflate(R.layout.chat_item_other, parent, false);
            return new MessageViewHolder(itemView, this);
        } else {
            throw new IllegalArgumentException("Unsupported viewType");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MessageViewHolder holder, int position) {
        Message msg = messageList.get(position);
        holder.message.setText(msg.getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //todo get current user from the database and compare if sender equals this user
        //user = database.getcurrentuser.getuserid;
        /*if(messageList.get(position).getSenderId().equals(user.getId())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }*/

        //todo delete
        return MSG_TYPE_RIGHT;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        public final TextView message;

        public MessageViewHolder(@NonNull View itemView, MessageListAdapter adapter) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }
}
