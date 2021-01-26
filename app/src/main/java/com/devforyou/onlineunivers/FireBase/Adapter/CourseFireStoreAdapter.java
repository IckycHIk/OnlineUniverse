package com.devforyou.onlineunivers.FireBase.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devforyou.onlineunivers.FireBase.Model.CourseModelF;
import com.devforyou.onlineunivers.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.stfalcon.chatkit.commons.ImageLoader;

public class CourseFireStoreAdapter extends FirestoreRecyclerAdapter<CourseModelF, CourseFireStoreAdapter.CourseDataViewHolder> {
    ImageLoader  imageLoader;
    OnClickCourseRecyclerViewItem onClickCourseRecyclerViewItem;

    public CourseFireStoreAdapter(@NonNull FirestoreRecyclerOptions<CourseModelF> options, ImageLoader  imageLoader,OnClickCourseRecyclerViewItem onClick) {
        super(options);

        this.imageLoader = imageLoader;
        this.onClickCourseRecyclerViewItem = onClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull CourseDataViewHolder holder, int position, @NonNull CourseModelF model) {
        holder.title.setText(model.getTitle());
        holder.rating.setText("Rating: " +model.getRating()+"");

        holder.description.setText(model.getDescription());
        imageLoader.loadImage(holder.img,model.getImg_url(),null);
    }

    @NonNull
    @Override
    public CourseDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card,parent,false);
        return new CourseDataViewHolder(view);
    }

   public class CourseDataViewHolder extends  RecyclerView.ViewHolder {
        public TextView title;
        public TextView rating;
        public ImageView img;
        public TextView description;
        public View.OnClickListener onClickListener;

        public CourseDataViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.course_card_title);
            rating = itemView.findViewById(R.id.course_card_rating);
            img = itemView.findViewById(R.id.course_card_img);
            description =itemView.findViewById(R.id.course_card_description);

itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickCourseRecyclerViewItem.onItemClick(getItem(getAdapterPosition()),"sss");
                }
            });
        }
    }

    public interface OnClickCourseRecyclerViewItem{

        void onItemClick(CourseModelF courseModelF,String id);
    }
}

