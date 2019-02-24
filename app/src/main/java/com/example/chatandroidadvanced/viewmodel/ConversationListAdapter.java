package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.view.ChatActivity;

import java.util.LinkedList;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationViewHolder> {

    private LayoutInflater inflater;
    private final LinkedList<Conversation> conversationList;
    private Context mContext;

    public ConversationListAdapter(Context context, LinkedList<Conversation> conversationList) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        this.conversationList = conversationList;
    }

    @NonNull
    @Override
    public ConversationListAdapter.ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversation_item, parent, false);
        return new ConversationListAdapter.ConversationViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListAdapter.ConversationViewHolder holder, int position) {

        String firstName = conversationList.get(position).getFirstName();
        String lastName = conversationList.get(position).getLastName();
        holder.conversationPartner.setText(firstName + " " + lastName);

        String content = conversationList.get(position).getContent();
        holder.conversationStatus.setText(content);

        String time = conversationList.get(position).getTime();
        holder.conversationTime.setText(time);


        String image = conversationList.get(position).getImageUrl() + firstName + lastName;

        GlideApp.with(mContext)
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(holder.conversationImage);


    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationStatus;
        public final TextView conversationTime;

        final ConversationListAdapter adapter;

        public ConversationViewHolder(@NonNull View itemView, ConversationListAdapter adapter) {
            super(itemView);
            conversationImage = itemView.findViewById(R.id.conversationImage);
            conversationPartner = itemView.findViewById(R.id.conversationPartner);
            conversationStatus = itemView.findViewById(R.id.conversationMessage);
            conversationTime = itemView.findViewById(R.id.conversationTime);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //String element = contactList.get(position).getFirstName();
            //contactList.get(position).setFirstName("Clicked");
            //contactList.set(position, "Clicked" + element);
            //Todo if clicked on contact it should be added to the database to see it in conversations
            Context context = v.getContext();
            Intent intentChat = new Intent(context, ChatActivity.class);
            intentChat.putExtra("contact", conversationList.get(position));
            context.startActivity(intentChat);
            adapter.notifyDataSetChanged();
            ((Activity) context).finish();
        }
    }
}
