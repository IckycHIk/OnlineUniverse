package com.devforyou.onlineunivers.Adapter;

import android.text.Html;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.FireBase.Model.MessageModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.entity.MessageModelAdapter;
import com.devforyou.onlineunivers.entity.QuickMultipleEntity;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseMultiItemQuickAdapter<MessageModelAdapter, BaseViewHolder> {
private FirebaseFirestore  firebaseFirestore;

    public ChatAdapter(ArrayList<MessageModelAdapter> data, FirebaseFirestore  firebaseFirestore) {
        super(data);
        addItemType(1, R.layout.chat_user2_item);
        addItemType(0,R.layout.chat_user1_item);

        this.firebaseFirestore =firebaseFirestore;

    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, MessageModelAdapter messageModelAdapter) {

MessageModelF message = messageModelAdapter.getMessage();

        switch (holder.getItemViewType()) {
            case 1:

                holder.setText(R.id.messageText,message.getText());

                Timestamp timestamp =message.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String string  = dateFormat.format(timestamp.toDate());
                holder.setText(R.id.messageTime,string);

                break;
            case 0:
                holder.setText(R.id.messageText,message.getText());

                firebaseFirestore.collection("Users").whereEqualTo("id",message.getUser_id())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {

                                if (e!=null){


                                }else {
                                    for (QueryDocumentSnapshot document : snapshots) {

                                        UserModelF userModelF = document.toObject( UserModelF.class);

                                        Picasso.get().load(userModelF.getImg_url()).into((CircleImageView)holder.getView(R.id.messageUserAvatar));

                                    }

                                }

                            }
                        });


                holder.setText(R.id.user_name,message.getUser_name());
                Timestamp tmp =message.getTime();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time  =  format.format( tmp.toDate());
               holder.setText(R.id.messageTime,time);

                break;
            default:
                break;
        }

    }
}
