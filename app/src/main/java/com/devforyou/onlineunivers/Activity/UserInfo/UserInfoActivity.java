package com.devforyou.onlineunivers.Activity.UserInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.devforyou.onlineunivers.Activity.AuthGoogleActivity;
import com.devforyou.onlineunivers.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    TabLayout mtablayout;
    ViewPager mviewpager;
 private FloatingActionButton sign_out;
    private Context context;
    private NiftyDialogBuilder dialogBuilder;
    private FirebaseAuth mAuth;
    private CircleImageView img_user;
    private FirebaseFirestore firebaseFirestore;

    private TextView user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_user_info);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorcoltitle));
user_name =findViewById(R.id.user_name);

img_user =findViewById(R.id.user_img);
firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dialogBuilder=NiftyDialogBuilder.getInstance(this);
sign_out = findViewById(R.id.sign_out_floating_button);
sign_out.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {

        dialogBuilder
                .withDialogColor(getColor(R.color.blue))
                .withDividerColor(getColor(R.color.navigation_main))
                .withTitle("Выйти из учетной записи?")
                .withButton1Text("Да")
                .withButton2Text("Нет")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signOut();
                    }
                }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        })
                .show();

    }
});

       context = this;
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.navigation_secondary));
        mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.navigation_main));
//        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
        int px = DipToPixels(getApplicationContext(), 60);
        mCollapsingToolbarLayout.setExpandedTitleMargin(0, px, 0, 0);
//        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.BOTTOM);

        BlurredLayout layout = (BlurredLayout) findViewById(R.id.blured);
        layout.setBlurRadius(0, 50);
        mtablayout = (TabLayout) findViewById(R.id.tabs);
        mviewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mviewpager);
        mtablayout.setupWithViewPager(mviewpager);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



      user_name.setText(currentUser.getDisplayName());
      Picasso.get().load(currentUser.getPhotoUrl()).into(img_user);

    }


    private void signOut() {

        Intent intent = new Intent(context, AuthGoogleActivity.class);

        intent.putExtra("key" , "sign_out");

        startActivity(intent);
    }

    public int DipToPixels(Context context, int dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());


                adapter.addFragment(new UserInfoFragement2(), "Мои Тесты");
                adapter.addFragment(new UserInfoFragement(), "Мои Курсы");


                adapter.addFragment(new UserInfoFragementTuter(), "Тесты учеников");



       viewPager.setAdapter(adapter);
    }

    @Override
    public void finish() {

        Intent intent = new Intent();
        intent.putExtra("delete",true);
        setResult(RESULT_OK,intent);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("delete",true);
        setResult(RESULT_OK,intent);
        finish();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
