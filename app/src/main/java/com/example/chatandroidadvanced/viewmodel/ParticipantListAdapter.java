package com.example.chatandroidadvanced.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.chatandroidadvanced.view.MainActivity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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

            //todo find better solution to not display current user in recyclerview
            SharedPreferences preferences = mContext.getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);
            if(preferences.getString(MainActivity.FIRSTNAME, "").equals(current.getfirstName())
                   && preferences.getString(MainActivity.LASTNAME, "").equals(current.getlastName())
                   && preferences.getString(MainActivity.EMAIL, "").equals(current.getEmail())) {

                //if it is the actual user it should not be displayed in recycler view
                holder.conversationEmail.setText("");
                holder.conversationTime.setText("");
                holder.conversationPartner.setText("");

            } else {

                //Log.d("preference name", preferences.getString(FIRSTNAME, ""));
                //looking if room participant is empty
                Log.d("Participant info room", current.getCreatedBy() + " " + current.getCreatedDate());

                //Alle LAst und email auch
                holder.conversationEmail.setText(current.getEmail());
                holder.conversationTime.setText("");
                holder.conversationPartner.setText(current.getfirstName() + " " + current.getlastName());

                //todo should image be loaded when user is created??
                String image = "https://robohash.org/" + current.getEmail();
                GlideApp.with(mContext)
                        .load(image)
                        .placeholder(R.drawable.ic_loading_image)
                        .error(R.drawable.ic_loading_error)
                        .into(holder.conversationImage);
            }
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
