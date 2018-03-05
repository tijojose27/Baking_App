package com.example.tijoj.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
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

    public int position;
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

    public long mediaPosition;
    public final String MEDIA_POSITION = "MEDIA POSITION";

    public StepsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        position = getArguments().getInt(Recipes.RECIPE_POSITION);
        isTwoPane = getArguments().getBoolean("LAYOUT", false);
        currRecipesSteps = getArguments().getParcelableArrayList(Recipes.RECIPE_KEY);

        currDescription = currRecipesSteps.get(position).getDescription();
        currVideoUri = currRecipesSteps.get(position).getVideoURL();
        currThumbnailUri = currRecipesSteps.get(position).getThumbnamilUrl();

        mediaPosition = C.TIME_UNSET;

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
                if (position < currRecipesSteps.size() - 1) {
                    position++;
                    currDescription = currRecipesSteps.get(position).getDescription();
                    currVideoUri = currRecipesSteps.get(position).getVideoURL();
                    currThumbnailUri = currRecipesSteps.get(position).getThumbnamilUrl();
                    updateUI(currDescription, currVideoUri, currThumbnailUri);
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    currDescription = currRecipesSteps.get(position).getDescription();
                    currVideoUri = currRecipesSteps.get(position).getVideoURL();
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
            if(mediaPosition!=C.TIME_UNSET){
                simpleExoPlayer.seekTo(mediaPosition);
            }
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }


    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Baking_App")).
                createMediaSource(uri);
    }

    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            mediaPosition = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private void checkButtons() {
        if (position >= 0 && position < currRecipesSteps.size()) {
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
        }
        if (position >= currRecipesSteps.size() - 1) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(true);
        }
        if (position <= 0) {
            nextButton.setEnabled(true);
            prevButton.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(currDescription, currVideoUri, currThumbnailUri);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MEDIA_POSITION, mediaPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
