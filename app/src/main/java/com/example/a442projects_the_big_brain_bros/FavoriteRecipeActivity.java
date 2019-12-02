package com.example.a442projects_the_big_brain_bros;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FavoriteRecipeActivity extends AppCompatActivity {
    boolean mIsSaved = true;
    //    public TextView textView = (TextView) findViewById(R.id.textV);
    @Override

    //onCreate passed in the parsed JSONarray.  The details are then displayed onto the new activity(Recipe title, Recipe Ingredients, Recipe instructions).
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe);
        TextView textView = new TextView(this);
        TextView title = (TextView) findViewById(R.id.textT);
        title.setText(FavoriteActivity.title + "\n");
        String text = "";
        TextView ingredients = (TextView) findViewById(R.id.textIL);
        for(int i = 0; i < FavoriteActivity.ingredientList.size(); i++){
            text += FavoriteActivity.ingredientList.get(i) + "\n";
        }
        ingredients.setText(text + "\n");
        TextView instruction = (TextView) findViewById(R.id.textSL);
        text = "";
        for(int i = 0; i < FavoriteActivity.instruction.size(); i++){
            text += "STEP " + (i+1) + ". \n\n" +  FavoriteActivity.instruction.get(i) + "\n\n";
        }
        instruction.setText(text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.recipe_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        switch (item.getItemId()) {
            case R.id.save_button:
                if (mIsSaved) { //you could modify this to check the icon/text of the menu item

                    String title;
                    FileOutputStream fos = null;
                    try{
                        fos = openFileOutput("FAVORITE_RECIPES.txt", MODE_APPEND);
                        title = FavoriteActivity.title;
                        fos.write(title.getBytes());  //write the text to the local storage

                        fos = null;

                        Toast.makeText(getApplicationContext(), "Saved to" + getFilesDir() + "/" + "FAVORITE_RECIPES", Toast.LENGTH_LONG).show();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if (fos != null){
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    mIsSaved = false;
                } else {

                    ArrayList<String> title = new ArrayList<>();
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput("FAVORITE_RECIPES.txt");
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        StringBuilder sb = new StringBuilder();
                        String text;
                        while ((text = br.readLine()) != null){
                            sb.append(text).append("\n");
                            if(!(text.equals(FavoriteActivity.title)))
                                title.add(text);
                        }

                        isr = null;
                        br = null;
                        sb = null;
                        fis = null;


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



                    FileOutputStream fos = null;
                    try{


                        fos = openFileOutput("FAVORITE_RECIPES.txt", MODE_APPEND);
                        String joinedTitle = String.join("\n", title);
                        fos.write(joinedTitle.getBytes());  //write the text to the local storage
                        fos = null;
                        Toast.makeText(getApplicationContext(), "Unsaved to" + getFilesDir() + "/" + "FAVORITE_RECIPES", Toast.LENGTH_LONG).show();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if (fos != null){
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    mIsSaved = true;

                }
                invalidateOptionsMenu(); //cause a redraw
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mIsSaved) {
            //in production you'd probably be better off keeping a reference to the item
            menu.findItem(R.id.save_button)
                    .setIcon(R.drawable.white_border_heart)
                    .setTitle("Save");
        } else {
            menu.findItem(R.id.save_button)

                    .setIcon(R.drawable.white_heart)
                    .setTitle("Unsave");
        }
        return super.onPrepareOptionsMenu(menu);
    }
}



