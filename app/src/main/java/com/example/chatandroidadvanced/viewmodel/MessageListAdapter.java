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

    private SharedPreferences mPreferences;


    public MessageListAdapter(Context context, SharedPreferences preferences) {
        mPreferences = preferences;
        mInflater = LayoutInflater.from(context);
    }


    public Message getMessageAtPosition(int position) {
        return mMessages.get(position);
    }

    @Override
    public MessageListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(getLayout(viewType), parent, false);
        return new MessageListAdapter.MessageViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MessageListAdapter.MessageViewHolder holder, int position) {
        if (mMessages != null) {
            Message current = mMessages.get(position);
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
        notifyDataSetChanged();
    }

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
