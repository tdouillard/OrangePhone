package com.example.douille.contactphone;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douille on 12/2/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    @Override

    public int getCount(){
        return mFragmentList.size();
    }

    public void AddFragment(Fragment fragment,String tag){
        mFragmentList.add(fragment);
    }

}
