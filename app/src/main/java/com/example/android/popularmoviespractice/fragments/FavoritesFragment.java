package com.example.android.popularmoviespractice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.android.popularmoviespractice.MainViewModel;
import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.adapters.FavoritesAdapter;
import com.example.android.popularmoviespractice.tables.AppDatabase;
import com.example.android.popularmoviespractice.tables.AppExecutors;
import com.example.android.popularmoviespractice.tables.Favorites;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment  {


    // Constant for logging
    private static final String TAG = FavoritesFragment.class.getSimpleName();
private AppDatabase mDb;
private FavoritesAdapter mAdapter;
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;



    public FavoritesFragment() {
        // Required empty public constructor
    }


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

// Find a reference to the {@link RecyclerView} in the layout
       recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

       // use a grid layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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


  /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
               public void run() {
                    //The Adapter holds the items you want to delete
                    int position = viewHolder.getAdapterPosition();
                   List<Favorites> removeFavorite = mAdapter.getTasks();
                   mDb.favoritesDao().deleteFavorites(removeFavorite.get(position));
                }
            });
            }
        }).attachToRecyclerView(recyclerView);

        setUpViewModel();
        return rootView;
    }


    private void setUpViewModel() {
        //Insert Update andDelete do not have to observe changes in the database. This is for retrieving tass.
        //LiveData is for retrieving data, AppExcutors for the other three
        // Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        //final LiveData<List<Favorites>> favorites =   mDb.favoritesDao().loadAllFavorites();
        MainViewModel viewModel=new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(MainViewModel.class);
        viewModel.getTasks().observe((LifecycleOwner) getActivity(), new Observer<List<Favorites>>() {
            @Override
            public void onChanged(List<Favorites> favoritesEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setTasks(favoritesEntries);
            }
        });

    }

}