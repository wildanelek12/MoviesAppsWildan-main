package com.wildan.moviesappswildan.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteMovies {


    @PrimaryKey
    private int id;

    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "popularity")
    private double popularity;
    @ColumnInfo(name = "voteAverage")
    private double voteAverage;
    @ColumnInfo(name = "voteCount")
    private int voteCount;
    @ColumnInfo(name = "overview")
    private String overview;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getOverview(){
        return overview;
    }



    public String getTitle(){
        return title;
    }




    public String getBackdropPath(){
        return backdropPath;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public double getPopularity(){
        return popularity;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public int getId(){
        return id;
    }



    public int getVoteCount(){
        return voteCount;
    }
}
