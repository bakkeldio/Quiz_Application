package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private static RecyclerView.Adapter adapter;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private static RecyclerView recyclerView;
    static View.OnClickListener onClickListener;
    RecyclerView.LayoutManager layoutManager;
    private static ArrayList<DataModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        onClickListener = new MyOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupDrawerContent(navigationView);
        //recyclerView

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i=0; i<MyData.categories.length; i++){
            data.add(new DataModel(MyData.categories[i], MyData.id[i],MyData.drawableArray[i]));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

    }
    private static class MyOnClickListener implements View.OnClickListener{
        private final Context context;
        MyOnClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeCard(v);
        }
        private void removeCard(View view){
            int selectedItemPosition = recyclerView.getChildAdapterPosition(view);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            assert viewHolder != null;
            TextView textViewName
                    =  viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.categories.length; i++) {
                if (selectedName.equals(MyData.categories[i])) {
                    selectedItemId = MyData.id[i];
                }
            }
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }

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
