package com.devforyou.onlineunivers.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devforyou.onlineunivers.Adapter.ChatAdapter;
import com.devforyou.onlineunivers.FireBase.Firebase;
import com.devforyou.onlineunivers.FireBase.Model.MessageModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.entity.MessageModelAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ChatFragment   extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageButton imageButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<MessageModelAdapter> data;

private String current_room;
private ChatAdapter adapter;
private Firebase firebase;

    private FirebaseUser current_user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        context = getActivity();



        recyclerView = view.findViewById(R.id.rvMsgList);
       editText = view.findViewById(R.id.etMessage);
        imageButton = view.findViewById(R.id.ivSend);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user = firebaseAuth.getCurrentUser();

        firebase = new Firebase();
        firebase.initFireStore(firebaseFirestore);

        data = new ArrayList<>();

        Bundle arg = this.getArguments();
        if(arg!=null){
            current_room =arg.getString("room_id");


            firebaseFirestore.collection("Messages").whereEqualTo("room_id",current_room)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onEvent(@androidx.annotation.Nullable QuerySnapshot snapshots, @androidx.annotation.Nullable FirebaseFirestoreException e) {

                            if (e != null) {

                                Log.d("Exeption", e.toString());
                            }
                            else {
                                data.clear();
                                ArrayList<MessageModelF> messageModel = new ArrayList<>();

                                for (QueryDocumentSnapshot document : snapshots) {

                                    MessageModelF messageModelF = document.toObject(MessageModelF.class);

                                    messageModel.add(messageModelF);

                                }

                                Collections.sort( messageModel, new Comparator<MessageModelF>(){

                                    @Override
                                    public int compare(MessageModelF t1, MessageModelF t2) {

                                        Timestamp a = t1.getTime();
                                        Timestamp b = t2.getTime();
                                        if (a.getSeconds()<b.getSeconds())
                                            return -1;
                                        else if (a.getSeconds()==b.getSeconds()) // it's equals
                                            return 0;
                                        else
                                            return 1;

                                    }
                                });

                                for(int i =0;i<messageModel.size();i++){

                                data.add(new MessageModelAdapter(messageModel.get(i),current_user));

                                }

                                adapter.setNewData(data);
                                recyclerView.scrollToPosition(data.size()-1);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });

        }

        adapter = new ChatAdapter(data,firebaseFirestore);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setAnimationEnable(true);
       recyclerView.setAdapter(adapter);



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(editText.getText().toString().trim())){

                    Toast.makeText(context,"Текст не может быть пустым",Toast.LENGTH_LONG).show();
                }
                else {
                    MessageModelF messageModelF = new MessageModelF();
                    messageModelF.setRoom_id(current_room);
                    messageModelF.setText(editText.getText().toString());
                    messageModelF.setTime(new Timestamp(new Date()));
                    messageModelF.setUser_id(current_user.getUid());
                    messageModelF.setUser_name(current_user.getDisplayName());

                     editText.setText(" ");


                    firebase.addMessage(messageModelF);
                }

            }
        });


        return view;
    }


}
