package com.example.shehan.bikerlance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.shehan.bikerlance.DBqueries.lists;
import static com.example.shehan.bikerlance.DBqueries.loadFragmentData;
import static com.example.shehan.bikerlance.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //////// home page fake list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakelList = new ArrayList<>();
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,"","#ffffff",horizontalProductScrollModelFakelList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#ffffff",horizontalProductScrollModelFakelList));

        //////// home page fake list

        categoryRecyclerView = findViewById(R.id.category_recycleview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayout.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        adapter = new HomePageAdapter(homePageModelFakeList);

        int listPosition = 0;
        for (int x = 0;x < loadedCategoriesNames.size();x++){
            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
        }
        if (listPosition == 0){
            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNames.size() -1 , title);
        }else {
            adapter = new HomePageAdapter(lists.get(listPosition));

        }
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        }else if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
