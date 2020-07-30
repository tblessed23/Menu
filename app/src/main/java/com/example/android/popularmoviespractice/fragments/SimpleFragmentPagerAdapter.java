package com.example.android.popularmoviespractice.fragments;

import android.app.Fragment;
import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.popularmoviespractice.R;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Create a new {@link SimpleFragmentPagerAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     * @return
     */
    @Override
    public androidx.fragment.app.Fragment getItem(int position) {

        if (position == 0) {
            return new MostPopularFragment();
        } else if (position == 1)  {
            return new TopRatedFragment();
        } else   {
            return new FavoritesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.most_popular);
        } else if (position == 1)  {
            return mContext.getString(R.string.top_rated);
        } else {
            return mContext.getString(R.string.favorites);
        }
    }
}
