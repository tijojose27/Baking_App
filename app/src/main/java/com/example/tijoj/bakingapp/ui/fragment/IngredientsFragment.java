package com.example.tijoj.bakingapp.ui.fragment;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.Widgets.BakingAppWidget;
import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.data.RecipesIngredients;

import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    public String[] ingreds;
    public FloatingActionButton floatingActionButton;
    public String recipeName;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ingreds = getArguments().getStringArray(RecipesIngredients.INGREDIENTS_KEY);
        recipeName = getArguments().getString(Recipes.RECIPE_NAME);

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ListView listView = view.findViewById(R.id.ingredient_list);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, ingreds);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Recipes.RECIPE_NAME, recipeName);
                StringBuilder stringBuilder = new StringBuilder();
                for(int i =0; i<ingreds.length;i++){
                    stringBuilder.append(ingreds[i]).append(",");
                }
                editor.putString(Recipes.RECIPE_INGREDS, stringBuilder.toString());
                editor.apply();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                Bundle bundle = new Bundle();
                int appWidgetId = bundle.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                BakingAppWidget.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, recipeName, ingreds);


            }
        });

        listView.setAdapter(adapter);

        return view;
    }


}
