package com.ekenozlu.dgpaysfinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ekenozlu.dgpaysfinal.R;
import com.ekenozlu.dgpaysfinal.activities.ui.movies.HomeFragment;
import com.ekenozlu.dgpaysfinal.adapters.CastAdapter;
import com.ekenozlu.dgpaysfinal.adapters.GenreAdapter;
import com.ekenozlu.dgpaysfinal.databinding.ActivityDetailsBinding;
import com.ekenozlu.dgpaysfinal.models.CastModel;
import com.ekenozlu.dgpaysfinal.models.MovieModel;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    DocumentReference savedMoviesRef;
    ArrayList<Long> savedMoviesIDArrayList;
    boolean isMovieSaved;
    int movieID;


    private String JSON_URL;
    private String JSON_CASTURL;

    ArrayList<String> genreArray;
    RecyclerView genreRecyclerView;
    RecyclerView castRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<CastModel> castArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        movieID = intent.getIntExtra("movieID",0);

        savedMoviesIDArrayList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        savedMoviesRef = firebaseFirestore.collection("users").document(auth.getUid());
        getDataFromDB();

        JSON_URL = "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=188b7cc2a027cbbf38915b398655b0b8";
        JSON_CASTURL = "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=188b7cc2a027cbbf38915b398655b0b8";

        genreArray = new ArrayList<>();
        castArray = new ArrayList<>();
        genreRecyclerView = binding.detailGenreRecyclerView;
        castRecyclerView = binding.castRecyclerView;

        GetData getData = new GetData();
        getData.execute();

        //GetCastData getCastData = new GetCastData();
        //getCastData.execute();

    }

    public class GetData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1){
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String tempBackImageURL = "https://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path");
                Picasso.get().load(tempBackImageURL).into(binding.detailBackImage);
                String tempPosterURL = "https://image.tmdb.org/t/p/original" + jsonObject.getString("poster_path");
                Picasso.get().load(tempPosterURL).into(binding.detailMovieImage);
                binding.detailMovieTitle.setText(jsonObject.getString("title"));
                binding.detailMovieRating.setText(jsonObject.getDouble("vote_average") + "/10");

                binding.detailReleseDate.setText(jsonObject.getString("release_date").substring(0,4));
                binding.detailMovieLength.setText(jsonObject.getInt("runtime") + " minutes");

                JSONArray genreJSONArray = jsonObject.getJSONArray("genres");
                for(int i=0;i<genreJSONArray.length();i++){
                    JSONObject tempObject = genreJSONArray.getJSONObject(i);
                    genreArray.add(tempObject.getString("name"));
                }

                binding.detailMovieSummary.setText(jsonObject.getString("overview"));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            loadGenres(genreArray);
        }
    }

    public void loadGenres(List<String> genreArray){
        linearLayoutManager = new LinearLayoutManager(DetailsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        genreRecyclerView.setLayoutManager(linearLayoutManager);
        GenreAdapter genreAdapter = new GenreAdapter(genreArray);
        genreRecyclerView.setAdapter(genreAdapter);
    }

    public void loadCast(List<CastModel> castArray){
        linearLayoutManager = new LinearLayoutManager(DetailsActivity.this,LinearLayoutManager.HORIZONTAL,false);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        castRecyclerView.setLayoutManager(linearLayoutManager);
        CastAdapter castAdapter = new CastAdapter(castArray);
        castRecyclerView.setAdapter(castAdapter);
    }

    public void checkMovieSaved(int movieID){
        if(savedMoviesIDArrayList.contains(Long.parseLong(String.valueOf(movieID)))){
            isMovieSaved = true;
            binding.saveButton.setText("REMOVE FROM SAVED LIST");
        }
        else{
            isMovieSaved = false;
            binding.saveButton.setText("ADD TO YOUR SAVED LIST");
        }
    }

    public void saveButtonClicked(View view){
        if(isMovieSaved){
            savedMoviesIDArrayList.remove(Long.valueOf(movieID));
            binding.saveButton.setText("ADD TO YOUR SAVED LIST");
            isMovieSaved = false;
        }
        else{
            savedMoviesIDArrayList.add(Long.valueOf(movieID));
            binding.saveButton.setText("REMOVE FROM SAVED LIST");
            isMovieSaved = true;
        }
        syncDataToDB();
    }

    public void getDataFromDB(){
        savedMoviesRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                savedMoviesIDArrayList = (ArrayList<Long>) documentSnapshot.get("savedMovies");
                checkMovieSaved(movieID);
            }
        });

    }

    public void syncDataToDB(){
        savedMoviesRef.update("savedMovies",savedMoviesIDArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {}
        });
    }
}