package com.example.chatandroidadvanced.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.model.Participant;

import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.ParticipantViewHolder> {

    private final LayoutInflater mInflater;
    private List<Participant> mParticipants;
    private static ClickListener clickListener;
    private Context mContext;

    public ParticipantListAdapter(Context context) {
        mContext =context;
        mInflater = LayoutInflater.from(context); }


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
        if (mParticipants != null ) {
            //&& Integer.valueOf(mParticipants.get(position).getIDServer())!= 170

            Participant current = mParticipants.get(position);

            //Alle LAst und email auch
            holder.conversationEmail.setText(current.getmEmail());
            holder.conversationTime.setText("");
            holder.conversationPartner.setText(current.getfirstName() + " " + current.getlastName());

            String image = "https://robohash.org/" + current.getmEmail();
            //todo delete till here

            GlideApp.with(mContext)
                    .load(image)
                    .placeholder(R.drawable.ic_loading_image)
                    .error(R.drawable.ic_loading_error)
                    .into(holder.conversationImage);


        } else {
            // Covers the case of data not being ready yet.
          //  holder.wordItemView.setText("No Word");
            holder.conversationEmail.setText("NoEmail");
            holder.conversationTime.setText("");
            holder.conversationPartner.setText("NoPartner");
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

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationEmail;
        public final TextView conversationTime;

        private ParticipantViewHolder(View itemView) {
            super(itemView);

            conversationImage = itemView.findViewById(R.id.conversationImage);
            conversationPartner = itemView.findViewById(R.id.conversationPartner);
            conversationEmail = itemView.findViewById(R.id.conversationMessage);
            conversationTime = itemView.findViewById(R.id.conversationTime);

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
