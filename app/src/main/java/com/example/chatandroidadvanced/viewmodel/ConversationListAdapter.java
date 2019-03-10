package com.example.chatandroidadvanced.viewmodel;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.GlideApp;
import java.util.List;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationViewHolder> {

    private static ClickListener clickListener;
    private LayoutInflater inflater;
    private List<Conversation> mConversation;
    private Context mContext;

    public ConversationListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public Conversation getConversationdAtPosition(int position) {
        return mConversation.get(position);
    }

    @NonNull
    @Override
    public ConversationListAdapter.ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversation_item, parent, false);
        return new ConversationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListAdapter.ConversationViewHolder holder, int position) {


        if (mConversation != null) {
            Conversation current = mConversation.get(position);

            //looking if room participant is empty
            Log.d("Conversation info room", current.getTopic() + " " + current.getId());

            //Alle LAst und email auch
            holder.conversationContent.setText(current.getContent());
            holder.conversationPartner.setText(current.getFirstName() + " " + current.getLastName());

            //todo should image be loaded when user is created??
            String image = "https://robohash.org/" + current.getEmail();
            GlideApp.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.ic_loading_image)
                    .error(R.drawable.ic_loading_error)
                    .into(holder.conversationImage);
        } else {
            // Covers the case of data not being ready yet.
            holder.conversationContent.setText("");
            holder.conversationPartner.setText("Nope");
        }
    }

    @Override
    public int getItemCount() {
        if (mConversation != null)
            return mConversation.size();
        else return 0;
    }


    public void setmParticipants(List<Conversation> conversations) {
        mConversation = conversations;
        notifyDataSetChanged();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationContent;


        private ConversationViewHolder(View itemView) {
            super(itemView);
            conversationImage = itemView.findViewById(R.id.conversationImage);
            conversationPartner = itemView.findViewById(R.id.conversationPartner);
            conversationContent = itemView.findViewById(R.id.conversationMessage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ConversationListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}

