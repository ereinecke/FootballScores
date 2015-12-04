package barqsoft.footballscores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yehya khaled on 2/27/2015.
 */
public class PagerFragment extends Fragment
{
    public ViewPager mPagerHandler;
    private final MainScreenFragment[] viewFragments = new MainScreenFragment[Constants.NUM_PAGES];
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        mPagerHandler = (ViewPager) rootView.findViewById(R.id.pager);
        myPageAdapter mPagerAdapter = new myPageAdapter(getChildFragmentManager());
        for (int i = 0;i < Constants.NUM_PAGES;i++)
        {
            Date fragmentDate = new Date(System.currentTimeMillis()+((i-2)*86400000));
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.getDefault());
            viewFragments[i] = new MainScreenFragment();
            viewFragments[i].setFragmentDate(mFormat.format(fragmentDate));
        }
        mPagerHandler.setAdapter(mPagerAdapter);
        mPagerHandler.setCurrentItem(MainActivity.current_fragment);
        return rootView;
    }

    private class myPageAdapter extends FragmentStatePagerAdapter {
        @Override
        public Fragment getItem(int i) {
            return viewFragments[i];
        }

        @Override
        public int getCount() {
            return Constants.NUM_PAGES;
        }

        public myPageAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            // TODO: probably need to revisit this equation to handle different number of days
            return Utilities.getDayName(getActivity(),
                    System.currentTimeMillis() + ((position - 2) * 86400000));
        }
    }
}
