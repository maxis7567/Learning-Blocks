package com.nova.maxis7567.learningblocks.main.categories.subcat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.globaladapters.ActivityAdapter;
import com.nova.maxis7567.learningblocks.globaladapters.SubCatAdapter;
import com.nova.maxis7567.learningblocks.models.Activity;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubCatListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCatListFragment extends Fragment {



    public SubCatListFragment() {
        // Required empty public constructor
    }


    public static SubCatListFragment newInstance(List list) {
        SubCatListFragment fragment = new SubCatListFragment();
        Bundle args = new Bundle();
        args.putSerializable("list", (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sub_cat_list, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.SubCatListRec);
        List list= (List) getArguments().get("list");
        if (list.get(0).getClass()== Activity.class){
            recyclerView.setAdapter(new ActivityAdapter(getContext(),list));
        }else {
            recyclerView.setAdapter(new SubCatAdapter(getContext(),list));
        }
        recyclerView.setItemViewCacheSize(1000);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        return view;
    }
}