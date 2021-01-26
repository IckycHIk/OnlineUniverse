package com.devforyou.onlineunivers.Activity.UserInfo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devforyou.onlineunivers.Activity.ResultTestActivity;
import com.devforyou.onlineunivers.Adapter.ResultListAdapter;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.ResultModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.interf.onResultItemClick;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by jamase on 2016-04-09.
 */
public class UserInfoFragement2 extends Fragment implements onResultItemClick {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ArrayList<ResultModelF> data;
    private ResultListAdapter adapter;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.user_info, container, false);
        recyclerView = root.findViewById(R.id.result_recycler_view);
        // recyclerView.hasFixedSize();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();

         context = getActivity();
        data = new ArrayList<>();
       adapter = new ResultListAdapter(data,firebaseFirestore,this);

       recyclerView.hasFixedSize();
       recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Query query = firebaseFirestore.collection("Result_Test")
                .whereEqualTo("user_id", firebaseUser.getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d("ExeptionResult", e.toString());
                }
                if (snapshots.size() != 0) {
                    data  = new ArrayList<>();
                    for (QueryDocumentSnapshot document : snapshots) {

                        ResultModelF resultModelF = document.toObject(ResultModelF.class);

                        data.add(resultModelF);
                        adapter.setNewData(data);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        });

        recyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onResultClick(String result_id) {

        Intent intent = new Intent(getActivity(), ResultTestActivity.class);
        intent.putExtra("result_id", result_id);

        startActivity(intent);

    }


}
