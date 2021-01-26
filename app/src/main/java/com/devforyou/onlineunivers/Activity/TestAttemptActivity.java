package com.devforyou.onlineunivers.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.devforyou.onlineunivers.Activity.Services.NotificationService;
import com.devforyou.onlineunivers.FireBase.Firebase;
import com.devforyou.onlineunivers.FireBase.Model.QuestionModelF;
import com.devforyou.onlineunivers.FireBase.Model.ResultModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.interf.TestResultOnClick;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.devforyou.onlineunivers.Activity.TestAttemptActivity.*;

public class TestAttemptActivity extends AppCompatActivity implements TestResultOnClick {
    private ArrayList<QuestionModelF> questions;
    private String []answers;
    private Toolbar toolbar;
    private DiscreteScrollView scrollView;
    private LinearLayout indexLayout;
    private GridView quesGrid;
    private ArrayList<Boolean> list_corect_answer;
    private ArrayList<String> list;
    private  ArrayList<String> arrayList;
    private ListenerRegistration lessonsRef;
    int flag_controller = 1;
    long main_timers;
    long timer ;// =((Test) getIntent().getExtras().get("Questions")).getTime()*60*1000;
    private  popGridAdapter popGrid;
    private Button next,prev;
    private TextView textView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private String TESTNAME;
    private RadioGroup group;
    private int countPaused = 0;
    private Context context;
    private QuestionAdapter questionAdapter;
    private CountDownTimer countDownTimer;
     private MenuItem counter ;
     private TextView timerTextView;
private CircleImageView circleImageView;
private long correctAnswer=0;
    private boolean see_answer  = true;
    private ArrayAdapter<String> arrayAdapter;
    private AlertDialog.Builder builderSingle;
    private  String Test_id;
    private boolean save = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_attempt);
        TESTNAME = "";
        toolbar=findViewById(R.id.toolbar);
       // toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
timerTextView =findViewById(R.id.timer_test);
circleImageView =findViewById(R.id.submit_test);
        Bundle argument = getIntent().getExtras();

        if(argument!=null) {
           Test_id  = argument.getString("test_id");


            firebaseFirestore.collection("Tests").whereEqualTo("id",Test_id)
             .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()){

                                for (QueryDocumentSnapshot document: task.getResult()){


                                    TestModelF testModelF = document.toObject(TestModelF.class);
                                    main_timers = testModelF.getTime()*1000;
                                    see_answer = testModelF.isSee_answer();
                                    timer =  testModelF.getTime()*1000;
                                   TESTNAME = testModelF.getTitle();
                                    initTimer();
                                }

                            }

                        }
                    });



           questions = new ArrayList<>();
            lessonsRef=    firebaseFirestore.collection("Question").whereEqualTo("test_id",Test_id)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if (e != null) {
                                Log.w(TAG, "Listen failed.", e);
                                return;
                            }

                            if(queryDocumentSnapshots!=null){
                                questions = new ArrayList<>();
                                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){


                                    QuestionModelF questionModelF = snapshot.toObject(QuestionModelF.class);
                                    questions.add(questionModelF);
                                    questionAdapter.setData(questions);
                                    questionAdapter.notifyDataSetChanged();
                                }

                                answers=new String[questions.size()];
                            }

                        }
                    });


        }


        circleImageView.setOnClickListener(view -> showPopUp());

        //setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));
        scrollView = findViewById(R.id.discrete);
        context = this;

        questionAdapter=new QuestionAdapter(questions);
        scrollView.setAdapter(questionAdapter);

        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrollView.getCurrentItem()==questions.size()-1){
                   showPopUp();
                }else {
                    //setNextPrevButton(scrollView.getCurrentItem() + 1);
                    scrollView.smoothScrollToPosition(scrollView.getCurrentItem() + 1);
                }
            }
        });

        prev=findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrollView.getCurrentItem()!=0){
                    //setNextPrevButton(scrollView.getCurrentItem()-1);
                    scrollView.smoothScrollToPosition(scrollView.getCurrentItem()-1);
                }
            }
        });

        setNextPrevButton(scrollView.getCurrentItem());
        indexLayout=findViewById(R.id.index_layout);
        indexLayout.setAlpha(.5f);
        quesGrid=findViewById(R.id.pop_grid);
        popGrid=new popGridAdapter(context);
        quesGrid.setAdapter(popGrid);
        quesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                scrollView.smoothScrollToPosition(i+1);
                slideUp(indexLayout);
            }
        });
        scrollView.addScrollListener(new DiscreteScrollView.ScrollListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
                setNextPrevButton(newPosition);
            }
        });

        //timer=((Test) getIntent().getExtras().get("Questions")).getTime()*60*1000;



    }

    void showPopUp(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Вы желаете закончить?");
        builder.setPositiveButton("Да", (dialogInterface, i) -> {
            submit();
//                setAlertDialog(answerText);
            dialogStart();
        });

        builder.setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();

    }
    void submit(){

        if(!save) {

            save =true;

            flag_controller = 0;
            int score = 0;
            list = new ArrayList<>();
            list_corect_answer = new ArrayList<>();
            arrayList = new ArrayList<>();
            for (int i = 0; i < answers.length; i++) {
                if (answers[i] != null && answers[i].equals(questions.get(i).getAnswer())) {
                    score++;
                }
                String temp = (answers[i] != null) ? answers[i] : "null) ";

                if (temp.equals(questions.get(i).getAnswer())) {

                    list_corect_answer.add(true);
                } else
                    list_corect_answer.add(false);


                if (see_answer) {
                    if (temp.equals("null")) {
                        list.add("Вы не дали ответ на данное задание");
                    } else if (temp.equals(questions.get(i).getAnswer())) {
                        list.add("Поздравляю вы ответили верно");
                        correctAnswer++;
                    } else list.add("К сожалению вы ответили не верно:\nПравильный ответ:" +
                            questions.get(i).getAnswer() + " - " + questions.get(i).getAnswerText() + " ");
                } else {
                    if (temp.equals("null")) {
                        list.add("Вы не дали ответ на донное задание");
                    } else if (temp.equals(questions.get(i).getAnswer())) {
                        list.add("Поздравляю вы ответили верно");
                        correctAnswer++;
                    } else
                        list.add("К сожалению вы ответили не верно:(\n ");

                }
                arrayList.add(questions.get(i).getQuestion());
            }

            try {

                long tmp = main_timers - timer;
                long hr = TimeUnit.MILLISECONDS.toHours(tmp), mn = (TimeUnit.MILLISECONDS.toMinutes(tmp) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tmp))),
                        sc = TimeUnit.MILLISECONDS.toSeconds(tmp) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tmp));

                String hms = format(hr) + ":" + format(mn) + ":" + format(sc);

                Firebase firebase = new Firebase();

                firebase.initFireStore(firebaseFirestore);

                ResultModelF resultModelF = new ResultModelF();

                resultModelF.setCorect_answer(correctAnswer);
                resultModelF.setCount_answer(questions.size());

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                resultModelF.setUser_id(firebaseAuth.getCurrentUser().getUid());

                long tmps = 100 / questions.size();

                long scor = tmps * correctAnswer;
                resultModelF.setScore(scor);
                resultModelF.setTest_id(Test_id);
                resultModelF.setTime(hms);

                Timestamp timestamp = new Timestamp(new Date());

                resultModelF.setTest_date(timestamp);

                firebase.addTestResult(resultModelF);

            } catch (Exception e) {
                Log.e("Result Update Failed ", e.getMessage());
            }
        }
    }

    void dialogStart() {

        builderSingle = new AlertDialog.Builder(context);
       // builderSingle.setIcon(R.mipmap.ic_launcher_round);
        long tmp = main_timers -timer;
        long hr= TimeUnit.MILLISECONDS.toHours(tmp),mn=(TimeUnit.MILLISECONDS.toMinutes(tmp)-
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tmp))),
                sc=TimeUnit.MILLISECONDS.toSeconds(tmp) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tmp));

        String  hms =format(hr)+":"+format(mn)+":"+format(sc) ;

        View view = getLayoutInflater().inflate(R.layout.custom_dialog_result,null);

        TextView result =view.findViewById(R.id.result);
        TextView testName = view.findViewById(R.id.test_name);
        TextView timeUse  = view.findViewById(R.id.time_use);

        result.setText("Результат: " +  correctAnswer +"/"+ questions.size()+"");
        testName.setText(TESTNAME);
        timeUse.setText("Затраченное Время   " + hms);

RecyclerView recyclerView = view.findViewById(R.id.recycle_view_answer);
recyclerView.setLayoutManager(new LinearLayoutManager(context));
recyclerView.hasFixedSize();


        final Adapter  adapter = new Adapter(R.layout.select_dilog_result,arrayList,list_corect_answer,this);


         arrayAdapter = new ArrayAdapter<>
                (context, R.layout.select_dilog_result);


recyclerView.setAdapter(adapter);
        builderSingle.setCustomTitle(view);



        for(String x: list){
            arrayAdapter.add(x);
        }

        for(int i =0;i<list_corect_answer.size();i++){

           // if(list_corect_answer.get(i)) arrayAdapter.;

        }

        countDownTimer.cancel();
        builderSingle.setCancelable(false);
        builderSingle.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                dialog.dismiss();
            }
        });


        builderSingle.show();

    }
    String format(long n){
        if(n<10)
            return "0"+n;
        else return ""+n;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countPaused<=2 && countPaused >=0 && flag_controller == 1)
            startService(new Intent(context,
                    NotificationService.class));
        countPaused++;
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(context, NotificationService.class));
        if(countPaused>2) {
            Toast.makeText(context,"ваш ответ был сохранен",Toast.LENGTH_SHORT).show();
            countPaused = -1000;
           // submit();
            dialogStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        stopService(new Intent(context, NotificationService.class));
    }

    void setNextPrevButton(int pos){
        if(pos==0){
//            prev.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            prev.setText("");

        }else {
//            prev.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            prev.setText("Преведущий вопрос");

        }
        if(pos==questions.size()-1){
            next.setText("Закончить");
//            next.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }else {
            next.setText("Следующий вопрос");
//            next.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onBackPressed() {
        showPopUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.attempt_menu, menu);

        counter = menu.findItem(R.id.counter);



        return  true;
    }

    private  void initTimer(){

        if(timer>0) {
            countDownTimer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    long millis = millisUntilFinished;
                    long hr = TimeUnit.MILLISECONDS.toHours(millis), mn = (TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                            sc = TimeUnit.MILLISECONDS.toSeconds(millis) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));


                    String hms = format(hr) + ":" + format(mn) + ":" + format(sc);
                    timerTextView.setText(hms);
                    timer = millis;
                }

                String format(long n) {
                    if (n < 10)
                        return "0" + n;
                    else return "" + n;
                }

                public void onFinish() {
                    //submit();
                    dialogStart();
                }
            }.start();
        }else {

            timerTextView.setText("Удачи");

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.submit){
            showPopUp();

            return true;
        }else if(id==R.id.info){
            togglePopUp();
        }
        return super.onOptionsItemSelected(item);
    }


    void togglePopUp(){
        if(indexLayout.getVisibility()==View.GONE){
            slideDown(indexLayout);
        }else slideUp(indexLayout);
    }

    @Override
    public void onClicks(int id) {

        String strName = arrayAdapter.getItem(id);
        AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
        builderInner.setMessage(strName);
        builderInner.setCancelable(false);
        builderInner.setTitle("Ваш Выбранный ответ");
        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
//                        finish();

                //builderSingle.show();
                       dialog.dismiss();
            }
        });
        builderInner.show();

    }



    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

        private int itemHeight;
        private ArrayList<QuestionModelF> data;

        private  void setData( ArrayList<QuestionModelF> data){

            this.data = data;

        }

        QuestionAdapter(ArrayList<QuestionModelF> data) {
            this.data = data;
        }


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            Activity context = (Activity) recyclerView.getContext();
            Point windowDimensions = new Point();
            context.getWindowManager().getDefaultDisplay().getSize(windowDimensions);
            itemHeight = Math.round(windowDimensions.y * 0.6f);
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.frag_test, parent, false);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    itemHeight);
            v.setLayoutParams(params);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.questionText.setText(data.get(position).getQuestion());
            holder.r1.setText(data.get(position).getOpt_A());
            holder.r2.setText(data.get(position).getOpt_B());
            holder.r3.setText(data.get(position).getOpt_C());
            holder.r4.setText(data.get(position).getOpt_D());
            holder.r5.setText("Отмена");

            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    final int selectedId = holder.radioGroup.getCheckedRadioButtonId();
                    if(i==R.id.radioButton){
                        answers[position]="A";
                    }  else if(i==R.id.radioButton2){
                        answers[position]="B";
                    }else if(i==R.id.radioButton3){
                        answers[position]="C";
                    }else if(i==R.id.radioButton4){
                        answers[position]="D";
                    }
                    else if(i==R.id.radioButton5) {
                        holder.radioGroup.clearCheck();
                        answers[position] = null;
                    }
                    popGrid.notifyDataSetChanged();
                }
            });
            if(answers[position]==null) {
                holder.radioGroup.clearCheck();
            }else if(answers[position].equals("A")) {
                holder.radioGroup.check(R.id.radioButton);
            }else if(answers[position].equals("B")) {
                holder.radioGroup.check(R.id.radioButton2);
            }else if(answers[position].equals("C")) {
                holder.radioGroup.check(R.id.radioButton3);
            }else if(answers[position].equals("D")) {
                holder.radioGroup.check(R.id.radioButton4);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private View overlay;
            private  TextView questionText;
            private RadioGroup radioGroup;
            private RadioButton r1,r2,r3,r4,r5;

            ViewHolder(View itemView) {
                super(itemView);
                questionText =  itemView.findViewById(R.id.questionTextView);
                radioGroup=itemView.findViewById(R.id.radioGroup);
                r1=itemView.findViewById(R.id.radioButton);
                r2=itemView.findViewById(R.id.radioButton2);
                r3=itemView.findViewById(R.id.radioButton3);
                r4=itemView.findViewById(R.id.radioButton4);
                r5 = itemView.findViewById(R.id.radioButton5);
            }

            public void setOverlayColor(@ColorInt int color) {
                overlay.setBackgroundColor(color);
            }

            public void unCheck() {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(radioGroup.getCheckedRadioButtonId() == R.id.radioButton) {
                    r1.setChecked(true);
                }
                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButton2) {
                    r2.setChecked(true);
                }
                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButton3) {
                    r3.setChecked(true);
                }
                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButton4) {
                    r4.setChecked(true);
                }
                else if(radioGroup.getCheckedRadioButtonId() ==R.id.radioButton5) {
                    r5.setChecked(true);
                }
            }
        }
    }

    class popGridAdapter extends BaseAdapter {
        Context mContext;
        popGridAdapter(Context context){
            mContext=context;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View convertView;
            if(view==null){
                convertView=new Button(mContext);
            }else convertView=view;
            if(answers[i]==null)
                (convertView).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            else
                (convertView).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

            ((Button)convertView).setText(""+(i+1));

            (convertView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //setNextPrevButton(i);
                    scrollView.smoothScrollToPosition(i);
                }
            });
            return convertView;
        }
    }

    public void slideUp(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(500);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        view.startAnimation(animate);

    }

    @Override
    protected void onDestroy() {
        submit();
        super.onDestroy();
    }



    private class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

private  TestResultOnClick test;
       private List<Boolean> tmp;

        public Adapter(int layoutResId,List<String> data,List<Boolean> tmp,   TestResultOnClick test) {
            super(layoutResId, data);
            this.tmp = tmp;
            this.test = test;
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, String s) {


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    test.onClicks(holder.getPosition());
                }
            });

            ((CheckedTextView)holder.getView(R.id.text_item_result)).setText(s);

            if(tmp.get(holder.getPosition())){

                ((CheckedTextView)holder.getView(R.id.text_item_result)).setTextColor(getResources().getColor(R.color.green));
            }else {

                ((CheckedTextView)holder.getView(R.id.text_item_result)).setTextColor(getResources().getColor(R.color.red));
            }
        }

    }



}
