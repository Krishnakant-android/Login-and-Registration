package com.krishnakantkumar.loginwithroom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
 
public class UsersListActivity extends AppCompatActivity {
 
    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private AppDatabase appDatabase;
 
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        initViews();
        initObjects();
 
    }
 
    /**
     * This method is to initialize views
     */
    private void initViews() {
        appDatabase = AppDatabase.getAppDatabase(activity);
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }
 
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();

 
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);


        getDataFromSQLite();
    }
 
    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();

                listUsers = appDatabase.userDao().getAll();

                System.out.println("List size>>"+listUsers.size());

               // listUsers.addAll(databaseHelper.getAllUser());
 
                return null;
            }
 
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);
                recyclerViewUsers.setAdapter(usersRecyclerAdapter);
            }
        }.execute();
    }
}