package com.example.tijoj.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tijoj.bakingapp.R;
import com.example.tijoj.bakingapp.data.Recipes;
import com.example.tijoj.bakingapp.data.RecipesSteps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StepsDetailFragment extends Fragment {


    public int recipeStepPosition;
    public ArrayList<RecipesSteps> currRecipesSteps;
    public TextView stepDetailsTV;
    public Button prevButton;
    public Button nextButton;
    public SimpleExoPlayerView simpleExoPlayerView;
    public SimpleExoPlayer simpleExoPlayer;
    public ImageView stepsImageView;
    public String currDescription;
    public String currVideoUri;
    public String currThumbnailUri;
    public LinearLayout linearLayout;
    public boolean isTwoPane;

    public final String PLAYER_POSITION = "PLAYER POSITION";
    public final String AUTO_PLAY = "AUTO PLAY";
    public final String PREVIOUS_POSITION = "PREVIOUS POSITION";

    public long playerPosition;
    public boolean shouldAutoPlay;
    public int prevPosition;


    public StepsDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PLAYER_POSITION)) {
                playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            } else {
                playerPosition = C.TIME_UNSET;
            }
            if (savedInstanceState.containsKey(AUTO_PLAY)) {
                shouldAutoPlay = savedInstanceState.getBoolean(AUTO_PLAY);
            } else {
                shouldAutoPlay = true;
            }
        } else {
            playerPosition = C.TIME_UNSET;
            shouldAutoPlay = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(PREVIOUS_POSITION)){
                recipeStepPosition = savedInstanceState.getInt(PREVIOUS_POSITION);
            }
        }else {
            recipeStepPosition = getArguments().getInt(Recipes.RECIPE_POSITION);
        }
        isTwoPane = getArguments().getBoolean("LAYOUT", false);
        currRecipesSteps = getArguments().getParcelableArrayList(Recipes.RECIPE_KEY);

        currDescription = currRecipesSteps.get(recipeStepPosition).getDescription();
        currVideoUri = currRecipesSteps.get(recipeStepPosition).getVideoURL();
        currThumbnailUri = currRecipesSteps.get(recipeStepPosition).getThumbnamilUrl();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_detail, container, false);

        if (isTwoPane) {
            linearLayout = view.findViewById(R.id.button_linear_layout);
            linearLayout.setVisibility(View.GONE);
        }

        stepDetailsTV = view.findViewById(R.id.step_instructions);
        simpleExoPlayerView = view.findViewById(R.id.exo_media_player);
        prevButton = view.findViewById(R.id.prev_step_button);
        nextButton = view.findViewById(R.id.next_step_button);
        stepsImageView = view.findViewById(R.id.steps_image_view);

        updateUI(currDescription, currVideoUri, currThumbnailUri);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeStepPosition < currRecipesSteps.size() - 1) {
                    recipeStepPosition++;
                    currDescription = currRecipesSteps.get(recipeStepPosition).getDescription();
                    currVideoUri = currRecipesSteps.get(recipeStepPosition).getVideoURL();
                    currThumbnailUri = currRecipesSteps.get(recipeStepPosition).getThumbnamilUrl();
                    updateUI(currDescription, currVideoUri, currThumbnailUri);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeStepPosition > 0) {
                    recipeStepPosition--;
                    currDescription = currRecipesSteps.get(recipeStepPosition).getDescription();
                    currVideoUri = currRecipesSteps.get(recipeStepPosition).getVideoURL();
                    updateUI(currDescription, currVideoUri, currThumbnailUri);
                }
            }
        });

        return view;

    }

    public void updateUI(String desc, String mediaUri, String picUri) {
        if (simpleExoPlayer != null) {
            releasePlayer();
        }
        stepDetailsTV.setText(desc);
        checkButtons();

        if (!mediaUri.isEmpty()) {
            initializePlayer(mediaUri);
        } else if (!picUri.isEmpty()) {
            releasePlayer();
            simpleExoPlayerView.setVisibility(View.GONE);
            stepsImageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(picUri).into(stepsImageView);
        } else {
            releasePlayer();
            simpleExoPlayerView.setVisibility(View.GONE);
            stepsImageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(R.mipmap.def_steps).into(stepsImageView);
        }

    }

    private void initializePlayer(String mediaUri) {

        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            simpleExoPlayerView.setVisibility(View.VISIBLE);
            stepsImageView.setVisibility(View.GONE);
            Uri uri = Uri.parse(mediaUri);
            MediaSource mediaSource = buildMediaSource(uri);

            if (playerPosition != C.TIME_UNSET) {
                
                simpleExoPlayer.seekTo(playerPosition);
            }/**/

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(shouldAutoPlay);
        }


    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Baking_App")).
                createMediaSource(uri);
    }

    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private void checkButtons() {
        if (recipeStepPosition >= 0 && recipeStepPosition < currRecipesSteps.size()) {
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
        }
        if (recipeStepPosition >= currRecipesSteps.size() - 1) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(true);
        }
        if (recipeStepPosition <= 0) {
            nextButton.setEnabled(true);
            prevButton.setEnabled(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(AUTO_PLAY, shouldAutoPlay);
        outState.putInt(PREVIOUS_POSITION, prevPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(currDescription, currVideoUri, currThumbnailUri);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            playerPosition = simpleExoPlayer.getCurrentPosition();
            shouldAutoPlay = simpleExoPlayer.getPlayWhenReady();
        }
        prevPosition = recipeStepPosition;
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
