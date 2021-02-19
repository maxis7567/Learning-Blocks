package com.nova.maxis7567.learningblocks1.main.categories.subcat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nova.maxis7567.learningblocks1.models.Activity;
import com.nova.maxis7567.learningblocks1.models.SubCat;

import java.util.ArrayList;
import java.util.List;

public class SubCatViewPager extends FragmentPagerAdapter {
    private List<List> list = new ArrayList<>();

    public SubCatViewPager(@NonNull FragmentManager fm, SubCat data) {
        super(fm);
        if (!data.getActivityList().isEmpty()) {
            list.add(data.getActivityList());
        }

        if (!data.getCategoryList().isEmpty()) {
            list.add(data.getCategoryList());
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            return SubCatListFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (list.get(position).get(0) instanceof Activity) {
            return "Activities" + "(" + list.get(position).size() + ")";
        } else {
            return "Categories";
        }
    }

}
