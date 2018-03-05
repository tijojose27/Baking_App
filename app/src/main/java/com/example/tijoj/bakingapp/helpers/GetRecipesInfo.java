package com.example.tijoj.bakingapp.helpers;

import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.data.RecipesIngredients;
import com.example.tijoj.bakingapp.data.RecipesSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tijoj on 2/21/2018.
 */

public class GetRecipesInfo {

    //HELPER METHOD AND CLASSES

    private static ArrayList<Recipes> currRecipes;


    public static ArrayList<Recipes> recipesToArray(String json){

        currRecipes = new ArrayList<>();

        JSONArray reader = null;

        try {
            reader = new JSONArray(json);

            for(int i=0; i<reader.length();i++) {
                JSONObject object = reader.getJSONObject(i);

                int id = object.getInt("id");
                String name = object.getString("name");

                JSONArray ingredients = object.getJSONArray("ingredients");

                ArrayList<RecipesIngredients> ingredientsArrayList = new ArrayList<>();

                for(int j=0; j<ingredients.length();j++){
                    JSONObject obj = ingredients.getJSONObject(j);
                    Double quantity = obj.getDouble("quantity");
                    String measure = obj.getString("measure");
                    String ingredient = obj.getString("ingredient");

                    ingredientsArrayList.add(new RecipesIngredients(quantity, measure, ingredient));
                }

                JSONArray steps = object.getJSONArray("steps");
                ArrayList<RecipesSteps> recipesStepsArrayList = new ArrayList<>();

                for(int k=0;k<steps.length();k++){
                    JSONObject obj = steps.getJSONObject(k);
                    int stepsId = obj.getInt("id");
                    String shortDesc = obj.getString("shortDescription");
                    String desc = obj.getString("description");
                    String videoUrl = obj.getString("videoURL");
                    String thumbnail = obj.getString("thumbnailURL");

                    recipesStepsArrayList.add(new RecipesSteps(stepsId, shortDesc, desc, videoUrl, thumbnail));
                }

                int servings = object.getInt("servings");
                String image = object.getString("image");

                Recipes recipes = new Recipes(id, name, ingredientsArrayList, recipesStepsArrayList, servings, image);

                currRecipes.add(recipes);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return currRecipes;

    }

}