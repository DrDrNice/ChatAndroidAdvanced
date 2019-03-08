package com.example.chatandroidadvanced.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.view.MainActivity;

import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.chatandroidadvanced.view.MainActivity.MY_PREFERENCES;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    public static final int MSG_TYPE_OTHER = 0;
    public static final int MSG_TYPE_ME = 1;
    private final LayoutInflater mInflater;
    private List<Message> mMessages;
   // private static ClickListener clickListener;
    private Context mContext;
    private SharedPreferences mPreferences;


    public MessageListAdapter(Context context, SharedPreferences preferences) {
        mContext = context;
        mPreferences = preferences;
        mInflater = LayoutInflater.from(context);
    }


    public Message getMessageAtPosition(int position) {
        return mMessages.get(position);
    }

    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView =mInflater.inflate(getLayout(viewType), parent, false);
        return new MessageListAdapter.MessageViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MessageListAdapter.MessageViewHolder holder, int position) {
        if (mMessages != null) {
            Message current = mMessages.get(position);

            //Log.d("preference name", preferences.getString(FIRSTNAME, ""));
            //looking if room participant is empty
            Log.d("Adapter call messageinfo room", current.getContent() + " " + current.getSenderId() + " "+ current.getReceiverId());

            //Alle LAst und email auch
            holder.messageTextView.setText(current.getContent());
        } else {
            holder.messageTextView.setText("DummyData");

        }
    }

    @Override
    public int getItemViewType(int position) {

        String mSenderId = mPreferences.getString(MainActivity.ID, "");

        if (mMessages.get(position).getSenderId().equals(mSenderId)) {
            return MSG_TYPE_ME;
        } else {
            return MSG_TYPE_OTHER;
        }
    }

    private int getLayout(final int type) {
        if (type == MSG_TYPE_ME) {
            return R.layout.chat_item_me;
        } else {
            return R.layout.chat_item_other;
        }
    }
    public void setmMessages(List<Message> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        mMessages.add(message);
       // notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMessages != null)
            return mMessages.size();
        else return 0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        public final TextView messageTextView;

        private MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message);
        }
    }
}






  /*  private LayoutInflater inflater;
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
        }*/    /*

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
}*/
