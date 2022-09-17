package com.example.shehan.bikerlance;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shehan.bikerlance.DBqueries.categoryModelList;
import static com.example.shehan.bikerlance.DBqueries.lists;
import static com.example.shehan.bikerlance.DBqueries.loadCategories;
import static com.example.shehan.bikerlance.DBqueries.loadFragmentData;
import static com.example.shehan.bikerlance.DBqueries.loadedCategoriesNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;
    private Button retryBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        categoryRecyclerView = view.findViewById(R.id.category_recycleview);
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        retryBtn = view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayout.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);


        /////////category fake list
        categoryModelFakeList.add(new CategoryModel("null", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        /////////category fake list

        //////// home page fake list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakelList = new ArrayList<>();
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakelList.add(new HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1, "", "#dfdfdf"));
        homePageModelFakeList.add(new HomePageModel(2, "", "#dfdfdf", horizontalProductScrollModelFakelList, new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3, "", "#dfdfdf", horizontalProductScrollModelFakelList));

        //////// home page fake list

        categoryAdapter = new CategoryAdapter(categoryModelFakeList);

        adapter = new HomePageAdapter(homePageModelFakeList);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);
            if (lists.size() == 0) {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");

            } else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        } else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        ///////////// refresh layout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadpage();
            }
        });

        ///////////// refresh layout

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reloadpage();
            }
        });
        return view;
    }

    private void reloadpage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView, getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");

        } else {
            MainActivity.drawer.setDrawerLockMode(1);

            Toast.makeText(getContext(), "No internet Connection found!", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    ImageView image1, image2, image3, image4;
    TextView text1, text2, text3, text4;
    LinearLayout linearLayout;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<QueryDocumentSnapshot> vehicleList = new ArrayList<>();
    private static final String NEW_USER = "NEW_USER";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi(view);

        try {
            db.collection("USERS").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getData().get("newUser").equals("0")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    removeNewUser();
                                    getDatabase();
                                }
                            }, 60000); //WAIT FOR 60 SECONDS

                        } else {
                            linearLayout.setVisibility(View.VISIBLE);
                            getDatabase();
                        }
                    } else {
                        getPreference();
                    }
                }
            });
        } catch (Exception e) {
            getPreference();
        }
    }

    private void getPreference() {
        SharedPreferences prefs = getContext().getSharedPreferences(NEW_USER, MODE_PRIVATE);
        if (prefs.getBoolean("isNewUser", true)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    linearLayout.setVisibility(View.VISIBLE);
                    getDatabase();
                }
            }, 60000); //WAIT FOR 60 SECONDS
        } else {
            getDatabase();
        }
    }

    private void removeNewUser() {
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("newUser", "1");

        db.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .update(userdata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    private void getDatabase() {
        db.collection("CATEGORIES").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    vehicleList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getId().equals("HOME")) {
                            vehicleList.add(document);
                        }
                    }
                    getSuggesion();
                } else {
                    Log.e("kkk", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void getSuggesion() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 8; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        Glide.with(getContext()).load(vehicleList.get(list.get(0)).getData().get("logo").toString()).into(image1);
        Glide.with(getContext()).load(vehicleList.get(list.get(1)).getData().get("logo").toString()).into(image2);
        Glide.with(getContext()).load(vehicleList.get(list.get(2)).getData().get("logo").toString()).into(image3);
        Glide.with(getContext()).load(vehicleList.get(list.get(3)).getData().get("logo").toString()).into(image4);

        text1.setText(vehicleList.get(list.get(0)).getData().get("categoryName").toString());
        text2.setText(vehicleList.get(list.get(1)).getData().get("categoryName").toString());
        text3.setText(vehicleList.get(list.get(2)).getData().get("categoryName").toString());
        text4.setText(vehicleList.get(list.get(3)).getData().get("categoryName").toString());

    }

    @Override
    public void onDestroy() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(NEW_USER, MODE_PRIVATE).edit();
        editor.putBoolean("isNewUser", false);
        editor.apply();
        super.onDestroy();
    }

    private void initUi(View view) {
        image1 = view.findViewById(R.id.suggest_image1);
        image2 = view.findViewById(R.id.suggest_image2);
        image3 = view.findViewById(R.id.suggest_image3);
        image4 = view.findViewById(R.id.suggest_image4);

        text1 = view.findViewById(R.id.suggest_text1);
        text2 = view.findViewById(R.id.suggest_text2);
        text3 = view.findViewById(R.id.suggest_text3);
        text4 = view.findViewById(R.id.suggest_text4);

        linearLayout = view.findViewById(R.id.linearLayout12);
        linearLayout.setVisibility(View.GONE);
    }
}



