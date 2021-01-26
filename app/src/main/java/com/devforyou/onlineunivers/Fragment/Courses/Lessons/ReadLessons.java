package com.devforyou.onlineunivers.Fragment.Courses.Lessons;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.GridSpanSizeLookup;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.devforyou.onlineunivers.Activity.MainActivity;
import com.devforyou.onlineunivers.Adapter.LessonsMaterialAdapter;
import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.FireBase.Model.LessonsModelF;
import com.devforyou.onlineunivers.R;
import com.devforyou.onlineunivers.RoomDb.Model.ChatModel;
import com.devforyou.onlineunivers.RoomDb.Model.LessonModel;
import com.devforyou.onlineunivers.entity.Lesson;
import com.devforyou.onlineunivers.entity.QuickMultipleEntity;
import com.devforyou.onlineunivers.entity.message;
import com.fourlastor.dante.html.FlavoredHtml;
import com.fourlastor.dante.html.ImgLoader;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.ViewHolder;
import pl.droidsonroids.jspoon.HtmlAdapter;
import pl.droidsonroids.jspoon.Jspoon;
import pl.droidsonroids.jspoon.annotation.Selector;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ReadLessons extends Fragment {

    private Context context;
    private Activity activity;
    private LessonsModelF lessons;

private RecyclerView mRecyclerView;

    private LessonsMaterialAdapter mAnimationAdapter;
    private ParallaxScrollView parallax;
    private String  id;
private FirebaseFirestore firebaseFirestore;

private List<QuickMultipleEntity> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lesons_material, container, false);
        context = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            id  = bundle.getString("id");


        }else{
/*
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(id,fragment)
                    .commit();
                    */

        }





        firebaseFirestore =FirebaseFirestore.getInstance();

        DocumentReference docRef =firebaseFirestore.collection("Lessons").document(id+"");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot snapshot,
                                @androidx.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    // Log.d(TAG, "DocumentSnapshot data: " + snapshot.getData());

                   lessons  = snapshot.toObject(LessonsModelF.class);

                    mAnimationAdapter.setNewData(getData(lessons));

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

       // Toast.makeText(context,"Some Element :"+lessons.HtmlText,Toast.LENGTH_LONG ).show();


        mAnimationAdapter = new LessonsMaterialAdapter(data,lessons);
        mAnimationAdapter.setAnimationEnable(true);

        mAnimationAdapter.setGridSpanSizeLookup((gridLayoutManager, viewType, position) -> data.get(position).getSpanSize());


        mRecyclerView = view.findViewById(R.id.lessons_material_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mRecyclerView.setAdapter(mAnimationAdapter);

       // ParallaxToRecycler(mRecyclerView,parallax);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){


            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
       });


return view;
    }




private List<QuickMultipleEntity> getData(LessonsModelF  lessons){
    List<QuickMultipleEntity> data = new ArrayList<>();



    ArrayList<String> img = new ArrayList<>();
    ArrayList<String> text = new ArrayList<>();

    String[] subStr;
    String delimeter = "<img[^>]+>"; // Разделитель
    subStr = lessons.getHtml_text().split(delimeter);

    Document doc = Jsoup.parse(lessons.getHtml_text());
    Elements newsHeadlines = doc.select("img");
    for (Element img_src : newsHeadlines) {

        img.add(img_src.attr("src"));
      // Toast.makeText(context, headline.attr("src"),Toast.LENGTH_LONG).show();

    }

    StringBuilder ss = new StringBuilder();
    text.addAll(Arrays.asList(subStr));

   if(text.size()>img.size()){

       for(int i =0;i<img.size();i++){

       data.add(new QuickMultipleEntity(QuickMultipleEntity.TEXT,QuickMultipleEntity.TEXT_SPAN_SIZE,text.get(i)));
       data.add(new QuickMultipleEntity(QuickMultipleEntity.IMG,img.get(i)));

       }
    //   data.add(new QuickMultipleEntity(QuickMultipleEntity.VIDEO,"some","https:\\www.youtube.com/watch?v=M6sA8fvMCuA"));
       data.add(new QuickMultipleEntity(QuickMultipleEntity.TEXT,QuickMultipleEntity.TEXT_SPAN_SIZE,text.get(img.size())));
   }else if(text.size()<img.size()){

       for(int i =0;i<text.size();i++){

           data.add(new QuickMultipleEntity(QuickMultipleEntity.TEXT,QuickMultipleEntity.TEXT_SPAN_SIZE,text.get(i)));
           data.add(new QuickMultipleEntity(QuickMultipleEntity.IMG,img.get(i)));
       }
     //  data.add(new QuickMultipleEntity(QuickMultipleEntity.VIDEO,"some","https:\\www.youtube.com/watch?v=M6sA8fvMCuA"));
       data.add(new QuickMultipleEntity(QuickMultipleEntity.IMG,img.get(text.size())));
   }else {
       for(int i =0;i<text.size();i++){

           data.add(new QuickMultipleEntity(QuickMultipleEntity.TEXT,QuickMultipleEntity.TEXT_SPAN_SIZE,text.get(i)));
           data.add(new QuickMultipleEntity(QuickMultipleEntity.IMG,img.get(i)));
       }
   }

   // data.add(new QuickMultipleEntity(QuickMultipleEntity.VIDEO,"some","https:\\www.youtube.com/watch?v=M6sA8fvMCuA"));

    return data;
}


}
