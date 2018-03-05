package com.example.tijoj.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tijoj on 2/21/2018.
 */

public class Recipes implements Parcelable{

    //SIMPLE POJO FOR RECIPIES WITH IMPLEMENTS PARCELABLES

    public static final String RECIPE_KEY ="CURRENT RECIPIE";
    public static final String RECIPE_POSITION = "RECIPE POSITION";
    public static final String RECIPE_NAME = "RECIPE NAME";
    public static final String RECIPE_INGREDS = "RECIPE INGREDIENTS";

    private int recipeId;
    private String recipeName;
    private ArrayList<RecipesIngredients> recipesIngredients;
    private ArrayList<RecipesSteps> recipesSteps;
    private int servings;
    private String image;

    public Recipes(int recipeId, String recipeName, ArrayList<RecipesIngredients> recipesIngredientsArrayList, ArrayList<RecipesSteps> recipesSteps, int servings, String image) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipesIngredients = recipesIngredientsArrayList;
        this.recipesSteps = recipesSteps;
        this.servings = servings;
        this.image = image;
    }

    protected Recipes(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipesIngredients = in.createTypedArrayList(RecipesIngredients.CREATOR);
        recipesSteps = in.createTypedArrayList(RecipesSteps.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<RecipesIngredients> getRecipesIngredients() {
        return recipesIngredients;
    }

    public ArrayList<RecipesSteps> getRecipesSteps() {
        return recipesSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(recipeId);
        parcel.writeString(recipeName);
        parcel.writeTypedList(recipesIngredients);
        parcel.writeTypedList(recipesSteps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
