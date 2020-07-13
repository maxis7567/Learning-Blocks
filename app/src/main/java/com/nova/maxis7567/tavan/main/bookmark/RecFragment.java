package com.nova.maxis7567.tavan.main.bookmark;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.globaladapters.ActivityAdapter;
import com.nova.maxis7567.tavan.globaladapters.BlogAdapter;
import com.nova.maxis7567.tavan.globaladapters.SubCatAdapter;
import com.nova.maxis7567.tavan.main.categories.CatAdapter;
import com.nova.maxis7567.tavan.models.Activity;
import com.nova.maxis7567.tavan.models.Blog;
import com.nova.maxis7567.tavan.models.Bookmark;
import com.nova.maxis7567.tavan.models.Category;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecFragment extends Fragment {


    public RecFragment() {
        // Required empty public constructor
    }


    public static RecFragment newInstance(List list) {
        RecFragment fragment = new RecFragment();
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
        View view=inflater.inflate(R.layout.fragment_rec, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.FragmentRec);
        try {
            List list= ((List) getArguments().get("list"));
            if (list.get(0).getClass()==Blog.class){
                recyclerView.setAdapter(new BlogAdapter(getContext(),list,null));
            }else if (list.get(0).getClass()==Activity.class){
                recyclerView.setAdapter(new ActivityAdapter(getContext(),list));
            }else {
                recyclerView.setAdapter(new SubCatAdapter(getContext(),list));
            }
        }catch (Exception e){}
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}