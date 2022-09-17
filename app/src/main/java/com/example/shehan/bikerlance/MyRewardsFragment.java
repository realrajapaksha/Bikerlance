package com.example.shehan.bikerlance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add( new RewardModel("Cashback","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Discount","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Buy bike get free helmet","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Cashback","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Discount","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Buy bike get free helmet","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Cashback","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Discount","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));
        rewardModelList.add( new RewardModel("Buy bike get free helmet","till 15th November 2022","GET 10% OFF on any kind of motorbike above RS.500000/- and below RS.50000/-."));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,false);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        return view;
    }

}
