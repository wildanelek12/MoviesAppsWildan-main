package com.wildan.moviesappswildan.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.wildan.moviesappswildan.Adapter.FavoriteMoviesAdapter;
import com.wildan.moviesappswildan.Model.FavoriteMovies;
import com.wildan.moviesappswildan.R;
import com.wildan.moviesappswildan.Room.AppDatabase;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    RecyclerView rv_favorite;
    AppDatabase db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
      rv_favorite = root.findViewById(R.id.rv_favorite);
      getData();

      return root;
    }
    private void getData() {
        class GetData extends AsyncTask<Void,Void, List<FavoriteMovies>> {

            @Override
            protected List<FavoriteMovies> doInBackground(Void... voids) {
                db = Room.databaseBuilder(getContext(),
                        AppDatabase.class, "favoriteMovies").allowMainThreadQueries().build();
                List<FavoriteMovies>myDataLists=db.moviesDAO().getAll();
                return myDataLists;

            }

            @Override
            protected void onPostExecute(List<FavoriteMovies> myDataList) {
                FavoriteMoviesAdapter adapter=new FavoriteMoviesAdapter(getContext(),myDataList);
                rv_favorite.setAdapter(adapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rv_favorite.setLayoutManager(layoutManager);
                super.onPostExecute(myDataList);
            }
        }
        GetData gd=new GetData();
        gd.execute();
    }
}