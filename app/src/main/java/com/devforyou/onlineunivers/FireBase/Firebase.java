package com.devforyou.onlineunivers.FireBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.devforyou.onlineunivers.FireBase.Model.ChatRoomModelF;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.MessageModelF;
import com.devforyou.onlineunivers.FireBase.Model.ResultModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.devforyou.onlineunivers.RoomDb.Model.CourseModel;
import com.devforyou.onlineunivers.entity.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Firebase {


    private FirebaseFirestore db;
private boolean ss;


    public void initFireStore( FirebaseFirestore db) {

        this.db = db;

    }




   public void getCourseFireStore(Context context){

            db.collection("Course")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());


                                Toast.makeText(context, document.getString("Title"),Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());

                        }
                    }
                });
    }

    public boolean addUser(FirebaseUser user,Context context){

        db.collection("Users").whereEqualTo("id",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.getResult().size()==0){
                            UserModelF userModelF = new UserModelF(user.getUid(), user.getDisplayName(), "", 1, 0, user.getPhotoUrl().toString());

// Add a new document with a generated ID
                            db.collection("Users")
                                    .add(userModelF)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);

                                        }
                                    });
                        }
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "" + document.getId() + " => " + document.getData());

                            }

                        } else {
                            Log.w(TAG, " Error getting documents.", task.getException());
                            ss =  false;

                        }
                    }
                });

       return ss;
    }



    public void addTestResult(ResultModelF resultModelF){

        db.collection("Result_Test")
                .add(resultModelF)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        db.collection("Result_Test").document(documentReference.getId())
                         .update("id",documentReference.getId())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                      Log.d("Test_Resultt","Add_Tesst_Result_Id"+ documentReference.getId());

                                    }
                                });
                    }
                });
    }

    public void createRoomChat(String courseModel, String title){

        Timestamp timestamp = new Timestamp(new Date());

        ChatRoomModelF  chatRoomModelF  = new ChatRoomModelF();

        chatRoomModelF.setDate(timestamp);
        chatRoomModelF.setCourse_id(courseModel);
        chatRoomModelF.setTitle(title);

        db.collection("Chat_Room")
                .add(chatRoomModelF)
                .addOnSuccessListener(documentReference -> {

                    db.collection("Chat_Room").document(documentReference.getId())
                            .update("id",documentReference.getId())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d("Room_chat_Create","succes"+ documentReference.getId());

                                }
                            });

                });
    }

    public void addMessage(MessageModelF messageModelF){



        db.collection("Messages")
                .add(messageModelF)
                .addOnSuccessListener(documentReference -> {

                    db.collection("Messages").document(documentReference.getId())
                            .update("id",documentReference.getId())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d("Messages_Create","succes"+ documentReference.getId());

                                }
                            });

                });
    }



}
