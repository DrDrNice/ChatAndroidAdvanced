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
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.view.ChatActivity;

import java.util.LinkedList;
import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.ParticipantViewHolder> {

    private final LayoutInflater mInflater;
    private List<Participant> mParticipants; // Cached copy of words
    private static ClickListener clickListener;

    public ParticipantListAdapter(Context context) { mInflater = LayoutInflater.from(context); }


    public  Participant getWordAtPosition(int position){
        return mParticipants.get(position);
    }

    @Override
    public ParticipantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.conversation_item, parent, false);
        return new ParticipantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipantViewHolder holder, int position) {
        if (mParticipants != null) {
            Participant current = mParticipants.get(position);

            //Alle LAst und email auch
            holder.conversationfirstName.setText(current.getfirstName());
            holder.conversationlastName.setText(current.getlastName());
            holder.conversationEmail.setText(current.getmEmail());
        } else {
            // Covers the case of data not being ready yet.
          //  holder.wordItemView.setText("No Word");
            holder.conversationfirstName.setText("NoWord");
            holder.conversationlastName.setText("Nope");
            holder.conversationEmail.setText("NopeEmail");
        }
    }

    public void setmParticipants(List<Participant> participants){
        mParticipants = participants;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mParticipants != null)
            return mParticipants.size();
        else return 0;
    }

    class ParticipantViewHolder extends RecyclerView.ViewHolder {

        public final TextView conversationEmail;
        public final TextView conversationfirstName;
        public final TextView conversationlastName;

        private ParticipantViewHolder(View itemView) {
            super(itemView);

            conversationEmail= itemView.findViewById(R.id.conversationPartner);
            conversationfirstName = itemView.findViewById(R.id.conversationMessage);
            conversationlastName = itemView.findViewById(R.id.conversationTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        ParticipantListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
