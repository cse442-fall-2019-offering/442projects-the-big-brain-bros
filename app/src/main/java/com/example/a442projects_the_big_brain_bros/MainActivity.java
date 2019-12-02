package com.example.a442projects_the_big_brain_bros;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.example.a442projects_the_big_brain_bros.ui.send.LoginFragment;
import com.example.a442projects_the_big_brain_bros.ui.send.RegisterFragment;
import com.example.a442projects_the_big_brain_bros.ui.send.SendFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    public static final String HISTORY_FILE_NAME = "Recipe_History.txt";
    public static final String FAVORITE_RECIEPE_FILE_NAME = "FAVORITE_RECIPIES.txt";
    public static final String apiKey = "d1e18c74e2b14ae58d071e26a1a140cf";

    public static ArrayList<ArrayList<String>> recipeInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.nav_login:
                        getSupportFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();
                    case R.id.nav_gallery:
                        openFavorites();

                        break;
                    case R.id.nav_signup:
                        getSupportFragmentManager().beginTransaction().replace(R.id.registerlayout, new RegisterFragment()).commit();

                        break;
                    case R.id.nav_slideshow:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HistoryFragment()).commit();
                        open();
                        break;
                }
                return true;
            }
        });
    }
    public void openFavorites(){
        Intent intent = new Intent (this, FavoriteActivity.class);
        startActivity(intent);
    }
    public void open(){
        readHistory();
        Collections.reverse(recipeInfo);
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void readHistory(){
        recipeInfo.clear();
        ArrayList<String> title = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput(MainActivity.HISTORY_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");
                String[] result = text.split(", ");
                ArrayList<String> info = new ArrayList<>();
                info.add(result[0]);
                info.add(result[1]);
                info.add(result[2]);
                recipeInfo.add(info);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return recipeInfo;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_login:
//                getSupportFragmentManager().beginTransaction().replace(R.id.login_layout, new LoginFragment()).commit();

                break;
            case R.id.nav_signup:
//                getSupportFragmentManager().beginTransaction().replace(R.id.registerlayout, new RegisterFragment()).commit();

                break;
            case R.id.nav_slideshow:
                break;
        }
        return true;
    }

    public void on_search_select(View view){
        Intent intent= new Intent(this, SearchActivity.class);
        startActivity(intent);
//        onSearchRequested();
    }

    // Method for the Login button when clicked, this method is invoked (See button layout in fragment_home.xml
    public void on_login_click(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
