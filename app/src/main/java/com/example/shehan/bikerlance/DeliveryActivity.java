package com.example.shehan.bikerlance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeORaddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount;
    private TextView fullname;
    private TextView fullAddress;
    private TextView pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerView  = findViewById(R.id.delivery_recyclerview);
        changeORaddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);
        totalAmount = findViewById(R.id.total_cart_amount);
        fullname = findViewById(R.id.fullname);
        fullAddress = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);


        CartAdapter cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeORaddNewAddressBtn.setVisibility(View.VISIBLE);
        changeORaddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        fullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullname());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
