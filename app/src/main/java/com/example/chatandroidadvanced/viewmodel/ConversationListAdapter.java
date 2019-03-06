package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.view.ChatActivity;

import java.util.LinkedList;
import java.util.List;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationViewHolder> {

    private static ClickListener clickListener;
    private LayoutInflater inflater;
    //private LinkedList<Conversation> conversationList;
    private List<Conversation> mConversation;
    private Context mContext;

    public ConversationListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        //  this.conversationList = conversationList;
    }

    public  Conversation getConversationdAtPosition(int position){
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

      /*  //todo get firstname lastname and content
        /*String firstName = conversationList.get(position).getFirstName();
        String lastName = conversationList.get(position).getLastName();
        holder.conversationPartner.setText(firstName + " " + lastName);

        String content = conversationList.get(position).getContent();
        holder.conversationStatus.setText(content);

        String time = conversationList.get(position).getTime();
        holder.conversationTime.setText(time);

        String image = conversationList.get(position).getCreatedBy() + firstName + lastName;*/ /*
        //todo delete only testing
        holder.conversationPartner.setText(conversationList.get(position).getCreatedBy());
        String content = conversationList.get(position).getTopic();
        holder.conversationStatus.setText(content);
        String time = conversationList.get(position).getCreatedDate();
        holder.conversationTime.setText(time);
        String image = conversationList.get(position).getCreatedBy() + conversationList.get(position).getCreatedBy();
        //todo delete till here

        GlideApp.with(context)
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(holder.conversationImage);

*/

        if (mConversation != null) {

            Conversation current = mConversation.get(position);

            //Log.d("preference name", preferences.getString(FIRSTNAME, ""));
            //looking if room participant is empty
            Log.d("Conversation info room", current.getTopic() + " " + current.getId());

            //Alle LAst und email auch
            holder.conversationContent.setText(current.getContent());
            holder.conversationPartner.setText(current.getFirstName() + " " + current.getLastName());
           // holder.conversationPartner.setText("Test");

            //todo should image be loaded when user is created??
           String image = "https://robohash.org/" + current.getEmail() ;
           GlideApp.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.ic_loading_image)
                    .error(R.drawable.ic_loading_error)
                    .into(holder.conversationImage);
        } else {
            // Covers the case of data not being ready yet.
            //  holder.wordItemView.setText("No Word");
            holder.conversationContent.setText("");
           // holder.conversationTime.setText("");
            holder.conversationPartner.setText("NoPartner");
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
      //  public final TextView conversationTime;

        private ConversationViewHolder(View itemView) {
            super(itemView);
            conversationImage = itemView.findViewById(R.id.conversationImage);
            conversationPartner = itemView.findViewById(R.id.conversationPartner);
            conversationContent = itemView.findViewById(R.id.conversationMessage);
          //  conversationTime = itemView.findViewById(R.id.conversationTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

        }
    }
            public void setOnItemClickListener (ClickListener clickListener){
                ConversationListAdapter.clickListener = clickListener;
            }

            public interface ClickListener {
                void onItemClick(View v, int position);
            }

        }

