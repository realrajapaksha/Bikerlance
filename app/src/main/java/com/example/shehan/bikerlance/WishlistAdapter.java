package com.example.shehan.bikerlance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.Viewholder> {

    private List<WishlistModel> wishlistModelList;
    private int lastposition = -1;
    private Boolean wishlist;



    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String productId = wishlistModelList.get(position).getProductId();
        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        long freeCoupens = wishlistModelList.get(position).getFreeCoupens();
        String rating = wishlistModelList.get(position).getRating();
        long totalRating = wishlistModelList.get(position).getTotalRatings();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        boolean paymentMethod = wishlistModelList.get(position).isCOD();
        viewholder.setData(productId,resource,title,freeCoupens,rating,totalRating,productPrice,cuttedPrice,paymentMethod,position);

        if (lastposition < position){
            Animation animation = AnimationUtils.loadAnimation(viewholder.itemView.getContext(),R.anim.fade_in);
            viewholder.itemView.setAnimation(animation);
            lastposition = position;
        }


    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageView deletebtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupen);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            deletebtn = itemView.findViewById(R.id.deletebtn);

        }
        private void setData(final String productId, String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, final String price, String cuttedPriceValue, Boolean COD, final int index) {
          Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.mini_placeholder)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo != 0) {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {

                    freeCoupens.setText("free " + freeCoupensNo + "coupen");
                } else {
                    freeCoupens.setText("free " + freeCoupensNo + "coupens");
                }
            } else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalRatings.setText("("+totalRatingsNo+") rating");
            productPrice.setText("Rs."+price+"/-");
            cuttedPrice.setText("Rs."+cuttedPriceValue+"/-");
            if (COD){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishlist){
                deletebtn.setVisibility(View.VISIBLE);
            }else {
                deletebtn.setVisibility(View.GONE);
            }
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if (!ProductDetailsActivity.running_wishlist_query) {
                            ProductDetailsActivity.running_wishlist_query = true;
                            DBqueries.removeFromWishlist(index, itemView.getContext());
                        }
                        }

            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent =new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}

