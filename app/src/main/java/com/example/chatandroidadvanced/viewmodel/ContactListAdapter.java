package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private LayoutInflater inflater;
    private final LinkedList<Conversation> contactList;
    private Context mContext;

    public ContactListAdapter(Context context, LinkedList<Conversation> contactList){
        inflater = LayoutInflater.from(context);
        mContext=context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversation_item, parent, false);
        return new ContactViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {

        String firstName = contactList.get(position).getFirstName();
        String lastName = contactList.get(position).getLastName();
        holder.conversationPartner.setText(firstName + " " + lastName);

        String status = contactList.get(position).getStatus();
        holder.conversationStatus.setText(status);

        holder.conversationTime.setText("");

        String image = contactList.get(position).getImageUrl() + firstName + lastName;

        GlideApp.with(mContext)
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(holder.conversationImage);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationStatus;
        public final TextView conversationTime;

        final ContactListAdapter adapter;

        public ContactViewHolder(@NonNull View itemView, ContactListAdapter adapter) {
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
            intentChat.putExtra("contact", contactList.get(position));
            context.startActivity(intentChat);
            adapter.notifyDataSetChanged();
            ((Activity)context).finish();
        }
    }
}
