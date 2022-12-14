package com.example.shehan.bikerlance;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    private int lastposition = -1;
    private TextView cartTotalAmount;
    private boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new cartItemViewholder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new cartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
      switch (cartItemModelList.get(position).getType()) {
          case CartItemModel.CART_ITEM:
              String productID = cartItemModelList.get(position).getProductID();
              String resource = cartItemModelList.get(position).getProductImage();
              String title = cartItemModelList.get(position).getProductTitle();
              Long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
              String productPrice = cartItemModelList.get(position).getProductPrice();
              String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
              Long offersApplied = cartItemModelList.get(position).getOffersApplied();

              ((cartItemViewholder) viewHolder).setItemDetails(productID, resource, title, freeCoupens, productPrice, cuttedPrice, offersApplied, position);
              break;
          case CartItemModel.TOTAL_AMOUNT:
              int totalItems =  0;
              int totalItemPrice = 0;
              String serviceCharge ;
              int totalAmount ;
              int savedAmount = 0;



              for (int x = 0;x < cartItemModelList.size();x++){

                  if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM){
                      totalItems++;
                      totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                  }
              }

              if (totalItemPrice > 500){
                  serviceCharge = "FREE";
                  totalAmount = totalItemPrice;
              }else {
                  serviceCharge = "60";
                  totalAmount = totalItemPrice + 60;

              }




              ((cartTotalAmountViewholder) viewHolder).setTotalAmount(totalItems, totalItemPrice, serviceCharge, totalAmount, savedAmount);
              break;
          default:
              return;
      }
            if (lastposition < position){
                Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.fade_in);
                viewHolder.itemView.setAnimation(animation);
                lastposition = position;
            }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class cartItemViewholder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView freeCoupenIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;

        private LinearLayout deleteBtn;



        public cartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupenIcon = itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);

            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productID, String resource, String title, Long freeCoupensNo, String productPriceText, String cuttedPriceText, Long offerAppliedNo, final int position) {

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.mini_placeholder)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo > 0) {
                freeCoupenIcon.setVisibility(View.VISIBLE);
                freeCoupens.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("free " + freeCoupensNo + " Coupen");
                } else {
                    freeCoupens.setText("free " + freeCoupensNo + " Coupens");
                }
            } else {
                freeCoupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }

            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if (offerAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offerAppliedNo + " offers applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }

            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_no);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                    Button okBtn = quantityDialog.findViewById(R.id.ok_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            quantityDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productQuantity.setText("Qty: " + quantityNo.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }

            });

            if (showDeleteBtn){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ProductDetailsActivity.running_cart_query){
                        ProductDetailsActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position,itemView.getContext());
                    }
                }
            });
        }
    }

            class cartTotalAmountViewholder extends RecyclerView.ViewHolder {

                private TextView totalItems;
                private TextView totalItemPrice;
                private TextView serviceCharge;
                private TextView totalAmount;
                private TextView savedAmount;



                public cartTotalAmountViewholder(@NonNull View itemView) {
                    super(itemView);

                    totalItems = itemView.findViewById(R.id.total_iteams);
                    totalItemPrice = itemView.findViewById(R.id.total_items_price);
                    serviceCharge = itemView.findViewById(R.id.delivery_price);
                    totalAmount = itemView.findViewById(R.id.total_price);
                    savedAmount = itemView.findViewById(R.id.saved_amount);
                }
                private void setTotalAmount(int totalItemText,int totalItemPriceText,String serviceChargeText,int totalAmountText,int savedAmountText){
                    totalItems.setText("Price("+totalItemText+" items)");
                    totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
                    if (serviceChargeText.equals("FREE")){
                    serviceCharge.setText(serviceChargeText);
                    }else {
                        serviceCharge.setText("Rs."+serviceChargeText+"/-");

                    }
                    totalAmount.setText("Rs."+totalAmountText+"/-");
                    totalAmount.setText("Rs."+totalAmountText+"/-");
                    savedAmount.setText("You saved Rs."+savedAmountText+"/- on this order.");

                }

            }
        }


