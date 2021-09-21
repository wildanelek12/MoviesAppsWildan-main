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

import com.bumptech.glide.Glide;
import com.wildan.moviesappswildan.DetailActivity;
import com.wildan.moviesappswildan.Model.ResultsItem;
import com.wildan.moviesappswildan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Context context;
    private List<ResultsItem> listPopuler;


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

        String year;

        holder.tvTitle.setText(listPopuler.get(position).getTitle());
        holder.tv_vote.setText(String.valueOf(listPopuler.get(position).getPopularity()));
        holder.tv_rate.setText(String.valueOf(listPopuler.get(position).getVoteAverage()));

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + listPopuler.get(position).getPosterPath())
                .into(holder.imgBackdrop);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(listPopuler.get(position).getReleaseDate());
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            year = df.format(date);
            holder.tv_release_date.setText(year+"  |  ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.cl_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",listPopuler.get(position).getId());
                context.startActivity(intent);
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
        private TextView tv_release_date,tv_rate;
        private TextView tv_vote;
        private ConstraintLayout cl_movies;
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imgBackdrop = (ImageView) itemView.findViewById(R.id.img_backdrop);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tv_release_date = (TextView) itemView.findViewById(R.id.tv_release_date);
            tv_vote = (TextView) itemView.findViewById(R.id.tv_vote);
            cl_movies = (ConstraintLayout) itemView.findViewById(R.id.cl_movies);
            tv_rate = itemView.findViewById(R.id.tv_rate);
        }
    }
}
