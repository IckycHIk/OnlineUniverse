package com.devforyou.onlineunivers.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devforyou.onlineunivers.FireBase.Adapter.CourseFireStoreAdapter;
import com.devforyou.onlineunivers.FireBase.Firebase;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.UserModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.RoomDb.Model.CourseModel;
import com.devforyou.onlineunivers.entity.Course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AboutCourseActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    private Context context;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    private ImageView imageView;
    private TextView groupValue;
    private Button addCourse;
    private Button iWantThis;
    private TextView whatCanIDo;
    private TextView description;
   private  String name;
   private String user_tut_id="";
   private FirebaseAuth firebaseAuth ;
  private   FirebaseUser firebaseUser ;
  private String  courses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.about_course);
        context = this;

        imageView = findViewById(R.id.about_course_img);
        groupValue = findViewById(R.id.about_course_group_value);
        addCourse = findViewById(R.id.add_course);
        whatCanIDo = findViewById(R.id.what_can_i_do);
        description = findViewById(R.id.course_description);
        addCourse.setOnClickListener(view -> {

            firebaseFirestore.collection("Users").whereEqualTo("id",firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {


                                    String tmp = document.getString("courses")+name+";";

                                    firebaseFirestore.collection("Users").document(document.getId())
                                            .update("courses",tmp)
                                            .addOnCompleteListener(task12 -> {
                                              Log.d(TAG,"Update Succes: "+ tmp);

                                                firebaseFirestore.collection("Course").document(name)
                                                        .get()
                                                        .addOnCompleteListener(taskl->{

                                                            CourseModelF courseModelF = taskl.getResult().toObject(CourseModelF.class);

                                                            long number = courseModelF.getPeople_value();

                                                            firebaseFirestore.collection("Course").document(name)
                                                                    .update("people_value", number+1)
                                                                    .addOnCompleteListener(task1 -> {

                                                                           Intent intent =  new Intent();

                                                                           intent.putExtra("add",true);
                                                                           setResult(RESULT_OK,intent);
                                                                           finish();
                                                                    });

                                                        });

                                            });

                                }
                            }

                        }
                    });

        });


        iWantThis = findViewById(R.id.add_to_dream);
        iWantThis.setOnClickListener(view -> onBackPressed());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        imageLoader = (imageView, url, payload) -> {

        };

        Bundle arguments = getIntent().getExtras();
         name = arguments.get("course").toString();
        if (name.equals("")) {
            onBackPressed();
        } else {
           // Toast.makeText(context, name, Toast.LENGTH_LONG).show();
            firebaseFirestore = FirebaseFirestore.getInstance();


            DocumentReference docRef =firebaseFirestore.collection("Course").document(name+"");

            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d(TAG, "Current data: " + snapshot.getData());
                        // Log.d(TAG, "DocumentSnapshot data: " + snapshot.getData());

                            CourseModelF  model = snapshot.toObject(CourseModelF.class);

                            Picasso.get().load(model.getImg_url()).into(imageView);

                            groupValue.setText("Группа "+model.getPeople_value() +"/"+model.getMax_people_value());

                            whatCanIDo.setText(model.getWhat_can_lear());

                            description.setText(model.getDescription());
                        user_tut_id =model.getUser_id();

                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
            });

            firebaseFirestore.collection("Users").whereEqualTo("id",firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    UserModelF courseModelF = document.toObject(UserModelF.class);



                                   if(courseModelF.getCourses().contains(name)){

                                       if( user_tut_id.equals(courseModelF.getId()))
                                           addCourse.setText("Вы являетесь Учителем курса");
                                           else
                                       addCourse.setText("Вы уже записанны на этот курс");

                                       addCourse.setBackgroundColor(getResources().getColor(R.color.dark_grey_two));
                                       addCourse.setClickable(false);
                                   }
                                }
                            }

                        }
                    });

        }

    }


}
