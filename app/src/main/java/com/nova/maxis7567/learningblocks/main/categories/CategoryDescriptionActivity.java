package com.nova.maxis7567.learningblocks.main.categories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.models.Category;

public class CategoryDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_description);
        Category category= (Category) getIntent().getSerializableExtra("cat");
        ((TextView) findViewById(R.id.CatDescTitle)).setText(category.getName());
        ((TextView) findViewById(R.id.CatDesc)).setText(category.getDesc());

    }
}