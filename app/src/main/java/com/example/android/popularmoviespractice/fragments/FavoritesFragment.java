package com.example.android.popularmoviespractice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.adapters.FavoritesAdapter;
import com.example.android.popularmoviespractice.adapters.MovieAdapter;
import com.example.android.popularmoviespractice.tables.AppDatabase;
import com.example.android.popularmoviespractice.tables.Favorites;
import com.example.android.popularmoviespractice.tables.Movies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

private AppDatabase mDb;
private FavoritesAdapter mAdapter;
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;

    public FavoritesFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FavoritesFragment.
//     */
//
//    public static FavoritesFragment newInstance(String param1, String param2) {
//        FavoritesFragment fragment = new FavoritesFragment();
//        Bundle args = new Bundle();
//       ;
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        setHasOptionsMenu(true);

// Find a reference to the {@link RecyclerView} in the layout
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);



        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Create a new adapter that takes an empty list of moviess as input
        mAdapter = new FavoritesAdapter(getActivity(), new ArrayList<Favorites>());
        recyclerView.setAdapter(mAdapter);



        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setTasks(mDb.favoritesDao().loadAllFavorites());
    }
}