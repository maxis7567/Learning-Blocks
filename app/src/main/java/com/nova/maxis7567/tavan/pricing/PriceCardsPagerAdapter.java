package com.nova.maxis7567.tavan.pricing;

import android.app.Activity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.models.Package;

import java.util.ArrayList;
import java.util.List;

public class PriceCardsPagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    private List<Package> priceCardList=new ArrayList<>();
    private Activity mContext;
    private FragmentManager mFragmentManager;
    private float mScale;

    public PriceCardsPagerAdapter(Activity context, FragmentManager fragmentManager,@Nullable List<Package> priceCardList) {
        super(fragmentManager);
        this.mFragmentManager = fragmentManager;
        this.mContext = context;
        if (priceCardList!=null){
            this.priceCardList=priceCardList;
        }else {
            for (int i = 0; i < 5; i++) {
                this.priceCardList.add(new Package());
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        // make the first mViewPager bigger than others
        if (position == 0)
            mScale = BIG_SCALE;
        else
            mScale = SMALL_SCALE;

        return PriceCardFragment.newInstance(mContext, position, mScale,priceCardList.get(position));
    }

    @Override
    public int getCount() {
        return priceCardList.size();
    }

    @Override
    public void transformPage(View page, float position) {
        PriceCardLinearLayout myLinearLayout = (PriceCardLinearLayout) page.findViewById(R.id.item_root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);
    }
}