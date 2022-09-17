package com.example.shehan.bikerlance;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.shehan.bikerlance.DeliveryActivity.SELECT_ADDRESS;
import static com.example.shehan.bikerlance.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.shehan.bikerlance.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder> {

    private List<AddressesModel> addressesModelList;
    private int MODE;
    private int preSelectedPosition;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
        preSelectedPosition = DBqueries.selectedAddress;

    }


    @NonNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresses_item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.Viewholder viewholder, int position) {

        String name = addressesModelList.get(position).getFullname();
        String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPincode();
        Boolean selected = addressesModelList.get(position).getSelected();
        viewholder.setData(name,address,pincode,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder {

        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            icon =itemView.findViewById(R.id.icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);
        }

        private void setData(String username, String useraddress, String userpincode, Boolean selected, final int position){
          fullname.setText(username);
          address.setText(useraddress);
          pincode.setText(userpincode);

          if (MODE == SELECT_ADDRESS){
              icon.setImageResource(R.mipmap.check);
              if (selected){
                  icon.setVisibility(View.VISIBLE);
                  preSelectedPosition = position;
              }else {
                  icon.setVisibility(View.GONE);
              }
              itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (preSelectedPosition != position) {
                          addressesModelList.get(position).setSelected(true);
                          addressesModelList.get(preSelectedPosition).setSelected(false);
                          refreshItem(preSelectedPosition, position);
                          preSelectedPosition = position;
                          DBqueries.selectedAddress = position;
                      }
                  }
              });


          }else if(MODE == MANAGE_ADDRESS){
              optionContainer.setVisibility(View.GONE);
              icon.setImageResource(R.mipmap.vertical_dots);
              icon.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      optionContainer.setVisibility(View.VISIBLE);
                      refreshItem(preSelectedPosition,preSelectedPosition);
                      preSelectedPosition = position;
                  }
              });
              itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      refreshItem(preSelectedPosition,preSelectedPosition);
                      preSelectedPosition = -1;
                  }
              });
          }
        }
    }
}
