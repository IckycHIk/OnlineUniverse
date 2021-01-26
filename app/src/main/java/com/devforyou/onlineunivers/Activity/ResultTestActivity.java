package com.devforyou.onlineunivers.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.ResultModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ldoublem.ringPregressLibrary.Ring;
import com.ldoublem.ringPregressLibrary.RingProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import am.widget.circleprogressbar.CircleProgressBar;


public class ResultTestActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private TextView result_course_name;
    private TextView result_test_name;
    private CircleProgressBar progressBar;
    private TextView result_mark;
    private TextView result_date;
    private TextView result_timer;
    private TextView result_correct_answer;
    private String Result_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.test_tesult_activity);

        initUi();
    }


    private void initUi() {
        result_course_name = findViewById(R.id.result_course_name);
        result_test_name = findViewById(R.id.result_test_name);
        progressBar = findViewById(R.id.progressBar);
        result_mark = findViewById(R.id.result_mark);
        result_date = findViewById(R.id.result_date);
        result_timer = findViewById(R.id.result_timer);
        result_correct_answer = findViewById(R.id.result_correct_answer);

Bundle  arg = getIntent().getExtras();

if(arg!=null) {


    Result_id = arg.getString("result_id");

    firebaseFirestore.collection("Result_Test").whereEqualTo("id", Result_id)
            .get()
            .addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        ResultModelF resultModelF = documentSnapshot.toObject(ResultModelF.class);

                       // Ring r = new Ring((int) resultModelF.getScore(), "", "", R.color.gray, R.color.green);

                        progressBar.setProgress((int) resultModelF.getScore());
                        progressBar.setShowProgressValue(true);


                        float tmp = (int) resultModelF.getScore() / 20;

                        result_mark.setText("Оценка: " + tmp);


                        Timestamp timestamp = resultModelF.getTest_date();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String string = dateFormat.format(timestamp.toDate());

                        result_date.setText("Дата тестирования : " + string);

                        result_timer.setText("Затраченное Время : " + resultModelF.getTime());


                        result_correct_answer.setText("Правильных ответов : "
                                + resultModelF.getCorect_answer() + " из " + resultModelF.getCount_answer() + " вопросов.");

                        firebaseFirestore.collection("Tests").document(resultModelF.getTest_id())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> tasks) {

                                        if (tasks.isSuccessful()) {

                                            TestModelF testModelF = tasks.getResult().toObject(TestModelF.class);

                                            result_test_name.setText(testModelF.getTitle());


                                            firebaseFirestore.collection("Course").document(testModelF.getCourse_id())
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            result_course_name.setText(documentSnapshot.toObject(CourseModelF.class).getTitle());
                                                        }
                                                    });
                                        }
                                    }
                                });


                    }
                }

            });


}else {

    finish();
}

    }
}
