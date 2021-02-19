package com.nova.maxis7567.learningblocks1.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.main.blog.BlogFragment;
import com.nova.maxis7567.learningblocks1.main.bookmark.BookmarkFragment;
import com.nova.maxis7567.learningblocks1.main.categories.CatFragment;
import com.nova.maxis7567.learningblocks1.main.dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private int containerId;

    private Fragment dashboard,category,blog,bookmark;
    private ImageView dashboardIc,categoryIc,bookmarkIc,blogIc;
    private TextView dashboardTx,categoryTx,bookmarkTx,blogTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerId=R.id.MainFrameLayout;
        findViewById(R.id.MainBottomNavBarView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //required empty body
            }
        });
        dashboardIc=findViewById(R.id.MainDashboardIc);
        categoryIc=findViewById(R.id.MainCategoryIc);
        bookmarkIc=findViewById(R.id.MainBookmarkIc);
        blogIc=findViewById(R.id.MainBlogIc);
        dashboardTx=findViewById(R.id.MainDashboardTx);
        categoryTx=findViewById(R.id.MainCategoryTx);
        bookmarkTx=findViewById(R.id.MainBookmarlTx);
        blogTx=findViewById(R.id.MainBlogTx);
        fragmentManager=getSupportFragmentManager();
        dashboard=new DashboardFragment();
        category=new CatFragment();
        blog=new BlogFragment();
        bookmark= new BookmarkFragment();
        fragmentSelector(1);
        findViewById(R.id.MainDashboardBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSelector(1);
            }
        });
        findViewById(R.id.MainCategoryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSelector(2);
            }
        });
        findViewById(R.id.MainBookmarkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSelector(4);
            }
        });
        findViewById(R.id.MainBlogBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentSelector(3);
            }
        });


    }
    public void fragmentSelector(int i){
        Fragment f;
        switch (i){
            case 2:
                f = fragmentManager.findFragmentByTag("Category");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(containerId, category, "Category").commitNow();
                    dashboardIc.setImageResource(R.drawable.ic_cup_o);
                    dashboardIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    dashboardTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    categoryIc.setImageResource(R.drawable.ic_head);
                    categoryIc.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent2));
                    categoryTx.setTextColor(ContextCompat.getColor(this, R.color.colorAccent2));
                    bookmarkIc.setImageResource(R.drawable.ic_bookmark_o);
                    bookmarkIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    blogIc.setImageResource(R.drawable.ic_blog_o);
                    blogIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    blogTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));

                }
                break;
            case 3:
                f = fragmentManager.findFragmentByTag("Blog");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(containerId, blog, "Blog").commitNow();
                    dashboardIc.setImageResource(R.drawable.ic_cup_o);
                    dashboardIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    dashboardTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    categoryIc.setImageResource(R.drawable.ic_head_o);
                    categoryIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    categoryTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkIc.setImageResource(R.drawable.ic_bookmark_o);
                    bookmarkIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    blogIc.setImageResource(R.drawable.ic_blog);
                    blogIc.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent));
                    blogTx.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                }
                break;
            case 4:
                f = fragmentManager.findFragmentByTag("Bookmark");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(containerId, bookmark, "Bookmark").commitNow();
                    dashboardIc.setImageResource(R.drawable.ic_cup_o);
                    dashboardIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    dashboardTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    categoryIc.setImageResource(R.drawable.ic_head_o);
                    categoryIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    categoryTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkIc.setImageResource(R.drawable.ic_bookmark);
                    bookmarkIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    blogIc.setImageResource(R.drawable.ic_blog_o);
                    blogIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    blogTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                }
                break;
            default:
                f = fragmentManager.findFragmentByTag("Dashboard");
                if (!(f != null && f.isVisible())) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(containerId, dashboard, "Dashboard").commitNow();
                    dashboardIc.setImageResource(R.drawable.ic_cup);
                    dashboardIc.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                    dashboardTx.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                    categoryIc.setImageResource(R.drawable.ic_head_o);
                    categoryIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    categoryTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkIc.setImageResource(R.drawable.ic_bookmark_o);
                    bookmarkIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    bookmarkTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));
                    blogIc.setImageResource(R.drawable.ic_blog_o);
                    blogIc.setColorFilter(ContextCompat.getColor(this, R.color.textBlack));
                    blogTx.setTextColor(ContextCompat.getColor(this, R.color.textBlack));

                }
                break;
        }
    }
}