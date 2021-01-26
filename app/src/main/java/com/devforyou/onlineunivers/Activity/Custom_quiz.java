package com.devforyou.onlineunivers.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.devforyou.onlineunivers.FireBase.Model.QuestionModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class Custom_quiz extends AppCompatActivity {

    EditText question;
    EditText aText;
    EditText bText;
    EditText cText;
    EditText dText;
    RadioButton aRadio;
    RadioButton bRadio;
    RadioButton cRadio;
    RadioButton dRadio;

    int currentQuestion = 1;
    int previousQuestion = 1;
    TextView questionNumber;

    ArrayList<QuestionModelF> ques;
    String selectedOption = "";
    private ArrayList<QuestionModelF> questionModelF;


    Button save_button;
    AlertDialog alertDialog;
    private View dialogvView;
    String fileName = "file";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    CardView fab,f2,fl;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser current_user;
    private Context context;
    private String course_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_create_quiz);
        question = findViewById(R.id.questionView);
        question =  findViewById(R.id.questionView);
        aText =  findViewById(R.id.aText);
        bText =  findViewById(R.id.bText);
        cText =  findViewById(R.id.cText);
        dText =  findViewById(R.id.dText);
        questionNumber =  findViewById(R.id.questionNumber);
        aRadio =  findViewById(R.id.aRadio);

        bRadio =  findViewById(R.id.bRadio);
        cRadio =  findViewById(R.id.cRadio);
        dRadio =  findViewById(R.id.dRadio);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
       firebaseAuth = FirebaseAuth.getInstance();
       current_user = firebaseAuth.getCurrentUser();
        context = this;
        selectedOption = "";
        currentQuestion = 1;
        setListeners();
        questionModelF = new ArrayList<>();
        ques = new ArrayList<>();

        alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        dialogvView = inflater.inflate(R.layout.dialog_custom,null);



        fab = findViewById(R.id.nextfab);
        fl = findViewById(R.id.fab2);//save button
        f2 = findViewById(R.id.pre_card);

        Bundle arg= getIntent().getExtras();
        if(arg!=null){

            course_id = arg.getString("course_id");

        }

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousQuestion>1) {
                    previousQuestion--;
                    setAllData(previousQuestion);
                }
                if(previousQuestion==1)
                    f2.setVisibility(View.INVISIBLE);
                    //Question question1 = new Question();
                Toast.makeText(Custom_quiz.this, String.valueOf(previousQuestion), Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previousQuestion!=currentQuestion) {
                    previousQuestion++;
                    if(previousQuestion!=currentQuestion)
                    setAllData(previousQuestion);
                    else {
                        clearAllData();
                        questionNumber.setText(String.valueOf(currentQuestion));
                    }
                    if(previousQuestion>1)
                        f2.setVisibility(View.VISIBLE);
                }
                boolean cont = getEnteredQuestionsValue();
                if (cont)
                {
                    previousQuestion++;
                    currentQuestion++;
                    Toast.makeText(Custom_quiz.this, "Вопрос " + currentQuestion, Toast.LENGTH_SHORT).show();
                    questionNumber.setText(String.valueOf(currentQuestion));
                    clearAllData();
                    f2.setVisibility(View.VISIBLE);
                }
            }
        });

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean cont = getEnteredQuestionsValue();

                if(ques.size()!=0)
                {

                    // get dialog_custom.xml view
                    LayoutInflater li = LayoutInflater.from(Custom_quiz.this);
                    View promptsView = li.inflate(R.layout.dialog_custom, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Custom_quiz.this);

                    // set dialog_custom.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    final EditText userInput =  promptsView
                            .findViewById(R.id.editTextDialogUserInput);
                    final EditText userTime = promptsView.findViewById(R.id.editTextDialogUserInput1);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            if (TextUtils.isEmpty(userInput.getText().toString().trim())) {
                                                userInput.setError("Это поле не может быть пустым");
                                            }
                                            else {
                                                long str = 0;
                                                if(!TextUtils.isEmpty(userTime.getText().toString().trim())) {
                                                    str = Long.parseLong(userTime.getText().toString().trim());

                                                }else {
                                                     str = 0;
                                                }

                                                TestModelF testModelF = new TestModelF();
                                                testModelF.setTitle(userInput.getText().toString().trim());
                                                testModelF.setTime(str);
                                                testModelF.setCourse_id(course_id);
                                                testModelF.setSee_answer(true);

                                                firebaseFirestore.collection("Tests")
                                                        .add(testModelF)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {

                                                                firebaseFirestore.collection("Tests").document(documentReference.getId())
                                                                        .update("id",documentReference.getId())
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                Log.d("Tests","Add_Tesst_Id"+ documentReference.getId());
                                                                                String test_id = documentReference.getId();


                                                                                for(int i =0;i<ques.size();i++) {
                                                                                    ques.get(i).setTest_id(test_id);
                                                                                    firebaseFirestore.collection("Question")
                                                                                            .add(ques.get(i))
                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                                @Override
                                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                                    firebaseFirestore.collection("Question").document(documentReference.getId())
                                                                                                            .update("id",documentReference.getId())
                                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(Void aVoid) {
                                                                                                                    Log.d("Tests","Add_Tesst_Id"+ documentReference.getId());
                                                                                                                    finish();
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            });
                                                                                }


                                                                            }
                                                                        });
                                                            }
                                                        });
                                                Toast.makeText(context,"Успешно добавленно",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                            .setNegativeButton("Закрыть",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
                else
                {

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Custom_quiz.this);
        builder.setMessage("Выйти и не сохранить?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void updateData(int position) {
        QuestionModelF question1 = new QuestionModelF ();
        question1 = ques.get(position-1);
    }

    public void setAllData(int position) {
        clearAllData();
        QuestionModelF question1 = new QuestionModelF();
        question1 = ques.get(position-1);
        questionNumber.setText(String.valueOf(question1.getId()));
        question.setText(question1.getQuestion());
        aText.setText(question1.getOpt_A());
        bText.setText(question1.getOpt_B());
        cText.setText(question1.getOpt_C());
        dText.setText(question1.getOpt_D());
        switch (question1.getAnswer()){
            case "A":
                aRadio.setChecked(true);
                break;
            case "B":
                bRadio.setChecked(true);
                break;
            case "C":
                cRadio.setChecked(true);
                break;
            case "D":
                dRadio.setChecked(true);
                break;
        }
    }

    private void clearAllData() {

        aRadio.setChecked(false);
        bRadio.setChecked(false);
        cRadio.setChecked(false);
        dRadio.setChecked(false);
        aText.setText(null);
        bText.setText(null);
        cText.setText(null);
        dText.setText(null);
        question.setText(null);
        selectedOption = "";
    }

    private boolean getEnteredQuestionsValue() {

        boolean cont = false;
        if (TextUtils.isEmpty(question.getText().toString().trim())) {
            question.setError("Пожалуйста напишите вопрос");
        }
        else if (TextUtils.isEmpty(aText.getText().toString().trim())) {
            aText.setError("Пожалуйста напишите вариант ответа A");
        }
        else if (TextUtils.isEmpty(bText.getText().toString().trim())) {
            bText.setError("Пожалуйста напишите  вариант ответа B");
        }
        else if (TextUtils.isEmpty(cText.getText().toString().trim())) {
            cText.setError("Пожалуйста напишите  вариант ответа C");
        }
        else if (TextUtils.isEmpty(dText.getText().toString().trim())) {
            dText.setError("Пожалуйста напишите  вариант ответа D");
        }
        else if (selectedOption.equals("")) {
            Toast.makeText(this, "Пожалуйста выберите правильный вариант ответа", Toast.LENGTH_SHORT).show();
        }
        else {
            QuestionModelF  quest = new QuestionModelF();
            quest.setId(String.valueOf(currentQuestion));
            quest.setQuestion(question.getText().toString());
            quest.setOpt_A(aText.getText().toString());
            quest.setOpt_B(bText.getText().toString());
            quest.setOpt_C(cText.getText().toString());
            quest.setOpt_D(dText.getText().toString());
            quest.setAnswer(selectedOption);
            ques.add(quest);

            cont = true;
        }
        return cont;
    }

    private void setListeners() {
        aRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "A";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        bRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "B";
                aRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        cRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "C";
                bRadio.setChecked(false);
                aRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        dRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "D";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                aRadio.setChecked(false);
            }
        });

    }
}
