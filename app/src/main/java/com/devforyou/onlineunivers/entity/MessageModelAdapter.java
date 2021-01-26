package com.devforyou.onlineunivers.entity;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.devforyou.onlineunivers.FireBase.Model.MessageModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageModelAdapter implements MultiItemEntity {


    private MessageModelF message;
    private FirebaseUser user;



    public MessageModelAdapter(MessageModelF message, FirebaseUser user) {
        this.message = message;
        this.user = user;


    }


    public MessageModelF getMessage() {
        return message;
    }

    public void setMessage(MessageModelF message) {
        this.message = message;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }


    @Override
    public int getItemType() {

        if(message.getUser_id().equals(user.getUid()))
            return 1;
        else
         return 0;
    }
}
