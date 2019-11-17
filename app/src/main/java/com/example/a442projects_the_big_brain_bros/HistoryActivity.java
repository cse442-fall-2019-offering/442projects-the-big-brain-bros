package com.example.a442projects_the_big_brain_bros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import java.util.HashMap;

public class HistoryActivity extends Activity {
    public ListView listv;
    public static ArrayList<String> titleList = new ArrayList<>();
    public static HashMap<String, ArrayList<String>> recipeInfo = new HashMap<>();
    private static JsonArrayRequest objectRequest;
    public static ArrayList<String> instruction = new ArrayList<>();
    public static ArrayList<String> ingredientList = new ArrayList<>();
    public static String title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        onRecipeClick();
        listv = (ListView) findViewById(R.id.listv);
        readHistory();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTitleList());
        listv.setAdapter(arrayAdapter);

    }

    public ArrayList<String> getTitleList() {
        titleList.clear();
        for(String key : recipeInfo.keySet()){
            titleList.add(key);
        }
        return titleList;
    }

    private void onRecipeClick(){
        ListView listView = (ListView) findViewById(R.id.listv);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SearchActivity.titleList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                title = titleList.get(i);
                int id = Integer.parseInt(recipeInfo.get(title).get(0));
                String recipeIcon = recipeInfo.get(title).get(1);
                json_request(id);
            }
        });
    }

    //Starts the new activity RecipeActivity to display the recipe details.
    private void start_recipe_activity(JSONArray x){

        Intent intent = new Intent (this, RecipeActivity.class);
        startActivity(intent);
    }

    private void json_request(int id){
        String URL = "https://api.spoonacular.com/recipes/" + id + "/analyzedInstructions" + "?apiKey=" + MainActivity.apiKey;
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

    public void readHistory(){
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
                info.add(result[1]);
                info.add(result[2]);
                recipeInfo.put(result[0],info);
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
}
