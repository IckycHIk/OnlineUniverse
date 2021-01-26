package com.devforyou.onlineunivers.Activity.UserInfo;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.devforyou.onlineunivers.Activity.MainActivity;
import com.devforyou.onlineunivers.Adapter.mUserInfoRecycleAdapter;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.devforyou.onlineunivers.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by zhangning on 2016-04-08.
 * 一个简单的fragement
 */
public class UserInfoFragement extends Fragment implements mUserInfoRecycleAdapter.onItemClick {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;


    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser curent_user;
    private ArrayList<CourseModelF> tmpCourseList;
    private   mUserInfoRecycleAdapter mAdapter;
    private boolean onDelete =false;
    private NiftyDialogBuilder dialogBuilder;
private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycleadapterxml, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        tmpCourseList = new ArrayList<>();
         mAdapter = new mUserInfoRecycleAdapter(tmpCourseList,this);
       // mAdapter.setAnimationEnable(true);
        context= getContext();

        dialogBuilder= NiftyDialogBuilder.getInstance(context);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        curent_user = firebaseAuth.getCurrentUser();


        firebaseFirestore.collection("Users").whereEqualTo("id", curent_user.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshots != null) {
                            for (QueryDocumentSnapshot doc : snapshots) {
                                UserModelF userModelF = doc.toObject(UserModelF.class);

                                if (!userModelF.getCourses().equals("")&&!onDelete) {
                                    String[] tmp = userModelF.getCourses().split(";");

                                    // Toast.makeText(context,document.getString("courses"),Toast.LENGTH_SHORT).show();

                                    for (int i = 0; i < tmp.length; i++) {

                                        FirebaseFirestore ds = FirebaseFirestore.getInstance();
                                        DocumentReference docRef = ds.collection("Course").document(tmp[i].trim());
                                        docRef.get()
                                                .addOnCompleteListener(taskl -> {

                                                    CourseModelF courseModelF = taskl.getResult().toObject(CourseModelF.class);

                                                    //  Toast.makeText(context,courseModelF.getDescription(),Toast.LENGTH_SHORT).show();
                                                    tmpCourseList.add(courseModelF);
                                                    mAdapter.setNewData(tmpCourseList);
                                                    mAdapter.notifyDataSetChanged();
                                                });
                                    }
                                }


                            }


                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });


        mRecyclerView.setAdapter(mAdapter);
        //    mUserInfoRecycleAdapter madapter = new mUserInfoRecycleAdapter(s);
      //  mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        return root;
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void deleteCourse(CourseModelF courseModelF,int position) {
        onDelete = true;

        if (!courseModelF.getUser_id().equals(firebaseAuth.getUid())){
            dialogBuilder
                    .withDialogColor(context.getColor(R.color.blue))
                    .withDividerColor(context.getColor(R.color.navigation_main))
                    .withTitle("Удалить этот курс?")
                    .withButton1Text("Да")
                    .withButton2Text("Нет")
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            firebaseFirestore.collection("Users").whereEqualTo("id", curent_user.getUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {

                                            UserModelF userModelF = doc.toObject(UserModelF.class);


                                            firebaseFirestore.collection("Users").document(doc.getId())
                                                    .update("courses", userModelF.getCourses().replaceAll(courseModelF.getId() + ";", ""))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            long number = courseModelF.getPeople_value();
                                                            number -= 1;

                                                            firebaseFirestore.collection("Course").document(courseModelF.getId())
                                                                    .update("people_value", number)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Log.d(TAG, "Успешно удаленно:" + courseModelF.getId());
                                                                            // Toast.makeText(context,courseModelF.getPeople_value()-1+"",Toast.LENGTH_SHORT).show();
                                                                            tmpCourseList.remove(position);
                                                                            mAdapter.notifyDataSetChanged();
                                                                            onDelete = false;
                                                                            dialogBuilder.dismiss();
                                                                        }
                                                                    });

                                                        }
                                                    });

                                        }

                                    } else {
                                        Log.d(TAG, "Current data: null");
                                    }
                                }
                            });
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            }).show();

    }else {

            dialogBuilder
                    .withDialogColor(context.getColor(R.color.blue))
                    .withDividerColor(context.getColor(R.color.navigation_main))
                    .withTitle("Вы организатор курса ")
                    .withMessage("Желаете отказаться от организаторства?")
                    .withButton1Text("Да")
                    .withButton2Text("Нет")
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            firebaseFirestore.collection("Users").whereEqualTo("id", curent_user.getUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {

                                            UserModelF userModelF = doc.toObject(UserModelF.class);


                                            firebaseFirestore.collection("Users").document(doc.getId())
                                                    .update("courses", userModelF.getCourses().replaceAll(courseModelF.getId() + ";", ""))
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            long number = courseModelF.getPeople_value();
                                                            number -= 1;

                                                            firebaseFirestore.collection("Course").document(courseModelF.getId())
                                                                    .update("user_id", "")
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Log.d(TAG, "Успешно удаленно:" + courseModelF.getId());
                                                                            // Toast.makeText(context,courseModelF.getPeople_value()-1+"",Toast.LENGTH_SHORT).show();
                                                                            tmpCourseList.remove(position);
                                                                            mAdapter.notifyDataSetChanged();
                                                                            onDelete = false;
                                                                            dialogBuilder.dismiss();
                                                                        }
                                                          });
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.d(TAG, "Current data: null");
                                    }
                                }
                            });
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogBuilder.dismiss();
                }
            }).show();


        }


    }


}
