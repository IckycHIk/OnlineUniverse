package com.devforyou.onlineunivers.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devforyou.onlineunivers.Activity.AboutCourseActivity;
import com.devforyou.onlineunivers.FireBase.Adapter.CourseFireStoreAdapter;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;

import com.devforyou.onlineunivers.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.Date;


public class MainFragment  extends Fragment implements CourseFireStoreAdapter.OnClickCourseRecyclerViewItem {

    private static final int TOTAL_MESSAGES_COUNT = 100;

    private Context context;
    protected final String senderId = "0";
    protected ImageLoader imageLoader;


    private Menu menu;
    private int selectionCount;
    private Date lastLoadedDate;
private Activity   activity;

private RecyclerView recyclerView;
private FirebaseFirestore firebaseFirestore;
private CourseFireStoreAdapter adapter;

    private MessagesList messagesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_layout_fragment,container,false);
        context = getActivity();
        activity = getActivity();

        recyclerView = view.findViewById(R.id.first_recycler_view);

   firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("Course");

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url, Object payload) {
                Picasso.get().load(url).into(imageView);
            }
        };


FirestoreRecyclerOptions<CourseModelF> options = new FirestoreRecyclerOptions.Builder<CourseModelF>()
        .setQuery(query,CourseModelF.class)
        .build();


adapter = new CourseFireStoreAdapter(options,imageLoader,this);

 recyclerView.setHasFixedSize(true);
 recyclerView.setLayoutManager(new LinearLayoutManager(context));
recyclerView.setAdapter(adapter);


        messagesList = (MessagesList)view.findViewById(R.id.messagesList);

        MessageInput input = (MessageInput) view.findViewById(R.id.input);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(CourseModelF courseModelF,String id) {

        Intent intent = new Intent(getActivity(), AboutCourseActivity.class);
        intent.putExtra("course", courseModelF.getId());
        startActivityForResult(intent,1);
        onStop();

    }

}
