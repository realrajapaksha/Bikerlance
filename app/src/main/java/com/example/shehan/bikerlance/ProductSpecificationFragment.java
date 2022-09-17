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
public class ProductSpecificationFragment extends Fragment {


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecificationModel> productSpecificationModelList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerView = view.findViewById(R.id.tv_product_Specification_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);


//        productSpecificationModelList.add(new ProductSpecificationModel(0," General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Manufacturer",    "Honda"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Production",      "2004"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Class",           "Sport bike"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Engine",          "999cc liquid-cooled Engine"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Power",           "217 Horsepower"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Torque",          "113 Nm @ 12500 rpm"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Feature"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Tires",           "Front: 120/70-ZR17, Rear:200/55-ZR17"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Oil capacity",    "4 Liters"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Fuel consumption","18 Kml"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Seat height",     "828mm"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Related",         "Honda CBR600RR"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Performance"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Fuel",         "Dual stage injection"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Power",         "130kw 12000rpm"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Top speed",         "310km/h (190mph)"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Breaking",         "37m (123ft)"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Fuel economy",         "6.2l/100km"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0," Engine"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Manufacturer",    "Honda"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Production",      "2004"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Class",           "Sport bike"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Engine",          "999cc liquid-cooled Engine"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Power",           "217 Horsepower"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Torque",          "113 Nm @ 12500 rpm"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Others"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Tires",           "Front: 120/70-ZR17, Rear:200/55-ZR17"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Oil capacity",    "4 Liters"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Fuel consumption","18 Kml"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Seat height",     "828mm"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Related",         "Honda CBR600RR"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Body"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Bodykit",         "FIber and plastick"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Tyers",         "Dunelop "));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Silencer",         "Honda k2"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Break",         "Brembo break"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Mono shock",         "Honda mono"));





        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }


}
