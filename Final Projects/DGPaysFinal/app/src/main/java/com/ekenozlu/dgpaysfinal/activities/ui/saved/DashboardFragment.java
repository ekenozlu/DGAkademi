package com.ekenozlu.dgpaysfinal.activities.ui.saved;

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

import com.ekenozlu.dgpaysfinal.activities.ui.movies.HomeFragment;
import com.ekenozlu.dgpaysfinal.adapters.MovieAdapter;
import com.ekenozlu.dgpaysfinal.databinding.FragmentDashboardBinding;
import com.ekenozlu.dgpaysfinal.models.MovieModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    DocumentReference savedMoviesRef;
    ArrayList<Long> savedMoviesIDArrayList;
    ArrayList<MovieModel> movieModelArrayList;

    private String JSON_URL;

    RecyclerView recyclerView;
    ArrayList<GetData> getDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedMoviesIDArrayList = new ArrayList<>();
        movieModelArrayList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        savedMoviesRef = firebaseFirestore.collection("users").document(auth.getUid());

        getDataFromDB();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.savedRecyclerView;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDataList = new ArrayList<>();
    }

    public class GetData extends AsyncTask<String,String,String> {
        String urlString;

        public GetData(String urlString) {
            this.urlString = urlString;
        }

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(urlString);
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
                System.out.println("TRY " + JSON_URL);
                JSONObject jsonObject = new JSONObject(s);
                MovieModel movieModel = new MovieModel();
                movieModel.setMovieID(jsonObject.getInt("id"));
                movieModel.setMovieImageURL(jsonObject.getString("poster_path"));
                movieModel.setMovieTitle(jsonObject.getString("title"));
                movieModel.setMovieRating(jsonObject.getDouble("vote_average"));
                movieModelArrayList.add(movieModel);
                System.out.println(movieModel.getMovieTitle());
                }
            catch (JSONException e) {
                e.printStackTrace();
            }
            loadData(movieModelArrayList);
        }
    }

    public void getDataFromDB(){
        savedMoviesRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                savedMoviesIDArrayList = (ArrayList<Long>) documentSnapshot.get("savedMovies");

                for(int i=0;i<savedMoviesIDArrayList.size();i++){
                    JSON_URL = "https://api.themoviedb.org/3/movie/" + savedMoviesIDArrayList.get(i) + "?api_key=188b7cc2a027cbbf38915b398655b0b8";
                    GetData getData = new GetData(JSON_URL);
                    getDataList.add(getData);
                    getDataList.get(i).execute();
                }
            }
        });
    }

    public void loadData(List<MovieModel> movieModelList){
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeFragment.newInstance().getContext()));
        MovieAdapter movieAdapter = new MovieAdapter(movieModelList);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}