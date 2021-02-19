package com.nova.maxis7567.learningblocks1.main.bookmark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nova.maxis7567.learningblocks1.models.Activity;
import com.nova.maxis7567.learningblocks1.models.Blog;
import com.nova.maxis7567.learningblocks1.models.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<List> list= new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, Bookmark bookmark) {
        super(fm);
        if (!bookmark.getCategoryList().isEmpty()) {
            list.add(bookmark.getCategoryList());
        }
        if (!bookmark.getActivityList().isEmpty()) {
            list.add(bookmark.getActivityList());
        }
        if (!bookmark.getBlogList().isEmpty()) {
            list.add(bookmark.getBlogList());
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RecFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (list.get(position).get(0) instanceof Blog){
            return "Blog"+"("+list.get(position).size()+")";
        }else if (list.get(position).get(0) instanceof Activity){
            return "Activities"+"("+list.get(position).size()+")";
        }else {
            return "Categories"+"("+list.get(position).size()+")";
        }
    }
}
