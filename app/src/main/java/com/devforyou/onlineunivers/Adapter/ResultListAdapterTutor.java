package com.devforyou.onlineunivers.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.ResultModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.interf.onResultItemClick;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResultListAdapterTutor extends BaseQuickAdapter<ResultModelF, BaseViewHolder> {

   private FirebaseFirestore firebaseFirestore;
   private onResultItemClick  onResultItemClick;

    public ResultListAdapterTutor(@Nullable List<ResultModelF> data, FirebaseFirestore firebaseFirestore, onResultItemClick  onResultItemClick) {
        super(R.layout.test_result_tutor_item, data);
        this.firebaseFirestore =firebaseFirestore;
        this.onResultItemClick = onResultItemClick;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ResultModelF resultModelF) {

        firebaseFirestore.collection("Tests").document(resultModelF.getTest_id())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        TestModelF testModelF = documentSnapshot.toObject(TestModelF.class);

                        ((TextView)holder.getView(R.id.item_title_test)).setText(testModelF.getTitle());



                        firebaseFirestore.collection("Users").whereEqualTo("id",resultModelF.getUser_id())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot snapshots) {

                                        if(snapshots.size()>=0) {
                                            for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                                                UserModelF userModelF =  documentSnapshot.toObject(UserModelF.class);
                                                ((TextView) holder.getView(R.id.item_user)).setText(userModelF.getName());

                                            }
                                        }
                                    }
                                });

                        firebaseFirestore.collection("Course").document(testModelF.getCourse_id())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshots) {
                                        CourseModelF courseModelF = documentSnapshots.toObject(CourseModelF.class);

                                        ((TextView)holder.getView(R.id.item_title)).setText(courseModelF.getTitle());
                                    }
                                });


                    }
                });
        ((Button) holder.getView(R.id.item_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResultItemClick.onResultClick(resultModelF.getId());
            }
        });
    }
}
