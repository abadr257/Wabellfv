package com.clixifi.wabell.ui.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Messages;
import com.clixifi.wabell.utils.StaticMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int RIGHT_message = 3;
    private static final int LEFT_message = 4;
    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase, messages;
    Context context;
    String mCUID;

    public MessageAdapter(List<Messages> mMessageList, String mCurrentUID, Context context) {
        this.mMessageList = mMessageList;
        this.mCUID = mCurrentUID;
        this.context = context;
    }

    View v;
    Messages c;
    String userid = FirebaseAuth.getInstance().getUid();

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == RIGHT_message) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mine_message, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_single_layout, parent, false);
        }


        return new MessageViewHolder(itemView);

    }

    @Override
    public int getItemViewType(int position) {
        c = mMessageList.get(position);

        from_user = c.getFrom();
        message_type = c.getType();

        if (from_user.equals(mCUID)) {
            return RIGHT_message;
        } else {
            return LEFT_message;
        }


    }

    String from_user;
    String message_type;

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);

        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        c = mMessageList.get(i);

        from_user = c.getFrom();
        message_type = c.getType();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                StaticMethods.LoadImage(context, viewHolder.profileImage, image, null);
                viewHolder.displayName.setText(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            viewHolder.messageImage.setVisibility(View.INVISIBLE);


        } else {

            viewHolder.messageText.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
