package com.wildan.moviesappswildan.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.wildan.moviesappswildan.Adapter.MoviesAdapter;
import com.wildan.moviesappswildan.MainActivity;
import com.wildan.moviesappswildan.Model.FavoriteMovies;
import com.wildan.moviesappswildan.Model.Response;
import com.wildan.moviesappswildan.Model.ResultsItem;
import com.wildan.moviesappswildan.R;
import com.wildan.moviesappswildan.Rest.ApiServices;
import com.wildan.moviesappswildan.Rest.Client;
import com.wildan.moviesappswildan.Room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ApiServices apiService;
    public static final String MYAPI_KEY = "79bde1fdca47c096ddc9f3ce1356f832";
    public static final String LANGUAGE = "en-US";
    private int currentPageMoviePopular = 1;
    private int totalPagesMoviePopular = 1;
    private MoviesAdapter moviePopularAdapter;
    private final List<ResultsItem> moviePopularResults = new ArrayList<>();
    RecyclerView rv_movies;
    private AppDatabase db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Retrofit retrofit = Client.getClient();
        apiService = retrofit.create(ApiServices.class);
        getPopularMovies();
        rv_movies = root.findViewById(R.id.rv_movies);
        return root;
    }
    private void getPopularMovies() {
        Call<Response> call = apiService.getPopularMovies(MYAPI_KEY, LANGUAGE, currentPageMoviePopular);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null) {
                    totalPagesMoviePopular = response.body().getTotalPages();
                    if (response.body().getResults() != null) {
                        moviePopularResults.addAll(response.body().getResults());
                        moviePopularAdapter = new MoviesAdapter(getContext(),moviePopularResults);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        rv_movies.setLayoutManager(layoutManager);
                        rv_movies.setAdapter(moviePopularAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call,@NonNull Throwable t) {
                Toast.makeText(getContext(), "Terjadi kesalahan saat memuat popular movies",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}