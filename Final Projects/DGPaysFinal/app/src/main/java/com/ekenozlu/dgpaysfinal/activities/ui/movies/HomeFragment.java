package com.ekenozlu.dgpaysfinal.activities.ui.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekenozlu.dgpaysfinal.adapters.MovieAdapter;
import com.ekenozlu.dgpaysfinal.databinding.FragmentHomeBinding;
import com.ekenozlu.dgpaysfinal.models.MovieModel;

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
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    private String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=188b7cc2a027cbbf38915b398655b0b8";

    ArrayList<MovieModel> movieArrayList;
    RecyclerView recyclerView;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.moviesRecycleryView;
        GetData getData = new GetData();
        getData.execute();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public class GetData extends AsyncTask<String,String,String>{
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
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject tempObject = jsonArray.getJSONObject(i);

                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovieID(tempObject.getInt("id"));
                    movieModel.setMovieImageURL(tempObject.getString("poster_path"));
                    movieModel.setMovieTitle(tempObject.getString("title"));
                    movieModel.setMovieRating(tempObject.getDouble("vote_average"));
                    movieModel.setReleaseDate(tempObject.getString("release_date").substring(0,4));

                    movieArrayList.add(movieModel);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            loadData(movieArrayList);
        }
    }

    public void loadData(List<MovieModel> movieList){
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeFragment.newInstance().getContext()));
        MovieAdapter movieAdapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}