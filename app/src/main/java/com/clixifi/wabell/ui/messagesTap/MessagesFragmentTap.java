package com.clixifi.wabell.ui.messagesTap;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clixifi.wabell.R;

import com.clixifi.wabell.data.Conv;

import com.clixifi.wabell.databinding.FragmentMessagesTapBinding;

import com.clixifi.wabell.ui.chat.ChatScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


public class MessagesFragmentTap extends Fragment {

    FragmentMessagesTapBinding binding;
    View v;
    MyRooms handler;
    private DatabaseReference mConvDatabase;
    private DatabaseReference mMessageDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages_tap, container, false);
        v = binding.getRoot();
        handler = new MyRooms(getActivity());
        binding.setHandler(handler);
        configFirebaseDatabase();
        mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mConvDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(mCurrent_user_id);

        mConvDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(mCurrent_user_id);
        mUsersDatabase.keepSynced(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        binding.recRooms.setLayoutManager(linearLayoutManager);


        return v;
    }

    private void addToContacts() {
        /*chatRequestsRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        contactsRef.child("ql9I1bDHNHhW3WxYwbCeCFIOKGh1").child("etLNcoK2VHff0PdZ1ZklDAHuNRl2").child("Contact")
                .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    contactsRef.child("etLNcoK2VHff0PdZ1ZklDAHuNRl2").child(currentUserId).child("Contact")
                            .setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                chatRequestsRef.child("ql9I1bDHNHhW3WxYwbCeCFIOKGh1").child("etLNcoK2VHff0PdZ1ZklDAHuNRl2")
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    chatRequestsRef.child("etLNcoK2VHff0PdZ1ZklDAHuNRl2").child(currentUserId)
                                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(getContext(), "New Contact Saved", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        }
                    });

                }
            }
        });*/
    }

    private void sendAutoMessage() {
        DatabaseReference mRootRef;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        String current_user_ref = "messages/" + "zFB7sQevOTZ3sQYAvzRoP5PpFqq2" + "/" + "hGrvfFbzWlZvtV3XGWkkcB4ylIY2";
        String chat_user_ref = "messages/" + "hGrvfFbzWlZvtV3XGWkkcB4ylIY2" + "/" + "zFB7sQevOTZ3sQYAvzRoP5PpFqq2";

        DatabaseReference user_message_push = mRootRef.child("messages")
                .child("zFB7sQevOTZ3sQYAvzRoP5PpFqq2").child("hGrvfFbzWlZvtV3XGWkkcB4ylIY2").push();

        String push_id = user_message_push.getKey();

        Map messageMap = new HashMap();
        messageMap.put("message", "test message");
        messageMap.put("seen", false);
        messageMap.put("type", "text");
        messageMap.put("time", ServerValue.TIMESTAMP);
        messageMap.put("from", "zFB7sQevOTZ3sQYAvzRoP5PpFqq2");

        Map messageUserMap = new HashMap();
        messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
        messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);


        mRootRef.child("Chat").child("zFB7sQevOTZ3sQYAvzRoP5PpFqq2").child("hGrvfFbzWlZvtV3XGWkkcB4ylIY2").child("seen").setValue(true);
        mRootRef.child("Chat").child("zFB7sQevOTZ3sQYAvzRoP5PpFqq2").child("hGrvfFbzWlZvtV3XGWkkcB4ylIY2").child("timestamp").setValue(ServerValue.TIMESTAMP);

        mRootRef.child("Chat").child("hGrvfFbzWlZvtV3XGWkkcB4ylIY2").child("zFB7sQevOTZ3sQYAvzRoP5PpFqq2").child("seen").setValue(false);
        mRootRef.child("Chat").child("hGrvfFbzWlZvtV3XGWkkcB4ylIY2").child("zFB7sQevOTZ3sQYAvzRoP5PpFqq2").child("timestamp").setValue(ServerValue.TIMESTAMP);

        mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });


    }


    private void configFirebaseDatabase() {

    }


    @Override
    public void onStart() {
        super.onStart();
        Query conversationQuery = mConvDatabase.orderByChild("timestamp");
        Log.e(TAG, "onStart: "+"here" );
        final FirebaseRecyclerAdapter<Conv, ChatsViewHolder> firebaseConvAdapter = new FirebaseRecyclerAdapter<Conv, ChatsViewHolder>(
                Conv.class,
                R.layout.room_item,
                ChatsViewHolder.class,
                conversationQuery
        ) {
            @Override
            protected void populateViewHolder(final ChatsViewHolder convViewHolder, final Conv conv, int i) {

                final String list_user_id = getRef(i).getKey();

                Query lastMessageQuery = mMessageDatabase.child(list_user_id).limitToLast(1);

                lastMessageQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String data = dataSnapshot.child("message").getValue().toString();
                        //convViewHolder.setMessage(data, conv.isSeen());

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userThumb = dataSnapshot.child("image").getValue().toString();
                        convViewHolder.userName.setText(userName);
                        StaticMethods.LoadImage(getActivity() , convViewHolder.profileImage ,userThumb , null );
                        if (dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            if(userOnline.equals("true")){
                                convViewHolder.userOnlineStatus.setVisibility(View.VISIBLE);
                            }else {
                                convViewHolder.userOnlineStatus.setVisibility(View.GONE);
                            }
                            //convViewHolder.setUserOnline(userOnline);

                        }

                        //convViewHolder.setName(userName);
                        //convViewHolder.setUserImage(userThumb, getContext());

                        convViewHolder.linView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent chatIntent = new Intent(getContext(), ChatScreen.class);
                                chatIntent.putExtra("user_id", list_user_id);
                                chatIntent.putExtra("user_name", userName);
                                startActivity(chatIntent);
                            }
                        });
                        //convViewHolder.onChatClick(list_user_id , userName , getActivity());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        binding.recRooms.setAdapter(firebaseConvAdapter);
       // binding.recRooms.setAdapter(adapter);
    }

    public class MyRooms {
        Context context;

        public MyRooms(Context context) {
            this.context = context;
        }
    }

    /*public static class ConvViewHolder extends RecyclerView.ViewHolder {
        Context context;
        View mView;

        public ConvViewHolder(View itemView , Context context ) {
            super(itemView);
            this.context = context;
            this.mView = itemView;

        }

        public void setMessage(String message, boolean isSeen) {

            TextView userStatusView = (TextView) mView.findViewById(R.id.txt_bio);
            //userStatusView.setText(message);

            if (!isSeen) {
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.BOLD);

            } else {
                userStatusView.setTypeface(userStatusView.getTypeface(), Typeface.NORMAL);
            }

        }

        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.txt_stName);
            userNameView.setText(name);

        }
        public void onChatClick(final String list_user_id , final String name , final Context context ) {
            LinearLayout openChat=  mView.findViewById(R.id.lin_OpenMessage);
            openChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent chatIntent = new Intent(context, ChatScreen.class);
                    chatIntent.putExtra("user_id", list_user_id);
                    chatIntent.putExtra("user_name", name);
                    context.startActivity(chatIntent);

                }
            });


        }
        public void setUserImage(String thumb_image, Context ctx) {

            CircleImageView userImageView =  mView.findViewById(R.id.st_img);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.avatar).into(userImageView);

        }

        public void setUserOnline(String online_status) {

            RelativeLayout userOnlineView = mView.findViewById(R.id.rel_online);

            if (online_status.equals("true")) {

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }


    }*/
    public static class ChatsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView userName;
        RelativeLayout userOnlineStatus;
        LinearLayout linView ;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            linView = itemView.findViewById(R.id.lin_OpenMessage);
            profileImage = itemView.findViewById(R.id.st_img);
            userName = itemView.findViewById(R.id.txt_stName);
            userOnlineStatus = itemView.findViewById(R.id.rel_online);
        }
    }
}