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

import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.Conv;

import com.clixifi.wabell.databinding.FragmentMessagesTapBinding;

import com.clixifi.wabell.ui.Adapters.FirebaseMessagesAdapter;
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


public class MessagesFragmentTap extends Fragment implements MessagesInterFace {

    FragmentMessagesTapBinding binding;
    View v;
    MyRooms handler;
    private DatabaseReference mConvDatabase;
    private DatabaseReference mMessageDatabase;
    private DatabaseReference mUsersDatabase;
    String userServerId = "";
    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    MessagesPresenter presenter ;
    FirebaseMessagesAdapter adapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages_tap, container, false);
        v = binding.getRoot();
        handler = new MyRooms(getActivity());
        presenter = new MessagesPresenter(this);
        presenter.getMessagesLog(getActivity());
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.DataProfile.getType().equals("student")){
                binding.setButton(true);
            }else {
                binding.setButton(false);
            }
        }else {
            if(StaticMethods.userData.getUserType().equals("student")){
                binding.setButton(true);
            }else {
                binding.setButton(false);
            }
        }
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






    private void configFirebaseDatabase() {

    }


    @Override
    public void onStart() {
        super.onStart();
        /*Query conversationQuery = mConvDatabase.orderByChild("timestamp");
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
                        int size = 0 ;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            size ++ ;
                        }
                        if(size == 0){
                            binding.setOnNoData(true);
                        }else {
                            binding.setOnNoData(false);
                            binding.setButton(false);
                        }
                        String data = dataSnapshot.child("message").getValue().toString();
                        if(!conv.isSeen()){
                            convViewHolder.icon.setVisibility(View.VISIBLE);
                            convViewHolder.userName.setTypeface(convViewHolder.userName.getTypeface(), Typeface.BOLD);
                        }
                        if(dataSnapshot.child("RecUser").getValue() != null){
                            userServerId = dataSnapshot.child("RecUser").getValue().toString();
                        }

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
                        int size = 0 ;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            size ++ ;
                        }
                        if(size == 0){
                            binding.setOnNoData(true);
                        }else {
                            binding.setOnNoData(false);
                        }
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


                        }



                        convViewHolder.linView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent chatIntent = new Intent(getContext(), ChatScreen.class);
                                chatIntent.putExtra("user_id", list_user_id);
                                chatIntent.putExtra("user_name", userName);
                                chatIntent.putExtra("user_image", "");
                                if(!userServerId.equals("")){
                                    chatIntent.putExtra("userServerId", userServerId);
                                }
                                startActivity(chatIntent);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };*/
        //binding.recRooms.setAdapter(firebaseConvAdapter);

    }

    @Override
    public void onMessages(CallsArray array) {
        if(array.getResult().size() == 0){
            binding.setButton(true);
            binding.setOnNoData(true);
        }else {
            binding.setOnNoData(false);
            binding.setButton(false);
        }
        adapter = new FirebaseMessagesAdapter(getActivity() , array);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recRooms.setLayoutManager(linearLayoutManager);
        binding.recRooms.setAdapter(adapter);
    }

    @Override
    public void onFail(boolean fail) {

    }

    @Override
    public void onConnection(boolean isConnected) {

    }

    public class MyRooms {
        Context context;

        public MyRooms(Context context) {
            this.context = context;
        }
    }


    public static class ChatsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView userName;
        RelativeLayout icon ;
        RelativeLayout userOnlineStatus;
        LinearLayout linView ;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            linView = itemView.findViewById(R.id.lin_OpenMessage);
            profileImage = itemView.findViewById(R.id.st_img);
            icon = itemView.findViewById(R.id.rel_isSeen);
            userName = itemView.findViewById(R.id.txt_stName);
            userOnlineStatus = itemView.findViewById(R.id.rel_online);
        }
    }
}