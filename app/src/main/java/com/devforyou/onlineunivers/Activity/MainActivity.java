package com.devforyou.onlineunivers.Activity;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.devforyou.onlineunivers.Activity.UserInfo.UserInfoActivity;
import com.devforyou.onlineunivers.Adapter.MyCourseNavAdapter;
import com.devforyou.onlineunivers.Adapter.NavigationAdapter;
import com.devforyou.onlineunivers.FireBase.Adapter.CourseFireStoreAdapter;
import com.devforyou.onlineunivers.FireBase.Firebase;

import com.devforyou.onlineunivers.FireBase.Model.ChatRoomModelF;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.LessonsModelF;
import com.devforyou.onlineunivers.FireBase.Model.TestModelF;
import com.devforyou.onlineunivers.Fragment.ChatFragment;
import com.devforyou.onlineunivers.Fragment.Courses.Lessons.ReadLessons;
import com.devforyou.onlineunivers.Fragment.MainFragment;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.RoomDb.AppDatabase;

import com.devforyou.onlineunivers.RoomDb.Dao.ChatDao;
import com.devforyou.onlineunivers.RoomDb.Dao.LessonsDao;
import com.devforyou.onlineunivers.RoomDb.Model.ChatModel;
import com.devforyou.onlineunivers.RoomDb.Model.CourseModel;
import com.devforyou.onlineunivers.RoomDb.Model.LessonModel;
import com.devforyou.onlineunivers.RoomDb.di.AppModule;
import com.devforyou.onlineunivers.RoomDb.di.Executor;
import com.devforyou.onlineunivers.RoomDb.di.RoomModule;
import com.devforyou.onlineunivers.RoomDb.di.DaggerAppComponent;
import com.devforyou.onlineunivers.RoomDb.repository.ChatRepository;
import com.devforyou.onlineunivers.RoomDb.repository.CourseRepository;
import com.devforyou.onlineunivers.RoomDb.repository.LessonsRepository;
import com.devforyou.onlineunivers.entity.Course;
import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.NavigationEntity;
import com.devforyou.onlineunivers.entity.NavigationEntitySecondNode;
import com.devforyou.onlineunivers.entity.message;
import com.devforyou.onlineunivers.interf.OnClickAddSecondProvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import com.devforyou.onlineunivers.interf.SecondProvideInClick;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements SecondProvideInClick , MyCourseNavAdapter.OnClickSwitchCourse, OnClickAddSecondProvider {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationAdapter adapter;
    private Context context;
    private RecyclerView recyclerView;
    private ImageButton humb_button;
    private AppDatabase db;
    private Application main;
    private ChatDao chatDao;
    private LessonsDao lessonsDao;
    private ChatModel model;
    private LessonModel lessonModel;
    private CircleImageView addNewCourse;
    private FirebaseFirestore firebaseFirestore;

    private CircleImageView img_profile;
    private FirebaseUser auth_user;
    private ListenerRegistration lessonsRef;
    private ListenerRegistration testRef;
    private ListenerRegistration  roomRef;
    private RecyclerView my_course_recycler_view;
    private FirestoreRecyclerAdapter adapters;
    private     ArrayList<CourseModelF> tmpArrayList;
    private  MyCourseNavAdapter mMyCourseAdapter;
    private String use_course_id="";
    private  String material_ids ="";
private LinearLayout choose_course;
private TextView chose_course;
private  ArrayList<LessonsModelF> lessonsModelves;
private    ArrayList<TestModelF> testModels;
    private    ArrayList<ChatRoomModelF> chatModels;
private String user_tutor_id="";
private   Firebase f;
    final int TEST_ATTEMPT = 3;

    @Inject
    AppDatabase mAppDatabase;

    @Inject
    public LessonsRepository lessonsRepository;

    @Inject
    public ChatRepository chatRepository;

    @Inject
    public CourseRepository courseRepository;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);


        img_profile = findViewById(R.id.img_profile);
        context = this;
        main = getApplication();
        model = ViewModelProviders.of(this).get(ChatModel.class);
        lessonModel = ViewModelProviders.of(this).get(LessonModel.class);
        humb_button = findViewById(R.id.humburg_button);
        drawerLayout = findViewById(R.id.drawer_layout);

        my_course_recycler_view = findViewById(R.id.my_course_recycler_view);
        my_course_recycler_view.setHasFixedSize(true);
        my_course_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        navigationView = findViewById(R.id.nav_view);
        choose_course= findViewById(R.id.choose_course);
chose_course =findViewById(R.id.name_chose_course);

        Bundle bundle = getIntent().getExtras();

      if(bundle!=null) {
          boolean add = bundle.getBoolean("add");


          if (add) {

              tmpArrayList.clear();
              initMyCourse();
          }
      }
        generateDb();
        // AddSome();
        getOne();

        initUI();
        navItemSU();
        initOpenCourse();

        initFragment(new MainFragment());

        firebaseFirestore = FirebaseFirestore.getInstance();


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        auth_user = firebaseAuth.getCurrentUser();


        // img_profile.setImageURI(auth_user.getPhotoUrl());

        Picasso.get().load(auth_user.getPhotoUrl()).into(img_profile);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(context, UserInfoActivity.class),2);
            }
        });


        initMyCourse();
         f = new Firebase();
        f.initFireStore(firebaseFirestore);
        f.addUser(auth_user, context);

        // Toast.makeText(context,courseFireBase.HtmlText,Toast.LENGTH_LONG).show();

    }


    private void initOpenCourse() {

        addNewCourse = findViewById(R.id.searchCourse);
        addNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFragment(new MainFragment());

            }
        });

    }


    private void destroyDb() {
        chatDao = mAppDatabase.getChatDao();

        chatDao.nukeTable();
    }


    private void generateDb() {

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);
    }

    private void AddSome() {
      /*  chatDao = mAppDatabase.getChatDao();
        message ms = new message();
        ms.text_message = "SSSSSSSSS";
        Executor.IOThread(() -> chatDao.insert(ms));
*/
        lessonsDao = mAppDatabase.getLesonsDao();


        Lesson ls = new Lesson();
        ls.Description = "Как стать лучше";
        Executor.IOThread(() -> lessonsDao.insert(ls));


    }


    private void getOne() {

        chatRepository.findAll().observe(this, new Observer<List<message>>() {
            @Override
            public void onChanged(List<message> messages) {

                //Toast.makeText(context,messages.get(0).text_message,Toast.LENGTH_SHORT).show();

                model.selectList(messages);

            }
        });


    }


    private void initFragment(Fragment fragment) {

        drawerLayout.closeDrawers();

        int id = R.id.fragment_container;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(id, fragment)
                .commit();
    }


    private void initUI() {


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        humb_button.setOnClickListener(v -> {

            drawerLayout.openDrawer(GravityCompat.START);

        });


        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();

            return true;
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void navItemSU() {

        adapter = new NavigationAdapter(this,this);

        recyclerView =
                navigationView.findViewById(R.id.navigation_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);


        initLessonsItem();

        // 模拟新增node
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 2000);


    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void initLessonsItem(){

        lessonsModelves = new ArrayList<>();
        testModels = new ArrayList<>();
        chatModels=new ArrayList<>();


        firebaseFirestore = FirebaseFirestore.getInstance();

        Query lesson = firebaseFirestore.collection("Lessons").whereEqualTo("course_id", use_course_id);

        lessonsRef =  lesson.addSnapshotListener((queryDocumentSnapshots, e) -> {
            lessonsModelves.clear();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                lessonsModelves.add(document.toObject(LessonsModelF.class));

            }
            Collections.sort(lessonsModelves, (a, b) -> a.getTitle().compareTo(b.getTitle()));

            adapter.setNewData(getEntity(lessonsModelves,testModels, chatModels));
            adapter.notifyDataSetChanged();
        });


        Query query = firebaseFirestore.collection("Tests").whereEqualTo("course_id", use_course_id);

      testRef =   query
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                  @Override
                  public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                      testModels.clear();

                      for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                          testModels.add(document.toObject(TestModelF.class));

                      }
                      Collections.sort(testModels, (a, b) -> a.getTitle().compareTo(b.getTitle()));
                      adapter.setNewData(getEntity(lessonsModelves,testModels, chatModels));
                      adapter.notifyDataSetChanged();
                  }
              });
        Query queryRoom = firebaseFirestore.collection("Chat_Room").whereEqualTo("course_id", use_course_id);

        roomRef =   queryRoom
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        chatModels.clear();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                            chatModels.add(document.toObject(ChatRoomModelF.class));

                        }
                        Collections.sort(chatModels, (a, b) -> a.getTitle().compareTo(b.getTitle()));
                        adapter.setNewData(getEntity(lessonsModelves,testModels,chatModels));
                        adapter.notifyDataSetChanged();
                    }
                });



  }

    private List<BaseNode> getEntity(ArrayList<LessonsModelF> lessonsModelF,ArrayList<TestModelF> testModels,ArrayList<ChatRoomModelF> chatModels) {


        List<BaseNode> list = new ArrayList<>();
        List<BaseNode> secondNodeList = new ArrayList<>();
        List<BaseNode> materialSecondNodeList = new ArrayList<>();
        List<BaseNode> thirdNodeList= new ArrayList<>();

        for(int i =0;i<lessonsModelF.size();i++){

            NavigationEntitySecondNode seNode = new NavigationEntitySecondNode(null, lessonsModelF.get(i).getTitle(), lessonsModelF.get(i).getId(),"lesson");
            materialSecondNodeList.add(seNode);

        }
        for(int i =0;i<chatModels.size();i++){

            NavigationEntitySecondNode seNode = new NavigationEntitySecondNode(null, chatModels.get(i).getTitle(), chatModels.get(i).getId(),"chat");
            thirdNodeList.add(seNode);

        }

        for(int i =0;i<testModels.size();i++){
            NavigationEntitySecondNode seNode = new NavigationEntitySecondNode(null, testModels.get(i).getTitle(), testModels.get(i).getId(),"test");
            secondNodeList.add(seNode);
        }


if(user_tutor_id.equals(auth_user.getUid())) {

    NavigationEntity entity = new NavigationEntity(materialSecondNodeList, "Материал", false,0);
    entity.setExpanded(false);
    list.add(entity);
    entity = new NavigationEntity(secondNodeList, "Тесты", true,1);
    entity.setExpanded(false);
    list.add(entity);
    entity = new NavigationEntity(thirdNodeList, "Чат комната", true,2);
    entity.setExpanded(false);
    list.add(entity);

}else {

    NavigationEntity entity = new NavigationEntity(materialSecondNodeList, "Материал", false,0);
    entity.setExpanded(false);
    list.add(entity);
    entity = new NavigationEntity(secondNodeList, "Тесты", false,1);
    entity.setExpanded(false);
    list.add(entity);
    entity = new NavigationEntity(thirdNodeList, "Чат комната", false,2);
    entity.setExpanded(false);
    list.add(entity);
}

        return list;
}

    public void LessonsOpen(String id) {

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        ReadLessons rs = new ReadLessons();

        rs.setArguments(bundle);
        initFragment(rs);

    }

    private void initMyCourse() {

       tmpArrayList = new ArrayList<>();

       firebaseFirestore = FirebaseFirestore.getInstance();
       firebaseFirestore.collection("Users").whereEqualTo("id", auth_user.getUid())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (!document.getString("courses").equals("")) {
                                String[] tmp = document.getString("courses").split(";");

                                // Toast.makeText(context,document.getString("courses"),Toast.LENGTH_SHORT).show();

                                for (int i = 0; i < tmp.length; i++) {

                                    FirebaseFirestore ds = FirebaseFirestore.getInstance();
                                    DocumentReference docRef = ds.collection("Course").document(tmp[i].trim());
                                    docRef.get()
                                            .addOnCompleteListener(taskl -> {


                                                CourseModelF courseModelF = taskl.getResult().toObject(CourseModelF.class);

                                                //  Toast.makeText(context,courseModelF.getDescription(),Toast.LENGTH_SHORT).show();

                                                tmpArrayList.add(courseModelF);
                                                mMyCourseAdapter.setNewData(tmpArrayList);

                                                addToRoomCourse(courseModelF);

                                                mMyCourseAdapter.notifyDataSetChanged();

                                            });
                                }
                            }
                        }
                    }

                });

        //  firebaseFirestore.collection("Course");


        courseRepository.findUse().observe(this, course -> {
            if (course!=null) {
                use_course_id = course.F_id;
                choose_course.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });

        mMyCourseAdapter = new MyCourseNavAdapter(tmpArrayList,courseRepository,use_course_id,this);
        mMyCourseAdapter.setAnimationEnable(true);
        my_course_recycler_view.setAdapter(mMyCourseAdapter);

    }


    private  void  addToRoomCourse(CourseModelF course){


        if(courseRepository.findById(course.getId())==null){

            Course courseModel = new Course();

            courseModel.F_id = course.getId();
            courseModel.title = course.getTitle();
            courseModel.Description = course.getDescription();

            //ourseModel.image = course.getImg_url();
            courseRepository.insert(courseModel);
        }
    }


    @Override
    public void getIdLesson(String id) {

        LessonsOpen(id);
        drawerLayout.closeDrawers();

    }

    @Override
    public void getIdTest(String id) {

        Intent intent = new Intent(context,TestAttemptActivity.class);

        intent.putExtra("test_id",id);
        startActivityForResult(intent,TEST_ATTEMPT);
        drawerLayout.closeDrawers();

    }

    @Override
    public void getIdChatRoom(String id) {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("room_id",id );
        fragment.setArguments(bundle);

        initFragment(fragment);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(data.getBooleanExtra("add",false)){
                tmpArrayList.clear();
                initMyCourse();
            }else if(data.getBooleanExtra("delete",false)){
                tmpArrayList.clear();
                initMyCourse();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(CourseModelF courseModelF) {
        choose_course.setVisibility(View.INVISIBLE);
        use_course_id  = courseModelF.getId();

        user_tutor_id = courseModelF.getUser_id();

        material_ids = courseModelF.getMaterial_ids();
        chose_course.setText(courseModelF.getTitle());
        if(lessonsRef!=null && testRef!=null && roomRef!=null){
            lessonsRef.remove();
            testRef.remove();
            roomRef.remove();
            initLessonsItem();
        }

    }

    @Override
    public void addTest() {

        Intent intent = new Intent(context,Custom_quiz.class);
        intent.putExtra("course_id",use_course_id);
        startActivity(intent);
    }

    @Override
    public void addChat() {

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View promptsView = li.inflate(R.layout.dialog_custom_create_chat, null);
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder.setTitle("Напишите название чата");

        // set dialog_custom.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput =  promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, id) -> {

                      if(!TextUtils.isEmpty(userInput.getText().toString().trim())){

                        f.createRoomChat(use_course_id,userInput.getText().toString().trim());

                          Toast.makeText(context,"Вы создали чат "+userInput.getText().toString().trim(),Toast.LENGTH_LONG).show();


                      }else {
                          userInput.setError("Это поле не может быть пустым");



                      }

                        })
                .setNegativeButton("Закрыть",
                        (dialog, id) -> dialog.cancel());
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }

}


