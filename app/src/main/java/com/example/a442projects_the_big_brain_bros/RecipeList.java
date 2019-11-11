package com.example.a442projects_the_big_brain_bros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RecipeList extends AppCompatActivity {

    public ArrayList<String> dishes;
    private ArrayAdapter<String> adapter;
    public static final String apiKey = "2b590499522c4ac0a5cb4db5ef61b3bb";
    private static JsonArrayRequest objectRequest;
    public static ArrayList jsonText;
    public static ArrayList<String> instruction = new ArrayList<>();
    public static String title;
    public static ArrayList<String> ingredientList = new ArrayList<>();
    public ListView listv;
    public static final String HISTORY_FILE_NAME = "Recipe_History.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        update_view();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listv = (ListView) findViewById(R.id.listv);
        ListViewAdapter adapter = new ListViewAdapter(this, SearchActivity.titleList, SearchActivity.recipeIcons);
        listv.setAdapter(adapter);


    }
    //Starts the new activity RecipeActivity to display the recipe details.
    private void start_recipe_activity(JSONArray x){

        Intent intent = new Intent (this, RecipeActivity.class);
        startActivity(intent);
    }

    //This method calls spoonacular api with the recipe title id to get instructions in the form of a JSONArray.
    //Parses through the JSON to get the Recipe title, Recipe Ingredients, and Recipe Instructions.  After parsing the JSONArray
    //It calls "startRecipeActivity" and passes in the parsed JSONarray.
    private void json_request(int id){
        String URL = "https://api.spoonacular.com/recipes/" + id + "/analyzedInstructions" + "?apiKey=" + apiKey;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        objectRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            ingredientList.clear(); //FIXED DUPLICATE INGREDIENT LIST AND STEPS BUG
                            instruction.clear(); //FIXED DUPLICATE INGREDIENT LIST AND STEPS BUG
                            JSONObject json = response.getJSONObject(0);
                            JSONArray jArray = (JSONArray) json.getJSONArray("steps");
                            if (jArray != null) {
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject steps = (JSONObject) jArray.get(i);
                                    String step = steps.getString("step");
                                    JSONArray jsonIngredient = (JSONArray) steps.getJSONArray("ingredients");
                                    if (jsonIngredient != null){
                                        for (int j = 0; j < jsonIngredient.length(); j++){
                                            JSONObject ingredient = (JSONObject) jsonIngredient.get(j);
                                            String ingredientName = ingredient.getString("name");
                                            if (!ingredientList.contains(ingredientName))
                                                ingredientList.add(ingredientName);
                                        }
                                    }
                                    instruction.add(step);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        start_recipe_activity(response);
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Log.e("Rest Response", message);
            }

        });
        requestQueue.add(objectRequest);
    }

    //Updates listview to display titles of recipes.  Listview onClick calls the method jsonRequest and passes in the id# of the recipe.
    private void update_view() {
        ListView listView = (ListView) findViewById(R.id.listv);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SearchActivity.titleList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                title = SearchActivity.titleList.get(i);
                int id = SearchActivity.recipeName.get(title);
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(HISTORY_FILE_NAME, MODE_APPEND);

                    title = title + "\n";
                    fos.write(title.getBytes());

                    //Print File Save Feedback
                    Toast.makeText(getApplicationContext(), "Saved to" + getFilesDir() + "/" + HISTORY_FILE_NAME, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
                json_request(id);
            }
        });
    }

}




