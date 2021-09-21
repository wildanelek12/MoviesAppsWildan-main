package com.wildan.moviesappswildan;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.wildan.moviesappswildan.Model.DetailMovie;
import com.wildan.moviesappswildan.Model.GenresItem;
import com.wildan.moviesappswildan.Rest.ApiServices;
import com.wildan.moviesappswildan.Rest.Client;
import com.wildan.moviesappswildan.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ApiServices apiService;
    private CardView cardView2;
    private ImageView imgDetail;
    private TextView tvDetailJudul;
    private TextView tvDetailGenre;
    private TextView textView3;
    private TextView tvDetailOverview;
    private List<GenresItem> genresItemList = new ArrayList<GenresItem>();
    private TextView tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        apiService = Client.getClient().create(ApiServices.class);
        initView();
        tvDetailGenre.setText("");
        getDetail();
    }

    private void getDetail() {
        Call<DetailMovie> call = apiService.getMovieDetails(String.valueOf(getIntent().getIntExtra("id", 0)), HomeFragment.MYAPI_KEY);
        call.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(@NonNull Call<DetailMovie> call, @NonNull Response<DetailMovie> response) {
                if (response.body() != null) {
                    DetailMovie detailMovie = response.body();
                    tvDetailJudul.setText(response.body().getTitle());
                    tvDetailOverview.setText(response.body().getOverview());
                    genresItemList.addAll(response.body().getGenres());
                    for (int i = 0; i < detailMovie.getGenres().size(); i++) {
                        GenresItem genre = detailMovie.getGenres().get(i);
                        if (i < detailMovie.getGenres().size() - 1) {
                            tvDetailGenre.append(genre.getName() + ",");
                        } else {
                            tvDetailGenre.append(genre.getName());
                        }
                    }

                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/w500" + detailMovie.getPosterPath())
                            .into(imgDetail);

                    tvRating.setText(String.valueOf(detailMovie.getVoteAverage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailMovie> call, @NonNull Throwable t) {

                Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat memuat details dari ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        cardView2 = (CardView) findViewById(R.id.cardView2);
        imgDetail = (ImageView) findViewById(R.id.img_detail);
        tvDetailJudul = (TextView) findViewById(R.id.tv_detail_judul);
        tvDetailGenre = (TextView) findViewById(R.id.tv_detail_genre);
        textView3 = (TextView) findViewById(R.id.textView3);
        tvDetailOverview = (TextView) findViewById(R.id.tv_detail_overview);
        tvRating = (TextView) findViewById(R.id.tv_rating);
    }
}