package com.example.android.popularmoviespractice.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.adapters.FavoritesAdapter;
import com.example.android.popularmoviespractice.adapters.MovieAdapter;
import com.example.android.popularmoviespractice.tables.AppDatabase;
import com.example.android.popularmoviespractice.tables.AppExecutors;
import com.example.android.popularmoviespractice.tables.Favorites;
import com.example.android.popularmoviespractice.tables.Movies;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment  {

private AppDatabase mDb;
private FavoritesAdapter mAdapter;
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
private int favoriteView;


    public FavoritesFragment() {
        // Required empty public constructor
    }




//    public static void removeFavorite(View v) {
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        setHasOptionsMenu(true);

//// Find a reference to the {@link RecyclerView} in the layout
       recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//
//
//
//        // use a grid layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
//
//        // Create a new adapter that takes an empty list of moviess as input
        mAdapter = new FavoritesAdapter(getActivity(), new ArrayList<Favorites>(),  this);
        recyclerView.setAdapter(mAdapter);
//
//
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
        recyclerView.setAdapter(mAdapter);

        Button button = (Button) rootView.findViewById(R.id.removeFavoriteButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v,  final RecyclerView.ViewHolder viewHolder) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //The Adapter holds the items you want to delete

                        int position = viewHolder.getAdapterPosition();
                        List<Favorites> removeFavorite = mAdapter.getTasks();
                        mDb.favoritesDao().deleteFavorites(removeFavorite.get(position));
                        retrieveFavorites();
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

//       public void removeFavorite(View view,  final RecyclerView.ViewHolder viewHolder)  {
//            AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//               public void run() {
//                    //The Adapter holds the items you want to delete
//                    int position = viewHolder.getAdapterPosition();
//                   List<Favorites> removeFavorite = mAdapter.getTasks();
//                   mDb.favoritesDao().deleteFavorites(removeFavorite.get(position));
//                    retrieveFavorites();
//                }
//            });
//        }

        return rootView;
    }




    @Override
    public void onResume() {
        super.onResume();
     retrieveFavorites();

    }

    private void retrieveFavorites() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Favorites> favorites =   mDb.favoritesDao().loadAllFavorites();
                //Will be able to simplify once add more Android Architecture Components
               getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(favorites);
                    }
                });
            }
        });
    }


}