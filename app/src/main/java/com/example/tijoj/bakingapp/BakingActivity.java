package com.example.tijoj.bakingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.tijoj.bakingapp.Adapters.RecipeAdapter;
import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.helpers.GetRecipesInfo;
import com.example.tijoj.bakingapp.helpers.NetworkHelper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BakingActivity extends AppCompatActivity {

    public ArrayList<Recipes> recipes;

    public RecyclerView recyclerView;

    public final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);

        recyclerView = findViewById(R.id.recipes_recyclerView);

        NetworkHelper networkHelper = new NetworkHelper(getApplicationContext());

        if (!networkHelper.isConnected()) {
            new AlertDialog.Builder(this).setTitle("No Internet Connection")
                    .setMessage("There is no internet connection please verify device is connected and restart app")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }else {
            getRecipiesWithOkHttp(URL);
        }

    }

    private void getRecipiesWithOkHttp(String url) {

        recipes = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getBaseContext(), "FAILED TO GET INFO", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code "+response);
                }else {
                    final String result = response.body().string();
                    BakingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recipes = GetRecipesInfo.recipesToArray(result);
                            updateUi(recipes);
                        }
                    });
                }

            }
        });
    }

    private void updateUi(ArrayList<Recipes> currRecipes){

        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(new RecipeAdapter(this, currRecipes));
    }
}
