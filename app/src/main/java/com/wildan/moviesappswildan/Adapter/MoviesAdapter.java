package com.wildan.moviesappswildan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.wildan.moviesappswildan.DetailActivity;
import com.wildan.moviesappswildan.Model.FavoriteMovies;
import com.wildan.moviesappswildan.Model.ResultsItem;
import com.wildan.moviesappswildan.R;
import com.wildan.moviesappswildan.Room.AppDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Context context;
    private List<ResultsItem> listPopuler;
    private AppDatabase db;



    public MoviesAdapter(Context context, List<ResultsItem> listPopuler) {
        this.context = context;
        this.listPopuler = listPopuler;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies,
                parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "favoriteMovies").allowMainThreadQueries().build();

        if (db.moviesDAO().isRecordExistsUserId(listPopuler.get(position).getId())){
            holder.like.setImageResource(R.drawable.ic_after_like);
        }else {
            holder.like.setImageResource(R.drawable.ic_before_like);
        }

        String year;

        int id = listPopuler.get(position).getId();
        String title = listPopuler.get(position).getTitle();
        List<Integer>genre = listPopuler.get(position).getGenreIds();
        String backdrop = listPopuler.get(position).getBackdropPath();
        String release = listPopuler.get(position).getReleaseDate();
        double popularity = listPopuler.get(position).getPopularity();
        double vote_average = listPopuler.get(position).getVoteAverage();
        int vote_count = listPopuler.get(position).getVoteCount();
        String overview = listPopuler.get(position).getOverview();



        holder.tvTitle.setText(listPopuler.get(position).getTitle());
        holder.tv_vote.setText(String.valueOf(listPopuler.get(position).getPopularity()));
        holder.tv_rate.setText(String.valueOf(listPopuler.get(position).getVoteAverage()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(listPopuler.get(position).getReleaseDate());
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            year = df.format(date);
            holder.tv_release_date.setText(year + "  |  ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cl_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",listPopuler.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.moviesDAO().isRecordExistsUserId(listPopuler.get(position).getId())){
                    db.moviesDAO().hapus(listPopuler.get(position).getId());
                    holder.like.setImageResource(R.drawable.ic_before_like);
                }else {
                    FavoriteMovies favoriteMovies = new FavoriteMovies();
                    favoriteMovies.setId(id);
                    favoriteMovies.setTitle(title);
                    favoriteMovies.setBackdropPath(backdrop);
                    favoriteMovies.setReleaseDate(release);
                    favoriteMovies.setPopularity(popularity);
                    favoriteMovies.setVoteAverage(vote_average);
                    favoriteMovies.setVoteCount(vote_count);
                    favoriteMovies.setOverview(overview);
                    db.moviesDAO().insertAll(favoriteMovies);
                    holder.like.setImageResource(R.drawable.ic_after_like);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listPopuler.size();
    }




    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imgBackdrop;
        private TextView tvTitle;
        private TextView tv_release_date, tv_rate;
        private TextView tv_vote;
        private ConstraintLayout cl_movies;
        private ImageView like;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imgBackdrop = (ImageView) itemView.findViewById(R.id.img_backdrop);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tv_release_date = (TextView) itemView.findViewById(R.id.tv_release_date);
            tv_vote = (TextView) itemView.findViewById(R.id.tv_vote);
            cl_movies = (ConstraintLayout) itemView.findViewById(R.id.cl_movies);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            like = (ImageView) itemView.findViewById(R.id.like);
        }
    }
}
