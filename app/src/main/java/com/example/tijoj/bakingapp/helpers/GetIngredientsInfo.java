package com.example.tijoj.bakingapp.helpers;

import com.example.tijoj.bakingapp.data.RecipesIngredients;

import java.util.ArrayList;

/**
 * Created by tijoj on 2/23/2018.
 */

public class GetIngredientsInfo {

    //HELPER CLASS

    public ArrayList<RecipesIngredients> ingredients;

    public String[] stuff;

    //CONSTRUCTOR TAKING IN A REFERENCE TO INGREDIENTS
    public GetIngredientsInfo(ArrayList<RecipesIngredients> currIng) {
        ingredients = currIng;
    }

    //METHOD TO SPIT BACK A STRING ARRAY OF INGREDIENTS TO USE IN LIST VIEW
    public String[] getStuff(){
        int size = ingredients.size();

        stuff = new String[size];

        for(int i =0; i<size;i++){
            String currIng = ingredients.get(i).getIngreditent()+" "+ingredients.get(i).getMeasure()+
                    " "+ingredients.get(i).getQuantity();
            stuff[i]=currIng;
        }
        return stuff;
    }
}
