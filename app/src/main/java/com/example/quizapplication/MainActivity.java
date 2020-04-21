package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quizapplication.fragments.AboutQuizFragment;
import com.example.quizapplication.fragments.Logout;
import com.example.quizapplication.fragments.ProfileFragment;
import com.example.quizapplication.fragments.Quizzes;
import com.example.quizapplication.fragments.RankFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    public static View.OnClickListener onClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Quizzes quizzes = new Quizzes();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, quizzes).commit();
        setupDrawerContent(navigationView);

        View view = navigationView.inflateHeaderView(R.layout.header);
        ImageView imageView1 = view.findViewById(R.id.photoImageView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child("images/"+user.getUid());
        Glide.with(this)
                .load(storageReference)
                .into(imageView1);

        //Profile Fragment

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

    }


    public void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.Profile:
                fragment = new ProfileFragment();
                break;
            case R.id.aboutUs:
                fragment = new AboutQuizFragment();
                break;
            case R.id.Rank:
                fragment = new RankFragment();
                break;
            case R.id.Quizzes:
                fragment = new Quizzes();
                break;
            case R.id.yesButton:
                fragment = new Logout();

                break;

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        assert fragment != null;
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

}
